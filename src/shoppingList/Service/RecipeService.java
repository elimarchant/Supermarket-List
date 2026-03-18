package shoppingList.Service;

import shoppingList.Model.InventoryItem;

import java.util.List;

/**
 * Defines the contract for an external recipe generation service.
 * Implementing classes are responsible for taking a desired recipe and
 * converting it into a standardized list of inventory items required for preparation.
 */
public interface RecipeService {

    /**
     * Retrieves a list of ingredients required to prepare a specific recipe.
     *
     * @param name     The name of the recipe to generate (e.g., "Chocolate Cake").
     * @param servings The desired number of servings to calculate quantities for.
     * @return A {@link List} of {@link InventoryItem} objects representing the required ingredients.
     */
    List<InventoryItem> getRecipe(String name, int servings);

}