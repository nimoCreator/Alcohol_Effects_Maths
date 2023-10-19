
package pl.polsl.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author nimo
 */
public class View {
    public void printToTxt(Map<String, String> results, String filename) {
        
        String finalFilename = String.format("%s %s.txt", filename, new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date()));

        try (PrintWriter writer = new PrintWriter(finalFilename)) 
        {
            results.forEach((key, value) -> writer.printf("%s: %s%n", key, value));
        } 
        catch (IOException ex) 
        {
            System.err.println("Error writing to the file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public void outputToConsole(Map<String, String> results) {
        for (Map.Entry<String, String> entry : results.entrySet()) 
        {
            System.out.printf("%s: %s%n", entry.getKey(), entry.getValue());
        }
    }
}
