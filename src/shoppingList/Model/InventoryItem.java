package shoppingList.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a specific item within the supermarket inventory or shopping list.
 * Encapsulates a {@link Product} along with its associated quantity, unit type, and price.
 */
public class InventoryItem {

    private final Product product;
    private int quantity;
    private final UnitType unitType;
    private final double price;

    /**
     * Constructs a new InventoryItem with full details.
     *
     * @param product  The product associated with this inventory item.
     * @param quantity The amount of the product.
     * @param unitType The unit of measurement for the product (e.g., kg, pieces).
     * @param price    The price of the product.
     */
    public InventoryItem(Product product, int quantity,  UnitType unitType,  double price) {
        this.product = product;
        this.quantity = quantity;
        this.unitType = unitType;
        this.price = price;
    }

    /**
     * Constructs a new InventoryItem with a default quantity of 1 and a price of 0.0.
     *
     * @param product  The product associated with this inventory item.
     * @param unitType The unit of measurement for the product.
     */
    public InventoryItem(Product product,  UnitType unitType) {
        this.product = product;
        this.quantity = 1;
        this.unitType = unitType;
        this.price = 0;
    }

    /**
     * Constructs an InventoryItem from JSON properties during deserialization.
     * Recreates the underlying {@link Product} object using the provided name.
     *
     * @param name     The name of the product.
     * @param quantity The amount of the product.
     * @param unitType The unit of measurement for the product.
     * @param price    The price of the product.
     */
    @JsonCreator
    public InventoryItem(
            @JsonProperty("name") String name,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("unitType") UnitType unitType,
            @JsonProperty("price") double price) {

        this.product = new Product(name);
        this.quantity = quantity;
        this.unitType = unitType;
        this.price = price;
    }

    /**
     * Retrieves the name of the underlying product.
     *
     * @return The product name.
     */
    public String getName() {
        return product.getName();
    }

    /**
     * Retrieves the current quantity of this item.
     *
     * @return The quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Updates the quantity of this item.
     * This method is deliberately package-private to enforce encapsulation, ensuring
     * that modifications only occur through authorized domain classes like the ShoppingList,
     * rather than direct access from the main application layer.
     *
     * @param quantity The new quantity to set.
     */
    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the unit of measurement for this item.
     *
     * @return The unit type.
     */
    public UnitType getUnitType() {
        return unitType;
    }

    /**
     * Retrieves the price of this item.
     *
     * @return The price.
     */
    public double getPrice() {
        return price;
    }

}