package shoppingList.Command;

import shoppingList.Model.ShoppingList;

/**
 * Fallback implementation of {@link Command} used when the parser
 * fails to identify a valid user request.
 */
public class InvalidCommand implements Command {

    private String command;

    /**
     * Default constructor initializing with a generic error message.
     */
    public InvalidCommand() {
        this.command = "Invalid Command";
    }

    /**
     * Constructs an InvalidCommand with a specific error message.
     *
     * @param command The specific error message to be displayed.
     */
    public InvalidCommand(String command) {
        this.command = command;
    }

    /**
     * Executes the error handling logic by notifying the user of the invalid input.
     *
     * @param shoppingList The current shopping list.
     * @return A {@link CommandResult} that keeps the application running.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {
        return new CommandResult(true, "Invalid Command");
    }
}