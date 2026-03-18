package shoppingList.Command;


import shoppingList.Exceptions.RecipeGenerationException;
import shoppingList.Model.InventoryItem;
import shoppingList.Model.ShoppingList;
import shoppingList.Service.GeminiRecipeService;
import shoppingList.Service.RecipeService;

import java.util.List;

/**
 * Concrete implementation of {@link Command} that integrates with an external
 * AI service to add ingredients for a specific recipe to the shopping list.
 */
public class RecipeCommand implements Command {

    private final String toMake;
    private final int servings;

    /**
     * Constructs a RecipeCommand with the target recipe and serving size.
     *
     * @param toMake   The name of the recipe to generate.
     * @param servings The number of servings required.
     */
    public RecipeCommand(String toMake, int servings) {
        this.toMake = toMake;
        this.servings = servings;
    }

    /**
     * Executes the recipe-to-inventory logic.
     * Utilizes a {@link RecipeService} to fetch required ingredients and
     * iteratively adds each resulting item to the current shopping list.
     *
     * @param shoppingList The domain model where ingredients will be added.
     * @return A {@link CommandResult} confirming the items were added.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {

        RecipeService gemini = new GeminiRecipeService();

        try{

            List<InventoryItem> itemsToAdd = gemini.getRecipe(toMake,servings);
            for (InventoryItem item : itemsToAdd) {
                shoppingList.addItem(item);
            }

            return new CommandResult(true,
                    "Successfully added items from recipe to list");
        }
        catch(RecipeGenerationException e){
            return new CommandResult(true,e.getMessage());
        }


    }
}