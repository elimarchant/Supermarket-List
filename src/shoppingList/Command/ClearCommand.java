package shoppingList.Command;

import shoppingList.Model.ShoppingList;

/**
 * Concrete implementation of {@link Command} used to purge all items
 * from the shopping list.
 */
public class ClearCommand implements Command {

    /**
     * Executes the list clearing logic.
     *
     * @param shoppingList The list to be emptied.
     * @return A {@link CommandResult} confirming the list was cleared.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {
        shoppingList.clearList();
        return new CommandResult(true, "List cleared successfully.");
    }
}