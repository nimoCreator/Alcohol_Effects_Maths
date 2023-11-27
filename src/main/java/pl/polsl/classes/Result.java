package pl.polsl.classes;

/**
 *
 * @author nimo
 */
public class Result
{
    private final String label;
    private final String value;
    private final String unit;

    public Result(String label, String value, String unit) {
        this.label = label;
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString()
    {
        return label + ": " + value + " " + unit.replace("%", "%%");
    }
    
    public String getName()
    {
        return this.label;
    }
    public String getValue()
    {
        return this.value;
    }
    public String getUnit()
    {
        return this.unit;
    }
}

