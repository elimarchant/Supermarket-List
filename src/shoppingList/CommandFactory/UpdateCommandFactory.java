package shoppingList.CommandFactory;

import shoppingList.Command.Command;
import shoppingList.Command.UpdateCommand;
import shoppingList.Exceptions.InvalidCommandException;

/**
 * Factory responsible for parsing and constructing an {@link UpdateCommand}.
 * Enforces a strict three-part format for updating existing list items.
 */
public class UpdateCommandFactory implements CommandFactory {

    private static final int EXPECTED_LENGTH = 3;
    private static final int COMMAND_INDEX = 0;
    private static final int ITEM_NAME_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String EXPECTED_COMMAND = "update";

    /**
     * Creates an UpdateCommand after verifying the input format and numeric validity.
     *
     * @param parts The tokenized string array from the user input.
     * @return A new {@link UpdateCommand} with the target item and the new amount.
     */
    @Override
    public Command create(String[] parts) {
        validateFormat(parts);

        String itemName = extractItemName(parts);
        int amount = extractAmount(parts);

        return new UpdateCommand(itemName, amount);
    }

    /**
     * Validates that the input matches the specific 'update [item] [amount]' structure.
     *
     * @param parts The input tokens.
     * @throws InvalidCommandException If the token count is incorrect or the prefix is wrong.
     */
    private void validateFormat(String[] parts) {
        if (parts == null || parts.length != EXPECTED_LENGTH) {
            throw new InvalidCommandException("Invalid format." +
                    " Expected exactly 3 parts: 'update <item> <amount>'");
        }

        if (!parts[COMMAND_INDEX].equalsIgnoreCase(EXPECTED_COMMAND)) {
            throw new InvalidCommandException("Invalid command prefix. Expected 'update'");
        }
    }

    /**
     * Extracts the target item name from the expected index.
     *
     * @param parts The input tokens.
     * @return The item name.
     */
    private String extractItemName(String[] parts) {
        return parts[ITEM_NAME_INDEX];
    }

    /**
     * Extracts and parses the new quantity from the expected index.
     *
     * @param parts The input tokens.
     * @return The amount as an integer.
     * @throws InvalidCommandException If the amount token is not a valid integer.
     */
    private int extractAmount(String[] parts) {
        try {
            return Integer.parseInt(parts[AMOUNT_INDEX]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Invalid amount format." +
                    " Amount must be a valid integer.");
        }
    }
}