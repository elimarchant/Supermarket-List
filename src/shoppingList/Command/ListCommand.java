package shoppingList.Command;

import shoppingList.Model.ShoppingList;

/**
 * Concrete implementation of {@link Command} used to retrieve and display
 * the current state of the shopping list.
 */
public class ListCommand implements Command {

    /**
     * Executes the list display logic.
     * Invokes the receipt generation from the domain model and handles
     * the case where the list is currently empty.
     *
     * @param shoppingList The domain model containing the items to be listed.
     * @return A {@link CommandResult} containing the formatted receipt or an empty list notification.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {
        String list = shoppingList.generateRecipt();
        if (list == null || list.isEmpty()) {
            return new CommandResult(true, "Shopping list is Empty");
        }
        return new CommandResult(true, list);
    }
}