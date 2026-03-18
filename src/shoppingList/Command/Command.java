package shoppingList.Command;

import shoppingList.Model.ShoppingList;

/**
 * The core interface for the Command pattern implementation.
 * Encapsulates a request as an object, thereby letting you parameterize
 * the application with different requests and support undoable operations
 * or logging if needed in the future.
 */
public interface Command {

    /**
     * Executes the specific logic associated with the command.
     *
     * @param shoppingList The domain model upon which the command operates.
     * @return A {@link CommandResult} containing the success message and application state.
     */
<<<<<<< HEAD
    CommandResult execute(ShoppingList shoppingList);
=======
    public CommandResult execute(ShoppingList shoppingList);
>>>>>>> 7e74ce40a90cd5df7d2637f9ab17a68177c9e39f
}