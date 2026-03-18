package shoppingList.Exceptions;

/**
 * Thrown when the external AI recipe service fails to generate, retrieve, or parse a valid recipe.
 */
public class RecipeGenerationException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message explaining the cause of the AI failure.
     */
    public RecipeGenerationException(String message) {
        super(message);
    }
}