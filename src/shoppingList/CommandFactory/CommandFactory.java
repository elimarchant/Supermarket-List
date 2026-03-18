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
    Command create(String[] parts);
}