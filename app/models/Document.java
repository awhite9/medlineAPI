package models;


import java.util.ArrayList;

public class Document
{
    public java.util.List<Content> content = new ArrayList<>();
    //private Content[] content;

    private String rank;

    private String url;

    public java.util.List<Content> getContent ()
    {
        return content;
    }

    public void setContent (java.util.List<Content> content)
    {
        this.content = content;
    }

    public String getRank ()
    {
        return rank;
    }

    public void setRank (String rank)
    {
        this.rank = rank;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", rank = "+rank+", url = "+url+"]";
    }
}
