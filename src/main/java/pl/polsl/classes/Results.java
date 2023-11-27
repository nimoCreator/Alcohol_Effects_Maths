package pl.polsl.classes;

import java.util.ArrayList;
import java.util.List;

import pl.polsl.classes.Result;

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

    public Result getNthResult(int n)
    {
        if (n >= 0 && n < resultsList.size())
        {
            return resultsList.get(n);
        } else {
            throw new IndexOutOfBoundsException("Index " + n + " is out of bounds for the results list");
        }
    }

    public List<Result> getResultsList() 
    {
        return new ArrayList<>(resultsList);
    }
    
    public void clear() {
        resultsList.clear();
    }
}

