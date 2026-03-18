package shoppingList.Main;

import shoppingList.Command.Command;
import shoppingList.Command.CommandResult;
import shoppingList.Exceptions.InvalidCommandException;
import shoppingList.Model.ShoppingList;

import shoppingList.Parser.InputParser;

import java.util.Scanner;

/**
 * Orchestrates the Command Line Interface (CLI) for the Shopping List application.
 * Serves as the primary controller, managing the application lifecycle,
 * user session, and the central Read-Eval-Print Loop (REPL).
 */
public class Main {

    /**
     * Initializes the core system components and starts the main interactive input loop.
     * Continuously reads user input from the standard input stream, delegates parsing
     * to the {@link InputParser}, executes the resulting {@link Command}, and handles
     * any standard command validation exceptions safely.
     */
    private void checkInput() {
        ShoppingList shoppingList = new ShoppingList();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        InputParser inputParser = new InputParser();

        while (running) {
            String input = scanner.nextLine().toLowerCase();
            try{
                Command command  = inputParser.parse(input);
                CommandResult result = command.execute(shoppingList);
                System.out.println(result.getResponse());
                running = result.getKeepRunning();

            }
            catch (InvalidCommandException e){
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * The main entry point of the Java application.
     * Displays the initial greeting and instructions to the user, then instantiates
     * the main application controller to begin processing input.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        System.out.println("-----Welcome to Shopping List -----");
        System.out.println("what would you like to do?");
        System.out.println("Write in format of: [Command] [What] ");
        System.out.println("For example: add apples 2 kg 3.90. ");
        System.out.println("             remove 2 bananas. ");

        new Main().checkInput();
    }
}