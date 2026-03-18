package shoppingList.Command;

import shoppingList.Model.ShoppingList;

/**
 * Concrete implementation of {@link Command} used to signal the
 * termination of the application session.
 */
public class ExitCommand implements Command {

    /**
     * Executes the exit logic by returning a result that stops the main loop.
     *
     * @param shoppingList The current shopping list (unused in this command).
     * @return A {@link CommandResult} with keepRunning set to false.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {
        return new  CommandResult(false, "Exiting");
    }
}