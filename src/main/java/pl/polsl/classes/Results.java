package pl.polsl.classes;

import java.util.ArrayList;

class Result {
    private final String label;
    private final String value;
    private final String unit;

    public Result(String label, String value, String unit) {
        this.label = label;
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return label + ": " + value + " " + unit;
    }
}

public class Results {
    private final ArrayList<Result> resultsList;

    public Results() {
        resultsList = new ArrayList<>();
    }

    public void add(String label, String value, String unit) {
        Result result = new Result(label, value, unit);
        resultsList.add(result);
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        for (Result result : resultsList) {
            resultString.append(result.toString()).append("\n");
        }
        return resultString.toString();
    }

    public void clear() {
        resultsList.clear();
    }
}

