
package pl.polsl.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.polsl.classes.Results;

/**
 *
 * @author nimo
 */
public class View {
    public void printToTxt(Results results, String filename) {
        
        String finalFilename = String.format("%s %s.txt", filename, new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date()));

        try (PrintWriter writer = new PrintWriter(finalFilename)) 
        {
            writer.printf(results.toString());
        } 
        catch (IOException ex) 
        {
            System.err.println("Error writing to the file: " + ex.getMessage());
        }
    }
    public void outputToConsole(Results results) {
            System.out.printf(results.toString());
    }
}
