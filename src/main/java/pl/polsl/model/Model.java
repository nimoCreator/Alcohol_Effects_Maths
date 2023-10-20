package pl.polsl.model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.polsl.classes.StudentData;
import pl.polsl.classes.Results;

/**
 *
 * @author nimo
 */
public class Model
{
    private final ArrayList<StudentData> data;
    
    public Model() 
    {
        data = new ArrayList<>();
    }
    
    public void loadFromCSV(String filename)
    {
        FileReader fileReader;
        try {
            System.out.println("filename: " + filename);
            fileReader = new FileReader(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Model.loadFromCSV() could not find or parse the file.");
            return;
        }

        try (CSVReader csvReader = new CSVReader(fileReader)) {
            
            String[] headers = csvReader.readNext(); 

            if (headers != null) { }

            List<String[]> readData = csvReader.readAll();

            for (String[] row : readData) {
                data.add(new StudentData(row));
            }
        } catch (CsvException | IOException ex) {
            System.out.println("Model.loadFromCSV() could not parse the file.");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Results calculateAll()
    {
        Results results = new Results();
        
        int n = data.size();

        int sumX = 0;
        int sumY = 0;

        for (StudentData student : data) {
            String gender = student.getSex();
            int alcoholConsumption = (student.getDalc() + student.getWalc()) / 2;
            if (gender != null && (gender.equals("M") || gender.equals("F"))) {
                int genderValue = gender.equals("M") ? 1 : 0;
                sumX += genderValue;
                sumY += alcoholConsumption;
            }
        }

        if (n == 0) {
            results.add("PearsonCorrelation", "No data available", "");
        } else {
            double meanX = (double) sumX / n;
            double meanY = (double) sumY / n;

            double numerator = 0.0;
            double denominatorX = 0.0;
            double denominatorY = 0.0;

            for (StudentData student : data) {
                String gender = student.getSex();
                if (gender != null && (gender.equals("M") || gender.equals("F"))) {
                    int genderValue = gender.equals("M") ? 1 : 0;
                    int alcoholConsumption = (student.getDalc() + student.getWalc()) / 2;
                    numerator += (genderValue - meanX) * (alcoholConsumption - meanY);
                    denominatorX += Math.pow((genderValue - meanX), 2);
                    denominatorY += Math.pow((alcoholConsumption - meanY), 2);
                }
            }

            if (denominatorX == 0 || denominatorY == 0) {
                results.add("PearsonCorrelation", "Undefined (division by zero)", "");
            } else {
                double correlation = numerator / (Math.sqrt(denominatorX) * Math.sqrt(denominatorY));
                results.add("PearsonCorrelation", String.valueOf(correlation), "");
            }
        }

        return results;
    }
}
