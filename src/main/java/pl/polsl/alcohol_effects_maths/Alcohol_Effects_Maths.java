package pl.polsl.alcohol_effects_maths;

import java.util.Scanner;
import pl.polsl.controller.Controller;

/**
 *
 * @author nimo - aka - Sebastian Legierski InfKat32
 * 
 * 
 * arguments example:
 * 
 * lol 12  s -c -i C:\Users\nimo\Desktop\Alcohol_Effects_Maths\Alcohol_Effects_Maths\Maths.csv fus9hd 124 -o C:\Users\nimo\Desktop\Alcohol_Effects_Maths\output 54807 duofh apos xD
 * 
 * -c -i C:\Users\nimo\Desktop\Alcohol_Effects_Maths\Alcohol_Effects_Maths\Maths.csv -o C:\Users\nimo\Desktop\Alcohol_Effects_Maths\output
 * 
 */



public class Alcohol_Effects_Maths {

    private static class ArgValues {
        public String arg_input_path = "";
        public String arg_output_path = "";
        public boolean arg_to_console = false;
    }

    private static final ArgValues arg_values = new ArgValues();

    private static void NoArguments() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                           The arguments are 
                           \t-i [filename]
                           \t-o [filename]
                           \t-c (output to console)
                           Please provide the following:
                           """);

        if (arg_values.arg_input_path.equals("")) {
            System.out.print("Input (relative/absolute path and filename)\n -i: ");
            arg_values.arg_input_path = scanner.nextLine();
        }

        if (arg_values.arg_output_path.equals("")) {
            System.out.print("Output (relative/absolute path and filename)\n -o: ");
            arg_values.arg_output_path = scanner.nextLine();
        }

        System.out.print("Switches (write the letters)\n: ");
        String input = scanner.nextLine();
        if (input.toLowerCase().contains("c")) {
            arg_values.arg_to_console = true;
        }
    }

    public static void main(String[] args) {
        int numberOfParameters = args.length;

        if (numberOfParameters == 0) {
            System.out.println("You didn't include arguments while running the app.");
            NoArguments();
        } else {
            System.out.println("Arguments: ");
            for (int i = 0; i < numberOfParameters; i++) {
                if (args[i].equals("-i") && ++i != numberOfParameters) {
                    arg_values.arg_input_path = args[i];
                    System.out.println("\t Input file: "+ arg_values.arg_input_path);
                } else if (args[i].equals("-o") && ++i != numberOfParameters) {
                    arg_values.arg_output_path = args[i];
                    System.out.println("\t Output file: "+ arg_values.arg_output_path);
                } else if (args[i].equals("-c")) {
                    arg_values.arg_to_console = true;
                    System.out.println("\t C - console: " + ( arg_values.arg_to_console ? "TRUE" : "FALSE" ) );
                }
            }
            if (arg_values.arg_input_path.equals("") || arg_values.arg_output_path.equals("")) { NoArguments(); }
        }

        Controller controller = new Controller(arg_values.arg_input_path, arg_values.arg_output_path, arg_values.arg_to_console);
        controller.run();
    }
}



/*

TREŚĆ

16. Badanie wpływu alkoholu na wyniki w nauce matematyki

Aplikacja powinna umożliwiać przeprowadzenie analizy statystycznej dotyczącej badania wpływu alkoholu na efekty osiągane w nauce. Aplikacja powinna:

· Obliczać korelację Pearsona pomiędzy płcią a konsumpcją alkoholu

· Sortować konsumpcję alkoholu w tygodniu wg wieku

· Wyświetlać, w jaki sposób [%] zmienia się weekendowa konsumpcja alkoholu w zależności od relacji rodzinnych

· Wyświetlać szkołę, w której odsetek dziewczyn cechujących się dużą konsumpcją alkoholu w tygodniu był najwyższy.


*/