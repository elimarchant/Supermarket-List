package shoppingList.Model;

/**
 * Represents a foundational product entity within the supermarket domain.
 * This class encapsulates the fundamental details of an item (such as its name)
 * independent of inventory-specific metrics like quantity, price, or unit type.
 */
public class Product {
    private final String name;

    /**
     * Constructs a new Product with the specified name.
     *
     * @param name The name of the product.
     */
    public Product(String name) {
        this.name = name;

    }

    /**
     * Retrieves the name of the product.
     *
     * @return The product name.
     */
    public String getName() {
        return name;
    }

}