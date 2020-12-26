import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Cache memory to store data from Livsmedelsverkets API every time a
 * klient connects, to keep it updated.
 *
 * @author Emma Svensson, Carin Loven
 * @version 1.0
 */
public class APICache {
    private FoodItemContainer foodItemObjectInMemory;
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
        //print();
    }

    /**
     * Fetches the XML data, from Livsmedelsverkets API, as StringBuffer. Converts the data
     * to a regular String and saves the data locally in variable dataAsXML.
     */
    public void fetchFromAPI() {
        Calendar calendar = Calendar.getInstance();
        url = ("http://www7.slv.se/apilivsmedel/LivsmedelService.svc/Livsmedel/Naringsvarde/"
                + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.DATE));
        System.out.println(url);

        try {
            URL urlConnection = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlConnection.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
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
    }

    /**
     * Unmarshals the XML to an executable Java object. The unmarshalling process
     * contains the five classes:
     * LivsmedelDataset --> LivsmedelsList --> Livsmedel --> Naringsvarden --> Narigsvarde
     */
    private void deserialization() {
        try {
            JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
            jacksonXmlModule.setDefaultUseWrapper(false);
            ObjectMapper xmlMapper = new XmlMapper(jacksonXmlModule);
            container = xmlMapper.readValue(dataAsXML, LivsmedelDataset.class);
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
            System.out.println(item.getNameOfItem());
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
     * @param name name of the FoodItem.
     * @return
     */
    public FoodItem getFoodItem(String name) {
        return foodItem.get(name);
    }

    /**
     * Prints all "Näringsvärden".
     */
    public void print() {
        LivsmedelsLista lista = container.getLivsmedelsLista();
        for (Livsmedel livsmedel : lista.getListOfLivsmedel()) {
            System.out.println(livsmedel.getNamn());
            Naringsvarden naring = livsmedel.getNaringsvarden();
            for (Naringsvarde food : naring.getListOfNaringsvarde()) {
                System.out.println("Innehåller " + food.getNamn() + ": " + food.getVarde());
            }
        }
    }
}