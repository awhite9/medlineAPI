package controllers;

import models.CurrentMedCondition;
import models.HealthConditions;
import org.w3c.dom.Document;
import play.Logger;
import play.db.jpa.Transactional;
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
    //left these in because will need when hooking up the database and making queries
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public MedLineAPIController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result MedLineAPI()
    {
        //hard coded in the health conditions, will be pulling list of health conditions from database in DAC
        List<HealthConditions> fullSummery = new ArrayList<>();


        List<CurrentMedCondition> currentMedCondition = (List<CurrentMedCondition>) jpaApi.em().createNativeQuery("select mh.medical_history_id, mc.mc_name, mc.mc_description, mc.mc_url from medical_history mh\n" +
                "join medical_condition mc on mc.MEDICAL_CONDITION_ID = mh.MEDICAL_CONDITION_ID\n" +
                "where date_resolved is null", CurrentMedCondition.class).getResultList();

        for(CurrentMedCondition conditionList: currentMedCondition) {


            int indexPosition = 1;
            Document doc = null;
            try {
                //loops though, making a new API call for every health condition
                String myURL = "https://wsearch.nlm.nih.gov/ws/query?db=healthTopics&term=" + conditionList.name;

                URL url = new URL(myURL);

                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();
                //This contains all the xml info from the API
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

            } catch (Exception e) {
                Logger.error("oh no! got some exception: " + e.getMessage());
            }

            String fullSummaryString = doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(indexPosition).getTextContent();
            String readMoreURL = doc.getFirstChild().getChildNodes().item(13).getChildNodes().item(1).getAttributes().getNamedItem("url").getTextContent();
            HealthConditions summary = new HealthConditions(conditionList.name, fullSummaryString, readMoreURL);
            fullSummery.add(summary);
        }
        return ok(views.html.medLine.render(fullSummery));
    }
}

