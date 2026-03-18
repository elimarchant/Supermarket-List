package shoppingList.CommandFactory;

import shoppingList.Command.Command;
import shoppingList.Command.LoadCommand;
import shoppingList.Exceptions.InvalidCommandException;

import java.util.Arrays;

/**
 * Factory responsible for parsing and constructing a {@link LoadCommand}.
 * Handles the extraction of multi-word file names from the command tokens to
 * ensure the correct shopping list can be retrieved from persistent storage.
 */
public class LoadCommandFactory implements CommandFactory {

    private static final int MIN_LENGTH = 2;

    /**
     * Orchestrates the creation of a LoadCommand.
     * Validates the input structure and extracts the targeted list name before
     * instantiating the command.
     *
     * @param parts The tokenized string array from the user input.
     * @return A new {@link LoadCommand} configured with the specified list name.
     */
    @Override
    public Command create(String[] parts) {
        validateCommandPrefix(parts);
        String textName = extractTextName(parts);
        return new LoadCommand(textName);
    }

    /**
     * Validates that the input array contains the minimum required elements
     * for a load operation.
     *
     * @param parts The command arguments.
     * @throws InvalidCommandException If the list name is missing.
     */
    private void validateCommandPrefix(String[] parts) {
        if (parts == null || parts.length < MIN_LENGTH) {
            throw new InvalidCommandException("Invalid format. Expected: 'load <list_name>'");
        }
    }

    /**
     * Reconstructs the full list name by joining all tokens following the initial command keyword.
     * This allows for file names that contain spaces.
     *
     * @param parts The full array of input tokens.
     * @return A single string representing the intended list name.
     */
    private String extractTextName(String[] parts) {
        String[] nameParts = Arrays.copyOfRange(parts, 1, parts.length);
        return String.join(" ", nameParts);
    }
}