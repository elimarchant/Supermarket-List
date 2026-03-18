package shoppingList.CommandFactory;

import shoppingList.Command.Command;

/**
 * Functional interface for the Command Factory pattern.
 * Defines the contract for transforming raw string input parts into executable {@link Command} objects.
 */
public interface CommandFactory {

    /**
     * Creates a specific command based on the provided input arguments.
     *
     * @param parts The tokenized string array from the user input.
     * @return A concrete implementation of the {@link Command} interface.
     */
<<<<<<< HEAD
    Command create(String[] parts);
=======
    public Command create(String[] parts);
>>>>>>> 7e74ce40a90cd5df7d2637f9ab17a68177c9e39f
}