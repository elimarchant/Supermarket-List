package shoppingList.Parser;

import shoppingList.Command.*;
import shoppingList.CommandFactory.*;
import shoppingList.Exceptions.InvalidCommandException;

import java.util.HashMap;
import java.util.Map;

/**
 * Acts as the central input router for the application's Command Line Interface.
 * Utilizes a registry pattern to map raw string commands to their corresponding
 * {@link CommandFactory} implementations, ensuring that parsing logic is decoupled
 * from the main application flow.
 */
public class InputParser {

    /**
     * Pre-compiled regular expression for identifying positive integers.
     */
    private static final java.util.regex.Pattern INT_PATTERN = java.util.regex.Pattern.compile("^\\d+$");

    /**
     * Pre-compiled regular expression for identifying double-precision floating-point numbers.
     */
    private static final java.util.regex.Pattern DOUBLE_PATTERN = java.util.regex.Pattern.compile("^\\d+(\\.\\d+)?$");

    private final Map<String, CommandFactory> registry;

    /**
     * Constructs a new InputParser and initializes the command registry.
     * Populates the internal map with all supported system commands and their
     * respective factory implementations.
     */
    public InputParser() {
        registry = new HashMap<>();
        registry.put("add", new AddCommandFactory());
        registry.put("remove", new RemoveCommandFactory());
        registry.put("list" ,new ListCommandFactory());
        registry.put("exit", new ExitCommandFactory());
        registry.put("recipe", new RecipeCommandFactory());
        registry.put("clear", new ClearCommandFactory());
        registry.put("update", new UpdateCommandFactory());
        registry.put("save", new SaveCommandFactory());
        registry.put("load", new LoadCommandFactory());
    }

    /**
     * Parses the raw user input string and routes it to the appropriate factory.
     * Splits the input to extract the primary command keyword, looks up the corresponding
     * factory in the registry, and delegates the construction of the {@link Command} object.
     *
     * @param input The raw, unparsed string entered by the user.
     * @return A fully instantiated {@link Command} object ready for execution.
     * @throws InvalidCommandException If the initial command keyword does not exist in the registry.
     */
    public Command parse(String input) {
        String[] parts = input.trim().split(" ");
        String command = parts[0];

        CommandFactory commandFactory = registry.get(command);
        if (commandFactory == null) {
            throw new InvalidCommandException("Invalid command");
        }
        return commandFactory.create(parts);

    }

}