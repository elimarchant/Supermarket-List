package shoppingList.Exceptions;

/**
 * Thrown when attempting to modify, remove, or access an item that does not exist in the shopping list.
 */
public class ItemNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception indicating the specific item could not be found.
     *
     * @param itemName The name of the item that was not found.
     */
    public ItemNotFoundException(String itemName) {
        super("The item '" + itemName + "' was not found in the shopping list.");
    }
}