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
 * Represents the model of the Alcohol Effects application. Manages data
 * processing, calculations, and data loading from CSV.
 *
 * @author nimo
 */
public class Model {

    private final ArgValues argValues;
    private final ErrorHandler errorHandler;
    private final Results results;
    private final ArrayList<StudentData> data;

    
    
    public Results getResults() 
    {
        return results;
    }
    
    public ArrayList<StudentData> getData() 
    {
        return data;
    }
    
    
    /**
     * Initializes the Model with argument values and an error handler. Also
     * initializes the results and data structures.
     *
     * @param argValues The argument values passed to the application.
     * @param errorHandler The error handler for managing application errors.
     */
    public Model(ArgValues argValues, ErrorHandler errorHandler) {
        this.argValues = argValues;
        this.errorHandler = errorHandler;
        this.results = new Results();

        data = new ArrayList<>();
    }

    /**
     * Loads student data from a CSV file specified in the command line
     * arguments. Handles file reading and parsing errors.
     */
    public void loadFromCSV() {
        FileReader fileReader;
        try {
            fileReader = new FileReader(argValues.arg_input_path);
        } catch (FileNotFoundException ex) {
            errorHandler.addError(101, "Model.loadFromCSV() could not find or parse the file.");
            return;
        }

        try (CSVReader csvReader = new CSVReader(fileReader)) {

            String[] headers = csvReader.readNext();

            if (headers != null) {
            }

            List<String[]> readData = csvReader.readAll();

            for (String[] row : readData) {
                data.add(new StudentData(row));
            }
        } catch (CsvException | IOException ex) {
            errorHandler.addError(102, "Model.loadFromCSV() could not find or parse the file.");
        }
    }

    /**
     * Calculates the Pearson correlation between gender and alcohol
     * consumption. This is the solution for task 1: "Obliczać korelację Pearsona pomiędzy płcią a konsumpcją alkoholu"
     */
    private void pearsonCorrelation() {
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

    /**
     * Sorts the weekly alcohol consumption by age and calculates the average
     * for each age group. This is the solution for task 2: "Sortować konsumpcję alkoholu w tygodniu wg wieku"
     */
    public void sortAlcoholByAge() {
        Map<Integer, Agregator<Integer>> dataset = new HashMap<>();
        for (StudentData student : data) {
            int age = student.getAge();
            int walc = student.getWalc();

            if (dataset.containsKey(age)) {
                dataset.get(age).add(walc);
            } else {
                dataset.put(age, new Agregator<>(Integer.toString(age), walc));
                dataset.get(age).add(walc);
            }
        }

        List<Map.Entry<Integer, Agregator<Integer>>> sortedEntries = new ArrayList<>(dataset.entrySet());
        sortedEntries.sort(Comparator.comparingDouble(entry -> entry.getValue().Average()));

        results.add("WeeklyAgeConsumption\nAge: \tAvgWalc < NumOfRecords > \t[ data ]", "", "");

        for (Map.Entry<Integer, Agregator<Integer>> entry : sortedEntries) {
            results.add(entry.getKey().toString(), "\t" + String.format("%.2f", entry.getValue().Average()), "\t< " + entry.getValue().Count() + " >\t" + entry.getValue().Data());
        }
    }

    /**
     * Displays the percentage change in weekend alcohol consumption based on
     * family relationships. This is the solution for task 3: "Sortować konsumpcję alkoholu w tygodniu wg wieku"
     */
    private void familyRelInfluenceWalc() {
        Map<Integer, Agregator<Integer>> dataset = new HashMap<>();
        for (StudentData student : data) {
            if (dataset.containsKey(student.getFamrel())) {
                dataset.get(student.getFamrel()).add(student.getWalc());
            } else {
                dataset.put(student.getFamrel(), new Agregator<>(Integer.toString(student.getFamrel()), student.getWalc()));
                dataset.get(student.getFamrel()).add(student.getWalc());
            }
        }

        double baselineAverage = data.stream().mapToInt(StudentData::getWalc).average().orElse(0);

        double totalWeekendAlcohol = dataset.values().stream()
                .mapToDouble(aggregator -> aggregator.Sum())
                .sum();
        int totalCount = dataset.values().stream()
                .mapToInt(aggregator -> aggregator.Count())
                .sum();

        double averageWeekendAlcohol = totalWeekendAlcohol / totalCount;
        double percentageChange = ((averageWeekendAlcohol - baselineAverage) / baselineAverage) * 100;

        results.add("baselineAverage", Double.toString(baselineAverage), "");
        results.add("totalWeekendAlcohol", Double.toString(totalWeekendAlcohol), "");
        results.add("totalCount", Integer.toString(totalCount), "");
        results.add("averageWeekendAlcohol", Double.toString(averageWeekendAlcohol), "");
        results.add("FamilyRelInfluenceWalc", Double.toString(percentageChange), "%");

    }

    /**
     * Displays the school with the highest percentage of girls with high
     * alcohol consumption. This is the solution for task 4: "Wyświetlać szkołę, w której odsetek dziewczyn cechujących się dużą konsumpcją alkoholu w tygodniu był najwyższy"
     */
    private void schoolMaxFemaleDrinking() {
        Map<String, Agregator<Integer>> dataset = new HashMap<>();

        for (StudentData student : data) {
            if (student.getSex().equalsIgnoreCase("F")) {
                String school = student.getSchool();
                int alcoholConsumption = (student.getDalc() + student.getWalc()) / 2;

                dataset.computeIfAbsent(school,
                        key -> new Agregator<>(key))
                        .add(alcoholConsumption);
            }
        }

        String schoolWithHighestAlcohol = "";
        double highestPercentage = 0.0;

        for (Map.Entry<String, Agregator<Integer>> entry : dataset.entrySet()) {
            int totalGirlsInSchool = entry.getValue().Count();
            int girlsWithHighAlcohol = (int) entry.getValue().Data().stream()
                    .filter(alcohol -> alcohol >= 5)
                    .count();

            double percentage = (double) girlsWithHighAlcohol / totalGirlsInSchool * 100;

            if (percentage > highestPercentage) {
                highestPercentage = percentage;
                schoolWithHighestAlcohol = entry.getKey();
            }
        }

        results.add("SchoolWithHighestAlcoholConsumption", schoolWithHighestAlcohol, "(" + String.format("%.2f%%", highestPercentage) + " )");
    }

    /**
     * Initiates the calculation of all specified analyses. Clears previous
     * results, performs calculations, and returns the results.
     *
     * @return The results of the analyses.
     */
    public Results calculateAll() {
        results.clear();

        this.pearsonCorrelation();
        this.separator();

        this.sortAlcoholByAge();
        this.separator();

        this.familyRelInfluenceWalc();
        this.separator();

        this.schoolMaxFemaleDrinking();

        return results;
    }

    /**
     * Adds a separator line to the results to improve readability.
     */
    public void separator() {
        this.results.add("--------------------------------", "", "");
    }

     /**
     * Checks if the provided gender is valid (either 'm' or 'f').
     * @param gender The gender to be checked.
     * @return True if the gender is valid, false otherwise.
     */
    public boolean isValidGender(String gender) {
        return "mf".contains(gender.toLowerCase());
    }
}
