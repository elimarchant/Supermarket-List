package shoppingList.Command;


import shoppingList.Exceptions.ItemNotFoundException;

import shoppingList.Model.ShoppingList;

/**
 * Concrete implementation of {@link Command} used to reduce the quantity of
 * or completely remove a product from the shopping list.
 */
public class RemoveCommand implements Command  {
    private final String itemToRemove;
    private final int quantityToRemove;

    /**
     * Constructs a RemoveCommand with the target item and the quantity to be deducted.
     *
     * @param itemToRemove     The name of the item to be modified.
     * @param quantityToRemove The amount to remove.
     */
    public RemoveCommand(String itemToRemove, int quantityToRemove) {
        this.itemToRemove = itemToRemove;
        this.quantityToRemove = quantityToRemove;
    }

    /**
     * Executes the removal logic by delegating to the domain model.
     * Gracefully catches any {@link ItemNotFoundException} and returns the
     * error message as a result instead of allowing the application to crash.
     *
     * @param shoppingList The domain model to remove the item from.
     * @return A {@link CommandResult} indicating success or providing an error message.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {
        try{
            shoppingList.removeItem(quantityToRemove,itemToRemove);
            return new  CommandResult(true, "Successfully removed item");

        }
        catch(ItemNotFoundException e){
            return new CommandResult(true,e.getMessage());
        }

    }
}