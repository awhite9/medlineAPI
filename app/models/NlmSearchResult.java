package models;


import java.util.List;

public class NlmSearchResult
{
    private String retstart;

    private String count;

    private String term;

    private String file;

    private String retmax;

    private String server;

    private List list;

    public String getRetstart ()
    {
        return retstart;
    }

    public void setRetstart (String retstart)
    {
        this.retstart = retstart;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String getTerm ()
    {
        return term;
    }

    public void setTerm (String term)
    {
        this.term = term;
    }

    public String getFile ()
    {
        return file;
    }

    public void setFile (String file)
    {
        this.file = file;
    }

    public String getRetmax ()
    {
        return retmax;
    }

    public void setRetmax (String retmax)
    {
        this.retmax = retmax;
    }

    public String getServer ()
    {
        return server;
    }

    public void setServer (String server)
    {
        this.server = server;
    }

    public List getList ()
    {
        return list;
    }

    public void setList (List list)
    {
        this.list = list;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [retstart = "+retstart+", count = "+count+", term = "+term+", file = "+file+", retmax = "+retmax+", server = "+server+", list = "+list+"]";
    }
}
