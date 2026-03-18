package shoppingList.CommandFactory;

import shoppingList.Command.ClearCommand;
import shoppingList.Command.Command;


/**
 * Factory responsible for creating commands that require no additional arguments.
 */
public class ClearCommandFactory implements CommandFactory {
    @Override
    public Command create(String[] parts) {
        return new ClearCommand();
    }
}
