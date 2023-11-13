
package pl.polsl.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import pl.polsl.classes.ArgValues;
import pl.polsl.classes.ErrorHandler;

import pl.polsl.classes.Results;

/**
 * Displays the output of the Alcohol Effects application.
 * Handles printing results to the console and writing to a text file.
 * @author nimo
 */
public class View {
    
    private final ArgValues argValues;
    private final ErrorHandler errorHandler;
    
    public View(ArgValues argValues, ErrorHandler errorHandler) 
    {
        this.argValues = argValues;
        this.errorHandler = errorHandler;
    }
    
    /**
    * Writes the results to a text file with a timestamped filename.
    * If an error occurs during file writing, logs an error message.
    * @param results The results to be written to the text file.
    */
    public void printToTxt(Results results) {
        
        String finalFilename = String.format("%s %s.txt", argValues.arg_output_path, new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date()));

        try (PrintWriter writer = new PrintWriter(finalFilename)) 
        {
            writer.printf(results.toString());
        } 
        catch (IOException ex) 
        {
            errorHandler.addError(103, "Error writing to the file: " + ex.getMessage());
        }
    }
    
    /**
     * Prints the results to the console.
     * @param results The results to be printed.
     */
    public void outputToConsole(Results results) {
        System.out.println("========================\n        Resoults\n========================");
        System.out.printf(results.toString());
    }
    
    /**
    * Prints any errors stored in the error handler to the standard error stream.
    */
    public void viewErrors()
    {
        if(errorHandler.count() > 0)
            System.err.println(errorHandler.popAllAsString());
    }
    
    /**
    * Handles user interaction when there are no or insufficient command-line arguments.
    * Prompts the user to provide input for missing arguments.
    * Reads input from the console to set input paths and switches.
    */
    public void handleNoArguments() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                           The arguments are 
                           \t-i [filename]
                           \t-o [filename]
                           \t-c (output to console)
                           Please provide the following:
                           """);

        if (argValues.arg_input_path.equals("")) {
            System.out.print("Input (relative/absolute path and filename)\n -i: ");
            argValues.arg_input_path = scanner.nextLine();
        }

        if (argValues.arg_output_path.equals("")) {
            System.out.print("Output (relative/absolute path and filename)\n -o: ");
            argValues.arg_output_path = scanner.nextLine();
        }

        System.out.print("Switches (write the letters)\n: ");
        String input = scanner.nextLine();
        if (input.toLowerCase().contains("c")) {
            argValues.arg_to_console = true;
        }
    }
}
