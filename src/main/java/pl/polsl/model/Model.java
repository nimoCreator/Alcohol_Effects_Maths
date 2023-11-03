package pl.polsl.model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.polsl.classes.ArgValues;
import pl.polsl.classes.ErrorHandler;
import pl.polsl.classes.StudentData;
import pl.polsl.classes.Results;
import pl.polsl.classes.Agregator;

/**
 *
 * @author nimo
 */
public class Model
{
    private final ArgValues argValues;
    private final ErrorHandler errorHandler;
    private final Results results;
    private final ArrayList<StudentData> data;
    
    public Model(ArgValues argValues, ErrorHandler errorHandler) 
    {
        this.argValues = argValues;
        this.errorHandler = errorHandler;
        this.results = new Results();
        
        data = new ArrayList<>();
    }
    
    public void loadFromCSV()
    {
        FileReader fileReader;
        try {
            fileReader = new FileReader(argValues.arg_input_path);
        } catch (FileNotFoundException ex) {
            errorHandler.addError(101, "Model.loadFromCSV() could not find or parse the file.");
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
            errorHandler.addError(102, "Model.loadFromCSV() could not find or parse the file.");
        }
    }
    
    // · Sortować konsumpcję alkoholu w tygodniu wg wieku
    public void sortAlcoholByAge() {
        Map<Integer, Agregator<Integer>> dataset = new HashMap<>();
        for (StudentData student : data) {
            int age = student.getAge();
            int walc = student.getWalc();

            if (dataset.containsKey(age)) {
                dataset.get(age).add(walc);
            } else {
                dataset.add(age, new Agregator<Integer>(age, new ArrayList<Integer>(walc)))
            }
        }

        List<Map.Entry<Integer, List<Integer>>> sortedEntries = new ArrayList<>(ageToConsumption.entrySet());
        sortedEntries.sort(Comparator.comparingDouble(entry -> entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0)));

        results.add("WeeklyAgeConsumption\nAge: \tAvgWalc < NumOfRecords > \t[ data ]", "", "");

        for (Map.Entry<Integer, List<Integer>> entry : sortedEntries) {
            results.add(entry.getKey().toString(), "\t" + String.format("%.2f", entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0)), "\t< " + entry.getValue().size() + " >\t" + entry.getValue());
        }
    }
    
    // · Obliczać korelację Pearsona pomiędzy płcią a konsumpcją alkoholu
    private void pearsonCorrelation()
    {
        int n = data.size();
        int sumX = 0, sumY = 0;

        for (StudentData student : data) {
            String gender = student.getSex();
            int alcoholConsumption = (student.getDalc() + student.getWalc()) / 2;

            if (isValidGender(gender)) {
                sumX += "mf".indexOf(gender.toLowerCase());
                sumY += alcoholConsumption;
            }
        }

        if (n == 0) {
            results.add("PearsonCorrelation", "No data available", "");
        } else {
            double meanX = (double) sumX / n;
            double meanY = (double) sumY / n;
            double numerator = 0, denominatorX = 0, denominatorY = 0;

            for (StudentData student : data) {
                String gender = student.getSex();
                int alcoholConsumption = (student.getDalc() + student.getWalc()) / 2;

                if (isValidGender(gender)) {
                    int genderValue = "mf".indexOf(gender.toLowerCase());
                    numerator += (genderValue - meanX) * (alcoholConsumption - meanY);
                    denominatorX += Math.pow(genderValue - meanX, 2);
                    denominatorY += Math.pow(alcoholConsumption - meanY, 2);
                }
            }

            if (denominatorX == 0 || denominatorY == 0) {
                results.add("PearsonCorrelation", "Undefined (division by zero)", "");
            } else {
                double correlation = numerator / (Math.sqrt(denominatorX) * Math.sqrt(denominatorY));
                results.add("PearsonCorrelation", String.valueOf(correlation), "");
            }
        }
    }
    
    public Results calculateAll()
    {
        results.clear();
        
        this.pearsonCorrelation();
        this.separator();
        this.sortAlcoholByAge();
        
        
        return results;
    }
    
    public void separator()
    {
        this.results.add("--------------------------------", "", "");
    }
    
        

    public void calculateWeekendAlcoholChange() {

        Map<Integer, Double> changeByFamilyRelationship = new HashMap<>();
        for (StudentData student : data) {
            Integer familyRelationship = student.getFamrel();
            double weekendAlcohol = student.getWalc();

            changeByFamilyRelationship.put(familyRelationship, changeByFamilyRelationship.getOrDefault(familyRelationship, 0.0) + weekendAlcohol);
        }

        for (Map.Entry<Integer, Double> entry : changeByFamilyRelationship.entrySet()) {
            results.add("WeekendAlcoholChange", entry.getKey().toString(), String.format("%.2f", entry.getValue()));
        }
    }
    
    public void findSchoolWithHighestAlcoholConsumption()
    {
        String schoolWithHighestAlcohol = "";
        double highestPercentage = 0.0;

        for (String school : getUniqueSchools()) {
            int totalGirlsInSchool = countGirlsInSchool(school);
            int girlsWithHighAlcohol = countGirlsWithHighAlcoholInSchool(school);

            double percentage = (double) girlsWithHighAlcohol / totalGirlsInSchool * 100;

            if (percentage > highestPercentage) {
                highestPercentage = percentage;
                schoolWithHighestAlcohol = school;
            }
        }
    }

    private List<String> getUniqueSchools() {
        List<String> schools = new ArrayList<>();
        for (StudentData student : data) {
            if (!schools.contains(student.getSchool())) {
                schools.add(student.getSchool());
            }
        }
        return schools;
    }

    private int countGirlsInSchool(String school) {
        int count = 0;
        for (StudentData student : data) {
            if (student.getSchool().equals(school) && student.getSex().equalsIgnoreCase("F")) {
                count++;
            }
        }
        return count;
    }

    private int countGirlsWithHighAlcoholInSchool(String school) {
        int count = 0;
        for (StudentData student : data) {
            if (student.getSchool().equals(school) && student.getSex().equalsIgnoreCase("F")) {
                int alcoholConsumption = (student.getDalc() + student.getWalc()) / 2;
                if (alcoholConsumption >= 5) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public boolean isValidGender(String gender) {
        return "mf".contains(gender.toLowerCase());
    }
}
