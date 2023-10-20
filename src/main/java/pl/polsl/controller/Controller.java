package pl.polsl.controller;

import pl.polsl.model.Model;
import pl.polsl.view.View;
import pl.polsl.classes.Results;

/**
 *
 * @author nimo
 */
public class Controller {
    
    private String arg_input_path = "";
    private String arg_output_path = "";
    private Boolean arg_to_console = false;
    
    private final Model model;
    private final View view;
    
    public Controller(String arg_input_path, String arg_output_path, Boolean arg_to_console)
    {
        this.arg_input_path = arg_input_path;
        this.arg_output_path = arg_output_path;
        this.arg_to_console = arg_to_console;
        
        model = new Model();
        view = new View();
    }
    
    public void run()
    {
        model.loadFromCSV(arg_input_path);
        
        Results results = model.calculateAll();
        
        if(arg_to_console) view.outputToConsole( results );
        view.printToTxt( results, arg_output_path);
    }
}
