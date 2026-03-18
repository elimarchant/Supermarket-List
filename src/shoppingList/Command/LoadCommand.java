package shoppingList.Command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import shoppingList.Model.InventoryItem;
import shoppingList.Model.ShoppingList;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Concrete implementation of {@link Command} used to restore a shopping list
 * from a JSON file.
 */
public class LoadCommand implements Command {

    private final String textName;

    /**
     * Constructs a LoadCommand for a specific saved list.
     *
     * @param textName The name of the file (excluding the .json extension) to load.
     */
    public LoadCommand(String textName) {
        this.textName = textName;
    }

    /**
     * Executes the data restoration logic.
     * Verifies the existence of the physical file, reads the JSON content using Jackson,
     * and replaces the current in-memory list with the loaded data.
     *
     * @param shoppingList The domain model to be populated with loaded data.
     * @return A {@link CommandResult} indicating success or failure of the file operation.
     */
    @Override
    public CommandResult execute(ShoppingList shoppingList) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(textName + ".json");

        if (!file.exists()) {
            return new CommandResult(true, "Error: No saved list found with the name '" + textName + "'.");
        }

        try {
            List<InventoryItem> loadedItems = mapper.readValue(file, new TypeReference<List<InventoryItem>>() {});

            shoppingList.replaceAllItems(loadedItems);

            return new CommandResult(true, "Successfully loaded list: " + textName);

        } catch (IOException e) {
            return new CommandResult(true, "Error: Could not load the list. " + e.getMessage());
        }
    }
}