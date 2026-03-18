package shoppingList.CommandFactory;

import shoppingList.Command.Command;
import shoppingList.Command.ListCommand;


/**
 * Factory responsible for creating commands that require no additional arguments.
 */
public class ListCommandFactory implements CommandFactory {
    @Override
    public Command create(String[] parts) {
        return new ListCommand();
    }
}
