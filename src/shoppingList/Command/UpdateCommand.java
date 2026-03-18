package shoppingList.Command;

import shoppingList.Exceptions.ItemNotFoundException;
import shoppingList.Model.InventoryItem;
import shoppingList.Model.ShoppingList;

/**
 * Concrete implementation of {@link Command} used to explicitly set
 * the quantity of an existing item in the shopping list.
 */
public class UpdateCommand implements Command {

    private final String itemToUpdate;
    private final int amount;

    /**
     * Constructs an UpdateCommand for the specified item.
     *
     * @param itemToUpdate The name of the product to update.
     * @param amount       The new target quantity.
     */
    public UpdateCommand(String itemToUpdate, int amount) {
        this.itemToUpdate = itemToUpdate;
        this.amount = amount;
    }

    /**
     * Executes the update logic.
     * Safely handles scenarios where the item name is not found by catching
     * the domain exception and returning a user-friendly error string.
     *
     * @param shoppingList The domain model where the update will be performed.
     * @return A {@link CommandResult} confirming the update or describing the failure.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {
        try{
            shoppingList.updateItem(itemToUpdate,amount);
            return new CommandResult(true, "Successfully updated item");
        }
        catch (ItemNotFoundException e){
            return new CommandResult(true, e.getMessage());
        }
    }
}