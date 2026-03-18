package shoppingList.Model;

/**
 * Defines the standardized units of measurement utilized within the supermarket inventory system.
 * Associates each unit with a specific string label for command parsing and receipt generation.
 */
public enum UnitType {
    Kilogram("kg"),
    Gram("g"),
    Liter("liter"),
    Milliliter("ml"),
    Piece("pc"),
    Box("box");

    private final String label;

    /**
     * Constructs a UnitType with its corresponding string representation.
     *
     * @param label The standardized string abbreviation representing this unit type.
     */
    UnitType(String label) {
        this.label = label;
    }



    /**
     * Resolves a UnitType based on a provided string label.
     * Iterates through all available enum constants and compares the input text
     * (case-insensitively) against each constant's assigned label.
     *
     * @param text The string representation of the unit type to search for.
     * @return The matching {@link UnitType} constant, or null if no valid match is found.
     */
    public static UnitType getLabel(String text) {
        for (UnitType unit : UnitType.values()) {
            if (unit.label.equalsIgnoreCase(text)) {
                return unit;
            }
        }
        return null;
    }
}