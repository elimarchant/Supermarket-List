package shoppingList.Exceptions;

/**
 * Thrown when a user inputs an unrecognized, malformed, or logically invalid command in the CLI.
 */
public class InvalidCommandException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message explaining why the command is invalid.
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}