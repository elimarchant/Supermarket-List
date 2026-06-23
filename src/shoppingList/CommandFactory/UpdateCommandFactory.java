package shoppingList.CommandFactory;

import shoppingList.Command.Command;
import shoppingList.Command.UpdateCommand;
import shoppingList.Exceptions.InvalidCommandException;
import shoppingList.Model.UnitType;

/**
 * Factory responsible for parsing and constructing an {@link UpdateCommand}.
 * Supports {@code update <item> <amount>} and {@code update <item> <amount> <unit>}.
 */
public class UpdateCommandFactory implements CommandFactory {

    private static final java.util.regex.Pattern INT_PATTERN =
            java.util.regex.Pattern.compile("^\\d+$");

    private static final int COMMAND_INDEX = 0;
    private static final String EXPECTED_COMMAND = "update";

    /**
     * Creates an UpdateCommand after verifying the input format and numeric validity.
     *
     * @param parts The tokenized string array from the user input.
     * @return A new {@link UpdateCommand} with the target item, amount, and optional unit.
     */
    @Override
    public Command create(String[] parts) {
        validateCommand(parts);

        int currentIndex = 1;
        StringBuilder nameBuilder = new StringBuilder();

        while (currentIndex < parts.length) {
            String currentWord = parts[currentIndex];
            if (INT_PATTERN.matcher(currentWord).matches()) {
                break;
            }
            nameBuilder.append(currentWord).append(" ");
            currentIndex++;
        }

        String itemName = nameBuilder.toString().trim();
        if (itemName.isEmpty()) {
            throw new InvalidCommandException("Invalid product name. Please provide a product name.");
        }

        if (currentIndex >= parts.length) {
            throw new InvalidCommandException("Invalid format." +
                    " Expected 'update <item> <amount>' or 'update <item> <amount> <unit>'");
        }

        String amountToken = parts[currentIndex];
        if (!INT_PATTERN.matcher(amountToken).matches()) {
            throw new InvalidCommandException("Invalid amount format." +
                    " Amount must be a valid integer.");
        }
        int amount = Integer.parseInt(amountToken);
        currentIndex++;

        UnitType newUnit = null;
        if (currentIndex < parts.length) {
            newUnit = UnitType.getLabel(parts[currentIndex]);
            if (newUnit == null) {
                throw new InvalidCommandException("Unknown UnitType: " + parts[currentIndex]);
            }
            currentIndex++;
        }

        if (currentIndex < parts.length) {
            throw new InvalidCommandException("Unexpected arguments at the end of command.");
        }

        return new UpdateCommand(itemName, amount, newUnit);
    }

    /**
     * Validates that the input starts with the update command keyword.
     *
     * @param parts The input tokens.
     * @throws InvalidCommandException If the input is missing or the prefix is wrong.
     */
    private void validateCommand(String[] parts) {
        if (parts == null || parts.length < 2) {
            throw new InvalidCommandException("Invalid format." +
                    " Expected 'update <item> <amount>' or 'update <item> <amount> <unit>'");
        }

        if (!parts[COMMAND_INDEX].equalsIgnoreCase(EXPECTED_COMMAND)) {
            throw new InvalidCommandException("Invalid command prefix. Expected 'update'");
        }
    }
}
