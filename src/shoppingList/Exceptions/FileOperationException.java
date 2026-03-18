package shoppingList.Exceptions;

/**
 * Thrown when an error occurs during system I/O operations, such as saving or loading the shopping list.
 */
public class FileOperationException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message explaining the file operation failure.
     */
    public FileOperationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and root cause.
     *
     * @param message The detail message.
     * @param cause   The underlying cause of the exception (e.g., an IOException).
     */
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}