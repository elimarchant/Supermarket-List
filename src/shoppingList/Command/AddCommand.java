package shoppingList.Command;

import shoppingList.Model.InventoryItem;
import shoppingList.Model.ShoppingList;

/**
 * Concrete implementation of {@link Command} used to add a new item
 * to the shopping list.
 */
public class AddCommand implements Command  {

    private InventoryItem itemToAdd;

    /**
     * Constructs an AddCommand with the specific item to be added.
     *
     * @param itemToAdd The {@link InventoryItem} to be injected into the shopping list.
     */
    public AddCommand(InventoryItem itemToAdd) {
        this.itemToAdd = itemToAdd;
    }

    /**
     * Executes the addition logic by delegating to the shopping list model.
     *
     * @param shoppingList The list to which the item will be added.
     * @return A successful {@link CommandResult}.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {
        shoppingList.addItem(itemToAdd);
        return new  CommandResult(true, "Successfully added item");
    }
}