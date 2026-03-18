# Supermarket Management System: Shopping List Architecture

### Overview:

- This project serves as the foundational architecture for a centralized Supermarket Shopping List
    and Inventory Management System. Operating via a robust Command-Line Interface (CLI),
    it allows users to manage InventoryItem entities, track quantities by UnitType,
    persist data using JSON, and dynamically generate ingredients
    via integration with the Google Gemini API.
    The codebase strictly adheres to Object-Oriented Programming (OOP)
    principles and industry-standard Design Patterns to ensure maintainability,
    scalability, and adherence to the Single Responsibility Principle (SRP).
    
#### Architectural Highlights & Design Patterns

The system's architecture is decoupled into distinct layers - Models, Parsers, Commands,
  and Services - utilizing the following patterns:

- **Command Pattern (shoppingList.Command)**:
_Why_: To decouple the object that invokes the operation from the one that knows how to perform it.
_How_: User inputs are encapsulated into objects (e.g., AddCommand, SaveCommand, RecipeCommand)
that implement a unified Command interface, returning a standard CommandResult.

- **Factory Method Pattern** (shoppingList.CommandFactory):
_Why_: To delegate the complex instantiation logic of commands
(including regex parsing and validation) away from the main parser.
_How_: The InputParser maintains a registry of CommandFactory implementations.
When a command string is identified, the corresponding factory handles the safe
construction of the Command object.

- **Service Layer & Facade (shoppingList.Service):**
_Why_: To isolate external API logic and complex JSON manipulations from the core domain model.
_How_: GeminiRecipeService handles HTTP client generation, prompt construction,
and the deserialization of AI-generated JSON into internal InventoryItem domain entities.

- **Encapsulation**: * Domain entities like ShoppingList protect their internal state
(e.g., returning Collections.unmodifiableList(list)) ensuring that state mutations only occur
through strictly defined business rules (like quantity aggregation).


#### Project Structure:

**shoppingList.Model**: Contains the core domain entities (Product, InventoryItem, ShoppingList, UnitType).

**shoppingList.Parser:** Contains the InputParser responsible for initial string sanitization and routing to the correct factory.

**shoppingList.Command / CommandFactory:** Houses the interface and concrete implementations for all system operations (Add, Remove, Save, Load, Recipe, etc.).

**shoppingList.Service**: Contains integration logic for external APIs (Gemini).

**shoppingList.Exceptions**: Custom runtime exceptions (InvalidCommandException, ItemNotFoundException) for precise error handling.

### Features:

**Dynamic Parsing**: Intelligently parses complex string inputs into structured InventoryItem objects using RegEx.

**Smart Aggregation**: Automatically merges quantities of identical products with matching unit types.

**Persistence**: Serializes the current ShoppingList state to standard .json files via the Jackson library (save / load commands).

**AI Recipe Integration**: Prompts the Gemini API to formulate recipes into exact inventory requirements, including estimated local pricing and standard unit types.

### Setup and Configuration: Gemini API

To utilize the AI recipe command, the system requires a connection to the Google Gemini API. You must provide your own API key and configure it within your system's environment variables.

##### How to configure the API Key:

 
The GeminiRecipeService is designed to securely fetch your API key from your system's environment variables rather than hardcoding it into the application.

**Obtain a Key**: Generate an API key from Google AI Studio.

Set Environment Variable:

- **Windows** (Command Prompt): setx GEMINI_API_KEY "your_api_key_here"

- **Mac/Linux** (Terminal): Add export GEMINI_API_KEY="your_api_key_here" to your ~/.bashrc or ~/.zshrc file.

- **IDE Configuration**: If you are running the project from an IDE (like IntelliJ IDEA or Eclipse), you will need to add GEMINI_API_KEY to the Environment Variables section of your Run/Debug Configuration.

(**Important Note**: If this variable is missing or empty, executing the recipe command will result in a RecipeGenerationException.)

### Usage Example

Upon running the Main class, you can interact with the system using natural syntax:

    > add apple 5 pc 2.50
    Successfully added item
    > recipe "chocolate cake" 4
    Successfully added items from recipe to list
    > list
    Shopping list:
    apple , 5.0 pcs. Price : 12.50
    flour , 1.0 kg. Price : 5.00
    ...
    > save myWeeklyShop
    List 'myWeeklyShop' was saved successfully.