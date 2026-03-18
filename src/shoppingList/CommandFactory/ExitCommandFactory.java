package shoppingList.CommandFactory;

import shoppingList.Command.Command;
import shoppingList.Command.ExitCommand;


/**
 * Factory responsible for creating commands that require no additional arguments.
 */
public class ExitCommandFactory implements CommandFactory {
    @Override
    public Command create(String[] parts) {
        return new ExitCommand();
    }
}
