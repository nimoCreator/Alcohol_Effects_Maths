package pl.polsl.classes;

/**
 *
 * @author nimo
 */
public class ArgValues {
    public String arg_input_path = "";
    public String arg_output_path = "";
    public boolean arg_to_console = false;

    public ArgValues(String arg_input_path, String arg_output_path, boolean arg_to_console) {
        this.arg_input_path = arg_input_path;
        this.arg_output_path = arg_output_path;
        this.arg_to_console = arg_to_console;
                
    }

    public ArgValues() {
        this.arg_input_path = "";
        this.arg_output_path = "";
        this.arg_to_console = false;
    }
}