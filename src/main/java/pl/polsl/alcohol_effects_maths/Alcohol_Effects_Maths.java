package pl.polsl.alcohol_effects_maths;


import java.util.ArrayList;
import java.util.List;
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
 * -c -i C:\Users\nimo\Documents\NetBeansProjects\Alcohol_Effects_Maths\input_data\Maths.csv -o C:\Users\nimo\Documents\NetBeansProjects\Alcohol_Effects_Maths\output_data\resoults
 * 
 */

public class Alcohol_Effects_Maths {

    public static void main(String[] args) {    
        
        ArrayList<String> arguments = new ArrayList<>(List.of(args));

        Controller controller = new Controller();
        controller.readArguments(arguments);
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


/*

POPRAWKI 1 z dnia 2023-10-23

- nie powinno być innych metod statycznych poza metodą main()

- za dużo pakietów

- System.out.println powinno być w Widoku


*/