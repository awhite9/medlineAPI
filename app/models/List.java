package models;


import java.util.ArrayList;

public class List
{
    private String num;

    public java.util.List<Document> document = new ArrayList<>();

    //private Document[] document;

    private String per;

    private String start;

    public String getNum ()
    {
        return num;
    }

    public void setNum (String num)
    {
        this.num = num;
    }

    public java.util.List<Document> getDocument ()
    {
        return document;
    }

    public void setDocument (java.util.List<Document> document)
    {
        this.document = document;
    }

    public String getPer ()
    {
        return per;
    }

    public void setPer (String per)
    {
        this.per = per;
    }

    public String getStart ()
    {
        return start;
    }

    public void setStart (String start)
    {
        this.start = start;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [num = "+num+", document = "+document+", per = "+per+", start = "+start+"]";
    }
}
