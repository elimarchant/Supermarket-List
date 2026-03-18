package shoppingList.CommandFactory;

import shoppingList.Command.Command;
import shoppingList.Command.RemoveCommand;
import shoppingList.Exceptions.InvalidCommandException;

/**
 * Factory responsible for parsing and constructing a {@link RemoveCommand}.
 * Handles the extraction of the product name and the optional quantity to be removed.
 */
public class RemoveCommandFactory implements CommandFactory {

    /**
     * Pre-compiled regex pattern for identifying integer values.
     */
    private static final java.util.regex.Pattern INT_PATTERN = java.util.regex.Pattern.compile("^\\d+$");

    /**
     * Orchestrates the creation of a RemoveCommand by parsing the input tokens.
     *
     * @param parts The tokenized string array from the user input.
     * @return A new {@link RemoveCommand} with the target item name and quantity.
     */
    @Override
    public Command create(String[] parts) {
        String itemName = makeName(parts);
        int quantityToRemove = getItemQuantity(parts);
        return new RemoveCommand(itemName, quantityToRemove);
    }

    /**
     * Extracts the product name from the input tokens by collecting all strings
     * until a numeric quantity is encountered.
     *
     * @param parts The command arguments.
     * @return The reconstructed product name string.
     */
    private String makeName(String[] parts) {
        StringBuilder nameBuilder = new StringBuilder();
        int currentIndex = 1;

        while (currentIndex < parts.length ) {
            String currentWord = parts[currentIndex];

            if (INT_PATTERN.matcher(currentWord).matches()){
                break;
            }
            nameBuilder.append(currentWord).append(" ");
            currentIndex++;
        }
        return nameBuilder.toString().trim();
    }

    /**
     * Extracts the specific quantity to be removed from the input tokens.
     * Validates that the input follows a strict name-then-amount format.
     *
     * @param parts The user input tokens.
     * @return The quantity to remove as an integer.
     * @throws InvalidCommandException If the format is invalid or multiple numbers are found.
     */
    private int getItemQuantity(String[] parts) {
        int  quantity = 0;
        for (String currentWord : parts) {
            if(INT_PATTERN.matcher(currentWord).matches()){
                quantity = Integer.parseInt(currentWord);

            }
            else if(quantity>0){
                throw new InvalidCommandException("Invalid format. must write name and amount only.");
            }

        }
        return quantity;
    }
}