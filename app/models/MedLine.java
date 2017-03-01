package models;


public class MedLine
{
    private NlmSearchResult nlmSearchResult;

    public NlmSearchResult getNlmSearchResult ()
    {
        return nlmSearchResult;
    }

    public void setNlmSearchResult (NlmSearchResult nlmSearchResult)
    {
        this.nlmSearchResult = nlmSearchResult;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [nlmSearchResult = "+nlmSearchResult+"]";
    }
}
