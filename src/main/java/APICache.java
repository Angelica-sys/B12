import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Cache memory to store data from Livsmedelsverkets API every time a
 * client connects, to keep it updated. Hej
 *
 * @author Emma Svensson, Carin Loven, Angelica Asplund
 * @version 1.0
 */
public class APICache {

    private String url;
    private StringBuffer content;
    private String dataAsXML;
    private LivsmedelDataset container;
    private HashMap<String, FoodItem> foodItem;

    public APICache() {
        foodItem = new HashMap<String, FoodItem>();
        content = new StringBuffer();
        fetchFromAPI();
        deserialization();
        createFoodItemObject();
    }

    /**
     * Fetches the XML data, from Livsmedelsverkets API, as StringBuffer. Converts the data
     * to a regular String and saves the data locally in variable dataAsXML.
     */
    public void fetchFromAPI() {
        Calendar calendar = Calendar.getInstance();
        url = ("http://www7.slv.se/apilivsmedel/LivsmedelService.svc/Livsmedel/Naringsvarde/"
                + calendar.get(Calendar.YEAR) + "0" + (calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.DATE));
        System.out.println(url);

        try {
            URL urlConnection = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlConnection.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println(status);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            dataAsXML = content.toString();
            in.close();
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish caching");
    }

    /**
     * Unmarshalls the XML to an executable Java object. The unmarshalling process
     * contains the five classes:
     * LivsmedelDataset --> LivsmedelsList --> Livsmedel --> Naringsvarden --> Narigsvarde
     */
    private void deserialization() {
        try {
            JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
            jacksonXmlModule.setDefaultUseWrapper(false);
            ObjectMapper xmlMapper = new XmlMapper(jacksonXmlModule);
            container = xmlMapper.readValue(dataAsXML, LivsmedelDataset.class);
            System.out.println("Finish unmarshalling");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates FoodItem, containing name of the item and amount of the vitamin b12.
     * Adds the FoodItem to an HashMap.
     */
    public void createFoodItemObject() {
        LivsmedelsLista lista = container.getLivsmedelsLista();

        for (Livsmedel livsmedel : lista.getListOfLivsmedel()) {
            FoodItem item = new FoodItem();
            item.setNameOfItem(livsmedel.getNamn());
            Naringsvarden naring = livsmedel.getNaringsvarden();
            for (Naringsvarde naringsvarde : naring.getListOfNaringsvarde()) {
                String str = "Vitamin B12";
                if (naringsvarde.getNamn().equals(str)) {
                    String replace = naringsvarde.getVarde().replace(",", ".");
                    item.setB12inFoodItem(Float.parseFloat(replace));
                }
            }
            foodItem.put(item.getNameOfItem(), item);
        }
    }

    /**
     * Fetches a requested FoodItem.
     *
     * @param name name of the FoodItem.
     * @return
     */
    public FoodItem getFoodItem(String name) {
        return foodItem.get(name);
    }

    /**
     * A testmetod that prints all "Näringsvärden".
     */
    public void print() {
        LivsmedelsLista lista = container.getLivsmedelsLista();
        for (Livsmedel livsmedel : lista.getListOfLivsmedel()) {
            System.out.println(livsmedel.getNamn());
            FoodItem item = foodItem.get(livsmedel.getNamn());
            System.out.println(item.getB12inFoodItem());
        }
    }
}