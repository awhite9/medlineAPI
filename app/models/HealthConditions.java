package models;



public class HealthConditions
{
    private String name;
    private String summary;
    private String uRL;


    public HealthConditions(String name, String summary, String uRL)
    {
        this.name = name;
        this.summary = summary;
        this.uRL = uRL;
    }
    public String getName()
    {
        return name;
    }
    public  String getSummary()
    {
        return summary;
    }
    public String getuRL()
    {
        return uRL;
    }
}
