package shoppingList.CommandFactory;

import shoppingList.Command.Command;
import shoppingList.Command.SaveCommand;
import shoppingList.Exceptions.InvalidCommandException;

import java.util.Arrays;

/**
 * Factory responsible for parsing and constructing a {@link SaveCommand}.
 * Performs rigorous validation on the requested file name to ensure compatibility
 * with the operating system's file system.
 */
public class SaveCommandFactory implements CommandFactory {



    private static final int MIN_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 100;

    /**
     * Regular expression identifying characters forbidden in standard OS file names.
     */
    private static final String FORBIDDEN_CHARS_REGEX = ".*[\\\\/:*?\"<>|].*";

    /**
     * Creates a SaveCommand after validating the input format and the proposed file name.
     *
     * @param parts The tokenized string array from the user input.
     * @return A new {@link SaveCommand} configured with a validated file name.
     */
    @Override
    public Command create(String[] parts) {
        validateCommandPrefix(parts);

        String textName = extractTextName(parts);
        validateTextName(textName);

        return new SaveCommand(textName);
    }

    /**
     * Ensures the input array contains the minimum tokens required for a save operation.
     *
     * @param parts The input tokens.
     * @throws InvalidCommandException If the list name is missing.
     */
    private void validateCommandPrefix(String[] parts) {
        if (parts == null || parts.length < MIN_LENGTH) {
            throw new InvalidCommandException("Invalid format. Expected: 'save <list_name>'");
        }
    }

    /**
     * Reconstructs the full file name from the tokens following the command keyword.
     *
     * @param parts The command arguments.
     * @return The joined string representing the file name.
     */
    private String extractTextName(String[] parts) {
        String[] nameParts = Arrays.copyOfRange(parts, 1, parts.length);
        return String.join(" ", nameParts);
    }

    /**
     * Validates the file name against length constraints and forbidden characters.
     *
     * @param textName The name to be validated.
     * @throws InvalidCommandException If the name is empty, too long, or contains illegal characters.
     */
    private void validateTextName(String textName) {
        if (textName.trim().isEmpty()) {
            throw new InvalidCommandException("List name cannot be empty.");
        }

        if (textName.length() > MAX_NAME_LENGTH) {
            throw new InvalidCommandException("List name exceeds the maximum allowed length of " + MAX_NAME_LENGTH + " characters.");
        }

        if (textName.matches(FORBIDDEN_CHARS_REGEX)) {
            throw new InvalidCommandException("List name contains invalid characters for file saving. Avoid \\ / : * ? \" < > |");
        }
    }
}