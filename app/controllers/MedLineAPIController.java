package controllers;

import org.w3c.dom.Document;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import play.data.FormFactory;
import play.db.jpa.JPAApi;

import javax.inject.Inject;


public class MedLineAPIController extends Controller
{

    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public MedLineAPIController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result MedLineAPI()
    {

        int indexPosition = 1;
        Document doc = null;
        try
        {
            String myURL = "https://wsearch.nlm.nih.gov/ws/query?db=healthTopics&term=creutzfeldtjakobdisease";

            URL url = new URL(myURL);

            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            doc = play.libs.XML.fromInputStream(request.getInputStream(), null);

            while (!doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(indexPosition).getAttributes().getNamedItem("name").getFirstChild().getTextContent().contentEquals("FullSummary"))
            {
                indexPosition+=2;
                if(indexPosition>30)
                {
                    indexPosition=1;
                    break;
                }
            }
            //added these in to keep up with the index positions
            System.out.println("API function Doc Stuff: "+doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(indexPosition).getAttributes().getNamedItem("name").getFirstChild().getTextContent());
            System.out.println("API indexPosition: "+indexPosition);
            if(doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(indexPosition).getAttributes().getNamedItem("name").getFirstChild().getTextContent().contentEquals("FullSummary"))
            {
                System.out.println("Equal!!!!");
            }
        } catch (Exception e)
        {
            Logger.error("oh no! got some exception: " + e.getMessage());
        }
        return ok(views.html.medLine.render(doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(indexPosition).getTextContent()));
    }
}

