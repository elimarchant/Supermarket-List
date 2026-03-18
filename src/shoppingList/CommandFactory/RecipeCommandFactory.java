package shoppingList.CommandFactory;

import shoppingList.Command.Command;
import shoppingList.Command.RecipeCommand;
import shoppingList.Exceptions.InvalidCommandException;

/**
 * Factory responsible for parsing and constructing a {@link RecipeCommand}.
 * Validates servings counts and dynamically assembles multi-word recipe names
 * from the user's CLI input.
 */
public class RecipeCommandFactory implements CommandFactory {

    /**
     * Parses the input tokens to create a RecipeCommand.
     * Expects the last token to be an integer (servings) and all preceding
     * tokens (excluding the keyword) to form the recipe name.
     *
     * @param parts The tokenized string array from the user input.
     * @return A new {@link RecipeCommand} initialized with the recipe name and serving size.
     * @throws InvalidCommandException If parameters are missing, servings are non-numeric,
     * or the serving size falls outside the allowed 1-100 range.
     */
    @Override
    public Command create(String[] parts) {
        if (parts.length < 3) {
            throw new InvalidCommandException("Invalid parameters: expected syntax 'recipe <name> <servings>'");
        }

        int servings;
        try {
            servings = Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Invalid parameters: servings must be a valid number.");
        }

        if (servings < 1 || servings > 100) {
            throw new InvalidCommandException("Invalid parameters: Servings must be between 1 and 100.");
        }

        StringBuilder recipeName = new StringBuilder();
        for (int i = 1; i < parts.length - 1; i++) {
            recipeName.append(parts[i]);
            if (i < parts.length - 2) {
                recipeName.append(" ");
            }
        }

        return new RecipeCommand(recipeName.toString(), servings);
    }
}