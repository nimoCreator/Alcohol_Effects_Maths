package pl.polsl.classes;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author nimo
 * @param <T> Type of elements that are agregated, must extend Number.
 */
public class Agregator<T extends Number> {
    String label;
    Collection<T> data;
    Double average;
    Double sum;
    Integer count;
    
    public Agregator(String label, Collection<T> data)
    {
        this.label = label;
        this.data = data;
        this.count = data.size();
        this.sum = Double.valueOf(0);
        this.average = 0.0;
        
        for(T element : data)
        {
            sum += element.doubleValue();
        }
        
        average = sum / count;
    }

    public Agregator(String label) {
        this.label = label;
        this.data = (Collection<T>) new ArrayList<Double>();
        this.count = 0;
        this.sum = Double.valueOf(0);
        this.average = 0.0;
    }

    public void add(T element)
    {
        data.add(element);
        count++;
        sum += element.doubleValue();
        average = sum / count;
    }

    public String Label() {
        return label;
    }

    public Collection<T> Data() {
        return data;
    }

    public Double Average() {
        return average;
    }

    public Double Sum() {
        return sum;
    }

    public Integer Count() {
        return count;
    }
}
