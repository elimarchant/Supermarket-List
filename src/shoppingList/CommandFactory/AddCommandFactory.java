package shoppingList.CommandFactory;

import shoppingList.Command.AddCommand;
import shoppingList.Command.Command;
import shoppingList.Exceptions.InvalidCommandException;
import shoppingList.Model.InventoryItem;
import shoppingList.Model.Product;
import shoppingList.Model.UnitType;

/**
 * Factory responsible for parsing and constructing an {@link AddCommand}.
 * Validates complex input strings to build valid {@link InventoryItem} domain objects.
 */
public class AddCommandFactory implements CommandFactory {

    private static final java.util.regex.Pattern INT_PATTERN = java.util.regex.Pattern.compile("^\\d+$");
    private static final java.util.regex.Pattern DOUBLE_PATTERN = java.util.regex.Pattern.compile("^\\d+(\\.\\d+)?$");

    /**
     * Orchestrates the creation of an AddCommand by processing input tokens.
     *
     * @param parts The command arguments.
     * @return A new {@link AddCommand} containing the item to be added.
     */
    @Override
    public Command create(String[] parts) {
        InventoryItem newItem = makeItem(parts);
        return new AddCommand(newItem);
    }

    /**
     * Constructs an InventoryItem from raw user input parts using a multistep extraction process.
     * * 1. Collects product name parts until an integer or UnitType is encountered.
     * 2. Extracts the Quantity (first integer found).
     * 3. Extracts the required UnitType.
     * 4. Extracts the optional Price (double).
     * 5. Validates that no unexpected arguments remain.
     * 6. Assembles the final Product and InventoryItem objects.
     *
     * @param parts The command arguments (name, quantity, unit, price).
     * @return A fully populated {@link InventoryItem} object.
     * @throws InvalidCommandException If the input is blank, missing units, or contains malformed data.
     */
    private InventoryItem makeItem(String[] parts) {

        if(parts.length < 2){
            throw new InvalidCommandException("Command is blank. Please provide a product name.");
        }

        StringBuilder nameBuilder = new StringBuilder();
        int currentIndex = 1;

        while (currentIndex < parts.length ) {
            String currentWord = parts[currentIndex];
            if (INT_PATTERN.matcher(currentWord).matches()  || UnitType.getLabel(currentWord)!=null) {
                break;
            }
            nameBuilder.append(currentWord).append(" ");
            currentIndex++;
        }
        String name = nameBuilder.toString().trim();
        if(name.isEmpty()){
            throw new InvalidCommandException("Invalid product name. Please provide a product name.");
        }

        int quantity = 1;
        if (currentIndex < parts.length && INT_PATTERN.matcher(parts[currentIndex]).matches()) {
            quantity = Integer.parseInt(parts[currentIndex]);
            currentIndex++;
        }

        if (currentIndex >= parts.length) {
            throw new InvalidCommandException("Missing required UnitType (kg, l, pc, etc.)");
        }

        UnitType unit = UnitType.getLabel(parts[currentIndex]);
        if (unit == null) {
            throw new InvalidCommandException("Unknown UnitType: " + parts[currentIndex]);
        }
        currentIndex++;

        double price = 0.0;
        if (currentIndex < parts.length) {
            if (DOUBLE_PATTERN.matcher(parts[currentIndex]).matches()) {
                price = Double.parseDouble(parts[currentIndex]);
                currentIndex++;
            } else {
                throw new InvalidCommandException("Expected a price, but found: " + parts[currentIndex]);
            }
        }

        if (currentIndex < parts.length) {
            throw new InvalidCommandException("Unexpected arguments at the end of command.");
        }

        Product product = new Product(name);
        return new InventoryItem(product, quantity, unit, price);
    }
}