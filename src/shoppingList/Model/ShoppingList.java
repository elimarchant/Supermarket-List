package shoppingList.Model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shoppingList.Exceptions.InvalidCommandException;
import shoppingList.Exceptions.ItemNotFoundException;

/**
 * Represents a centralized shopping list within the supermarket management system.
 * It manages a collection of {@link InventoryItem} objects and provides methods
 * for item addition, modification, removal, and calculation of total costs.
 */
public class ShoppingList {

    private final List<InventoryItem> list;

    /**
     * Constructs a new, empty ShoppingList.
     */
    public ShoppingList() {
        this.list = new ArrayList<>();
    }

    /**
     * Adds an item to the shopping list. If an item with the same name already exists,
     * it attempts to merge the quantities rather than creating a duplicate entry.
     *
     * @param newItem The {@link InventoryItem} to be added.
     * @throws InvalidCommandException If the unit type of the new item does not
     * match the existing item's unit type.
     */
    public void addItem(InventoryItem newItem) {

        boolean appears = false;

        for (InventoryItem item : list) {
            if (item.getName().equals(newItem.getName())) {

                if (item.getUnitType() != newItem.getUnitType()) {
                    throw new InvalidCommandException
                            ("Cant add Item of unit - " + newItem.getUnitType()
                                    + " to " + item.getName() + " that has unit of "
                                    + item.getUnitType() + "!");
                }
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                appears = true;
                break;
            }
        }
        if (!appears) {
            list.add(newItem);
        }

    }

    /**
     * Decreases the quantity of a specific item or removes it entirely if the
     * specified quantity meets or exceeds the current stock.
     *
     * @param quantity The amount of the item to remove. If 0 or greater than the current stock, the item is removed completely.
     * @param itemName The identifier used for finding the item to modify or remove.
     * @throws ItemNotFoundException If the item is not currently in the shopping list.
     */
    public void removeItem(int quantity, String itemName) {
        InventoryItem item = getItemByName(itemName);
        if (item == null) {
            throw new ItemNotFoundException(itemName);
        }

        if (quantity == 0 || quantity >= item.getQuantity()) {
            list.remove(item);
        } else {
            int newQuantity = item.getQuantity() - quantity;
            item.setQuantity(newQuantity);
        }
    }

    /**
     * Clears all items currently held in the shopping list.
     */
    public void clearList() {
        list.clear();
    }

    /**
     * Iterates through the ShoppingList to find a match by exact name.
     *
     * @param itemName The exact name of the item.
     * @return The matching {@link InventoryItem}, or null if not found.
     */
    public InventoryItem getItemByName(String itemName) {
        for (InventoryItem item : list) {
            if (itemName.equalsIgnoreCase(item.getName())) {
                return item;
            }
        }
        return null;
    }


    /**
     * Generates a formatted text receipt from the current contents of the shopping list,
     * including quantities, unit types, individual item totals, and the aggregate price.
     *
     * @return A formatted String representing the receipt, or null if the list is empty.
     */
    public String generateRecipt() {
        StringBuilder recipt = new StringBuilder();
        recipt.append("Shopping list:\n");

        if (list.isEmpty()) {
            return null;
        }

        double totalPrice = 0.0;

        for (InventoryItem item : list) {
            totalPrice += item.getPrice() * item.getQuantity();
            String displayItemPrice = String.format("%.2f", item.getQuantity() * item.getPrice());

            String displayUnit = item.getUnitType().name();

            if (item.getQuantity() > 1) {
                displayUnit = addLetters(item,displayUnit);
            }

            String itemDetails = item.getName()
                    + " , " + item.getQuantity()
                    + " " + displayUnit
                    + ". Price : " + displayItemPrice;

            recipt.append(itemDetails).append("\n");
        }
        String displayTotalPrice = String.format("%.2f", totalPrice);
        recipt.append("Total price is ").append(displayTotalPrice);

        return recipt.toString();
    }

    /**
     * Helper method to correctly pluralize the display names of specific unit types.
     * For example, turns "Box" into "Boxes" and "Piece" into "Pieces".
     *
     * @param item        The item whose unit is being evaluated.
     * @param displayUnit The current string representation of the unit.
     * @return The properly pluralized string for the unit type.
     */
    private String addLetters(InventoryItem item, String displayUnit) {
        if (item.getUnitType() == UnitType.Box) {
            displayUnit += "es";
        } else if (item.getUnitType() == UnitType.Piece) {
            displayUnit += "s";
        }
        return displayUnit;
    }

    /**
     * Provides read-only access to the underlying list of items.
     * This enforces encapsulation by ensuring external classes cannot modify
     * the list directly, forcing them to use the defined business logic methods.
     *
     * @return An unmodifiable {@link List} containing the current {@link InventoryItem} objects.
     */
    public List<InventoryItem> getItemList() {
        return Collections.unmodifiableList(list);
    }

    /**
     * Directly updates the quantity of an existing item in the list.
     * If the specified amount is 0 or less, the item is removed from the list.
     *
     * @param newItem The exact name of the item to update.
     * @param amount  The new quantity to assign to the item.
     * @throws ItemNotFoundException If the specified item does not exist in the list.
     */
    public void updateItem(String newItem,  int amount) {
        for (InventoryItem item : list) {
            if (item.getName().equals(newItem)) {

                if(amount<=0){
                    list.remove(item);
                }
                else{
                    item.setQuantity(amount);
                }
                return;
            }

        }
        throw new ItemNotFoundException(newItem + " ");
    }

    /**
     * Replaces the current list entirely with a newly provided collection of items.
     * Useful for loading a saved state from persistent storage.
     *
     * @param newItems A {@link List} of {@link InventoryItem} objects to populate the shopping list.
     */
    public void replaceAllItems(List<InventoryItem> newItems) {
        this.list.clear();
        this.list.addAll(newItems);
    }

}