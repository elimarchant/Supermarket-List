package shoppingList.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;


import shoppingList.Model.ShoppingList;

/**
 * Concrete implementation of {@link Command} used to persist the current
 * state of the shopping list to a local JSON file.
 */
public class SaveCommand implements  Command {
    private final String textName;

    /**
     * Constructs a SaveCommand with the specified filename.
     *
     * @param textName The identifier for the saved list.
     */
    public SaveCommand(String textName) {
        this.textName = textName;
    }

    /**
     * Executes the serialization logic.
     * Automatically appends the ".json" extension to the filename to ensure
     * standardized data formatting. Catching {@link IOException} ensures
     * that file permission or storage errors do not interrupt the CLI session.
     *
     * @param shoppingList The domain model whose items are to be saved.
     * @return A {@link CommandResult} confirming the file was saved or reporting an error.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(textName + ".json");

        try {
            mapper.writeValue(file, shoppingList.getItemList());

            return new CommandResult(true, "List '" + textName + "' was saved successfully.");

        } catch (IOException e) {
            return new CommandResult(true, "Error: Could not save the list. " + e.getMessage());
        }
    }
}