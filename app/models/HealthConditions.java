package models;



public class HealthConditions
{
    private String name;
    private String summary;

    public HealthConditions(String name)
    {
        this.name = name;
    }
    public HealthConditions(String name, String summary)
    {
        this.name = name;
        this.summary = summary;
    }
    public String getName()
    {
        return name;
    }
    public  String getSummary()
    {
        return summary;
    }
}
