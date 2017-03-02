package controllers;

import models.HealthConditions;
import org.w3c.dom.Document;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


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
        List<HealthConditions> fullSummery = new ArrayList<>();
        List<HealthConditions> healthConditions = new ArrayList<>();
        healthConditions.add(new HealthConditions("Asthma"));
        healthConditions.add(new HealthConditions("Diabetes"));
        healthConditions.add(new HealthConditions("Depression"));

        for(HealthConditions conditionList: healthConditions) {


            int indexPosition = 1;
            Document doc = null;
            try {
                String myURL = "https://wsearch.nlm.nih.gov/ws/query?db=healthTopics&term=" + conditionList.getName();

                URL url = new URL(myURL);

                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();

                doc = play.libs.XML.fromInputStream(request.getInputStream(), null);

                while (!doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(indexPosition).getAttributes().getNamedItem("name").getFirstChild().getTextContent().contentEquals("FullSummary"))
                {
                    //with MedLine even numbered nodes are null - so increment by 2 to avoid null pointer exception
                    indexPosition += 2;
                    if (indexPosition > doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().getLength())
                    {
                        //if none of the nodes in the content node have the name "FullSummary", break the while loop and set indexPosition to 1, avoiding blowup
                        indexPosition = 1;
                        break;
                    }
                }
                //added these in to keep up with the indexPosition and used to test comparison while working out function
                //will remove before adding to DAC
                System.out.println("API function Doc Stuff: " + doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(indexPosition).getAttributes().getNamedItem("name").getFirstChild().getTextContent());
                System.out.println("API indexPosition: " + indexPosition);
                if (doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(indexPosition).getAttributes().getNamedItem("name").getFirstChild().getTextContent().contentEquals("FullSummary")) {
                    System.out.println("Equal!!!!");
                }
            } catch (Exception e) {
                Logger.error("oh no! got some exception: " + e.getMessage());
            }
            HealthConditions summary = new HealthConditions(conditionList.getName(), doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(indexPosition).getTextContent());
            fullSummery.add(summary);
        }

        return ok(views.html.medLine.render(fullSummery));
    }
}

