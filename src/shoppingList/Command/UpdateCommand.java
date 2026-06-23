package shoppingList.Command;

import shoppingList.Exceptions.ItemNotFoundException;

import shoppingList.Model.ShoppingList;
import shoppingList.Model.UnitType;

/**
 * Concrete implementation of {@link Command} used to explicitly set
 * the quantity of an existing item in the shopping list, optionally changing its unit.
 */
public class UpdateCommand implements Command {

    private final String itemToUpdate;
    private final int amount;
    private final UnitType newUnit;

    /**
     * Constructs an UpdateCommand for the specified item.
     *
     * @param itemToUpdate The name of the product to update.
     * @param amount       The new target quantity.
     * @param newUnit      Optional new unit; when null, the current unit is kept.
     */
    public UpdateCommand(String itemToUpdate, int amount, UnitType newUnit) {
        this.itemToUpdate = itemToUpdate;
        this.amount = amount;
        this.newUnit = newUnit;
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
        try {
            shoppingList.updateItem(itemToUpdate, amount, newUnit);
            return new CommandResult(true, "Successfully updated item");
        } catch (ItemNotFoundException e) {
            return new CommandResult(true, e.getMessage());
        }
    }
}
