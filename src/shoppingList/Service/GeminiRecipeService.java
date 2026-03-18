package shoppingList.Service;

import shoppingList.Exceptions.RecipeGenerationException;
import shoppingList.Model.InventoryItem;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import shoppingList.Model.Product;
import shoppingList.Model.UnitType;

/**
 * Concrete implementation of the {@link RecipeService} utilizing the Google Gemini API.
 * This service handles HTTP communication, JSON prompt engineering, and the mapping
 * of AI-generated responses into internal {@link InventoryItem} domain entities.
 */
public class GeminiRecipeService implements  RecipeService {

    /**
     * Retrieves the Gemini API key from the system environment variables.
     *
     * @return The API key string.
     * @throws RecipeGenerationException If the environment variable "GEMINI_API_KEY" is not set or is empty.
     */
    private String getApiKey() {
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null|| apiKey.isEmpty()) {
            throw new RecipeGenerationException("Please set API_KEY environment variable");
        }
        return apiKey;
    }

    /**
     * Generates a list of ingredients for a specified recipe using the Gemini AI model.
     * Validates the input, constructs the required JSON request, executes the API call,
     * extracts the raw AI string, and finally deserializes it into the domain models.
     *
     * @param recipeName The name of the recipe requested by the user.
     * @param servings   The number of servings to scale the ingredient quantities by.
     * @return A {@link List} of {@link InventoryItem} mapped from the AI response.
     * @throws RecipeGenerationException If the input parameters are invalid, or if the AI cannot formulate ingredients.
     */
    @Override
    public List<InventoryItem> getRecipe(String recipeName, int servings) {

        if(null == recipeName || recipeName.isEmpty() || servings < 0 || servings > 100) {
            throw new RecipeGenerationException("gemini didn't work");
        }

        String rawPrompt = getGeminiPrompt(recipeName, servings);
        String jsonPromptRequest = makeJsonStructure(rawPrompt);

        String rawGeminiResponse  = executeApiCall(jsonPromptRequest);

        String innerJsonArrayString = extractTextFromGeminiWrapper(rawGeminiResponse);



        List<InventoryItem> ingredients = mapToInventoryItems(innerJsonArrayString);

        return ingredients;
    }

    /**
     * Constructs the precise instructional prompt sent to the Gemini AI.
     * Enforces strict business rules regarding formatting, quantity rounding,
     * realistic local pricing (in NIS), and valid unit types.
     *
     * @param recipeName The requested recipe.
     * @param servings   The desired number of servings.
     * @return A raw string formatted with the specific instructions and parameters.
     */
    private String getGeminiPrompt(String recipeName, int servings) {
        String prompt = String.format(
                "You are a supermarket inventory system. I need the ingredients to make %d servings of %s. " +
                        "Return ONLY a raw JSON array. Do not include markdown formatting, backticks, or conversational text. " +
                        "Apply the following business rules: " +
                        "1. 'quantity' MUST be rounded UP to the nearest whole integer (e.g., if 0.5 is needed, return 1). " +
                        "2. 'price' MUST be an estimated realistic price (as a double) for ONE unit of that ingredient." +
                        " You should get the estimated prices from the website שופרסל or in shufersal - a supermarket in israel." +
                        " therefor the prices should be in NIS but that shouldn't bother you - all i need is the number" +
                        "Each object in the array must exactly match this format: " +
                        "{\"name\": \"product name\", \"quantity\": 1, \"unit\": \"kg\", \"price\": 2.50}. " +
                        "The 'unit' MUST be exactly one of these strings: (ml, kg, liter, pc, box, g)." +
                        " for smaller amount like salt, vinegar make sure to use g - grams or ml for milliLiter " +
                        "because no need to buy a kg of salt and pepper for such a small amount. " +
                        "If the recipeName is not a valid, edible food (e.g., 'gold', 'phone', 'dream'), return exactly the word null and nothing else.",
                servings, recipeName);

        return prompt;
    }

    /**
     * Wraps the raw text prompt into the specific JSON structure required by the Gemini API.
     * Builds the nested 'contents' and 'parts' arrays to fulfill the schema expectations.
     *
     * @param rawPrompt The raw instructional string prompt.
     * @return A serialized JSON string representing the complete request body.
     * @throws RecipeGenerationException If the Jackson ObjectMapper fails to serialize the map structure.
     */
    private String makeJsonStructure(String rawPrompt) {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> textPart = new HashMap<>();
        textPart.put("text", rawPrompt);

        List<Map<String, String>> partsArray = new ArrayList<>();
        partsArray.add(textPart);

        Map<String, Object> contentItem = new HashMap<>();
        contentItem.put("parts", partsArray);

        List<Map<String, Object>> contentsArray = new ArrayList<>();
        contentsArray.add(contentItem);

        Map<String, Object> rootRequestBody = new HashMap<>();
        rootRequestBody.put("contents", contentsArray);

        try {
            String JsonString = mapper.writeValueAsString(rootRequestBody);
            return JsonString;
        } catch (JsonProcessingException e) {
            throw new RecipeGenerationException("Request serialization failed");
        }
    }

    /**
     * Executes the synchronous HTTP POST request to the Gemini API endpoint.
     *
     * @param requestBody The formulated JSON payload to send to the server.
     * @return The raw JSON response body returned by the Gemini API.
     * @throws RecipeGenerationException If network connectivity fails, the thread is interrupted, or the HTTP status code is not 200.
     */
    private String executeApiCall(String requestBody)  {

        String GEMINI_API_URL_CONCATENATED_WITH_YOUR_API_KEY =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:" +
                        "generateContent?key="  + getApiKey();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.setHeader("Content-Type", "application/json");
        URI uriCode = URI.create(GEMINI_API_URL_CONCATENATED_WITH_YOUR_API_KEY);
        builder.uri(uriCode);
        builder.POST(HttpRequest.BodyPublishers.ofString(requestBody));

        HttpRequest RecipeRequest = builder.build();

        try{

            HttpResponse<String> RecipeResponse = client.send(
                    RecipeRequest, HttpResponse.BodyHandlers.ofString()
            );

            if(RecipeResponse.statusCode() != 200) {
                throw new RecipeGenerationException("API call failed. status - " +  RecipeResponse.statusCode());
            }

            String rawGeminiResponse = RecipeResponse.body();

            return rawGeminiResponse;
        }
        catch ( IOException e){
            throw new RecipeGenerationException("Network connection failed while contacting Gemini." + e.getMessage());
        }

        catch ( InterruptedException e){
            throw new RecipeGenerationException("The recipe generation was interrupted: " + e.getMessage());
        }

    }

    /**
     * Converts the raw Gemini JSON wrapper into a navigable tree structure to extract
     * the relevant, generated ingredients array string from the inner 'candidates' node.
     *
     * @param rawGeminiResponse The full JSON response from the Google API.
     * @return The extracted text content representing the generated JSON array of ingredients.
     * @throws RecipeGenerationException If the response structure is missing expected fields or parsing fails.
     */
    private String extractTextFromGeminiWrapper(String rawGeminiResponse) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(rawGeminiResponse);

            JsonNode candidatesArray = rootNode.get("candidates");
            if (candidatesArray == null || !candidatesArray.isArray() || candidatesArray.isEmpty()) {
                throw new RecipeGenerationException("Unexpected API response structure: missing candidates.");
            }

            JsonNode textNode = candidatesArray.get(0)
                    .get("content")
                    .get("parts")
                    .get(0)
                    .get("text");

            return textNode.asText();

        } catch (JsonProcessingException e) {
            throw new RecipeGenerationException("Failed to parse the JSON response from Gemini.");
        }
    }

    /**
     * Deserializes the extracted JSON array string from Gemini into a collection of {@link InventoryItem} objects.
     * Includes defensive programming logic to handle "null" string edge cases and defaults the unit
     * to 'Piece' if the AI hallucinates an invalid unit type.
     *
     * @param innerJsonArrayString The extracted string representation of the JSON array.
     * @return A mapped {@link List} of {@link InventoryItem} domain entities.
     * @throws RecipeGenerationException If the array contains the string "null" (invalid recipe prompt), or if Jackson fails to map the data.
     */
    private List<InventoryItem> mapToInventoryItems(String innerJsonArrayString) {
        if( innerJsonArrayString.equals("null".trim().replace("\"", ""))){
            throw new  RecipeGenerationException("Invalid recipe item.");
        }

        List<InventoryItem> finalList = new ArrayList<>();
        ObjectMapper mapper = new  ObjectMapper();

        try {
            JsonNode arrayNode = mapper.readTree(innerJsonArrayString);

            for(JsonNode jsonObject : arrayNode) {

                String itemName = jsonObject.get("name").asText();
                double itemQuantity = jsonObject.get("quantity").asDouble();
                String itemUnitString = jsonObject.get("unit").asText();
                double estimatedPrice = jsonObject.get("price").asDouble();

                Product newProduct = new Product(itemName);

                UnitType unitEnum = UnitType.getLabel(itemUnitString);

                if (unitEnum == null) {
                    unitEnum = UnitType.Piece;
                }

                InventoryItem newItem = new InventoryItem(newProduct, (int)Math.ceil(itemQuantity), unitEnum, estimatedPrice);

                finalList.add(newItem);
            }

            return finalList;

        } catch (JsonProcessingException e) {
            throw new RecipeGenerationException("Failed to parse recipe data: " + e.getMessage());
        }
    }

}