package pl.polsl.controller;

import java.util.ArrayList;
import pl.polsl.classes.ArgValues;
import pl.polsl.classes.ErrorHandler;
import pl.polsl.model.Model;
import pl.polsl.view.View;
import pl.polsl.classes.Results;

/**
 * Controls the flow of the Alcohol Effects application.
 * Reads command-line arguments, manages error handling,
 * and coordinates interactions between the model and view.
 * @author nimo
 */
public class Controller {
    

    private final ArgValues argValues = new ArgValues();
    private final ErrorHandler errorHandler = new ErrorHandler();
    
    private final Model model;
    private final View view;
    
    public Controller()
    {        
        model = new Model(argValues, errorHandler);
        view = new View(argValues, errorHandler);
        view.setModel(model);
    }
    
     /**
     * Reads and processes command-line arguments.
     * Handles user interaction for missing or incorrect arguments.
     * @param arguments The command-line arguments passed to the application.
     */
    public void readArguments(ArrayList<String> arguments)
    {
        if (arguments.isEmpty()) {
            errorHandler.addError(2, "You didn't include arguments while running the app.");
        } else {
            errorHandler.addError(1, "DEBUG INFO: Arguments: ");
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i).equals("-i") && ++i != arguments.size()) {
                    argValues.arg_input_path = arguments.get(i);
                    errorHandler.addError(1,"\t Input file: "+ argValues.arg_input_path);
                } else if (arguments.get(i).equals("-o") && ++i != arguments.size()) {
                    argValues.arg_output_path = arguments.get(i);
                    errorHandler.addError(1, "\t Output file: "+ argValues.arg_output_path);
                } else if (arguments.get(i).equals("-c")) {
                    argValues.arg_to_console = true;
                    errorHandler.addError(1, "\t C - console: " + ( argValues.arg_to_console ? "TRUE" : "FALSE" ) );
                }
            }
        }
        
    }
    
     /**
     * starts the default behavior of the program
     */
    public void run()
    {
        view.viewErrors();
        
        if (argValues.arg_input_path.equals("") || argValues.arg_output_path.equals("")) { view.handleNoArguments(); }   
        
        view.viewErrors();
        
    }
}
