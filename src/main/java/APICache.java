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
 * klient connects, to keep it updated.
 *
 * @author Emma Svensson, Carin Loven, Angelica Asplund
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
        //fetchFromAPI();
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
                + calendar.get(Calendar.YEAR) + "0" + (calendar.get(Calendar.MONTH) + 1) + "0" + calendar.get(Calendar.DATE));
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
                System.out.println(inputLine);
                content.append(inputLine);
            }
            dataAsXML = content.toString();
            System.out.println(dataAsXML);
            in.close();
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");

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
            container = xmlMapper.readValue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<LivsmedelDataset>\n" +
                    "   <Version>2020-01-16T13:15:00.9</Version>\n" +
                    "   <LivsmedelsLista>\n" +
                    "      <Livsmedel>\n" +
                    "         <Naringsvarden>\n" +
                    "            <Naringsvarde>\n" +
                    "               <Namn>Vitamin B6</Namn>\n" +
                    "               <Forkortning>VitB6</Forkortning>\n" +
                    "               <Varde>0,020</Varde>\n" +
                    "               <Enhet>mg</Enhet>\n" +
                    "               <SenastAndrad>2006-01-09T14:02:41.247</SenastAndrad>\n" +
                    "               <Vardetyp>Bästa skattning</Vardetyp>\n" +
                    "               <Ursprung>Livsmedelstabell</Ursprung>\n" +
                    "               <Publikation>SLV&lt; eller1986</Publikation>\n" +
                    "               <Framtagningsmetod>Överfört från likvärdigt livsmedel</Framtagningsmetod>\n" +
                    "            </Naringsvarde>\n" +
                    "            <Naringsvarde>\n" +
                    "               <Namn>Folat</Namn>\n" +
                    "               <Forkortning>Folat</Forkortning>\n" +
                    "               <Varde>1,0</Varde>\n" +
                    "               <Enhet>µg</Enhet>\n" +
                    "               <SenastAndrad>2006-01-09T14:02:41.247</SenastAndrad>\n" +
                    "               <Vardetyp>Bästa skattning</Vardetyp>\n" +
                    "               <Ursprung>Livsmedelstabell</Ursprung>\n" +
                    "               <Publikation>SLV&lt; eller1986</Publikation>\n" +
                    "               <Framtagningsmetod>Överfört från likvärdigt livsmedel</Framtagningsmetod>\n" +
                    "            </Naringsvarde>\n" +
                    "            <Naringsvarde>\n" +
                    "               <Namn>Socker totalt</Namn>\n" +
                    "               <Forkortning>Mono/disack</Forkortning>\n" +
                    "               <Varde>0,0</Varde>\n" +
                    "               <Enhet>g</Enhet>\n" +
                    "               <SenastAndrad>2017-03-01T10:01:36.42</SenastAndrad>\n" +
                    "               <Vardetyp>Logisk nolla</Vardetyp>\n" +
                    "               <Ursprung>Annat ursprung</Ursprung>\n" +
                    "               <Publikation>SLV - Riktlinjer för livsmedel</Publikation>\n" +
                    "               <Metodtyp>Skattat från ingrediensförteckning</Metodtyp>\n" +
                    "               <Framtagningsmetod>Skattat logisk slutledning</Framtagningsmetod>\n" +
                    "               <Referenstyp>Rapport</Referenstyp>\n" +
                    "            </Naringsvarde>\n" +
                    "            <Naringsvarde>\n" +
                    "               <Namn>Fullkorn totalt</Namn>\n" +
                    "               <Forkortning>Fullk/tot</Forkortning>\n" +
                    "               <Varde>0</Varde>\n" +
                    "               <Enhet>g</Enhet>\n" +
                    "               <SenastAndrad>2010-06-21T13:41:49.363</SenastAndrad>\n" +
                    "               <Vardetyp>Bästa skattning</Vardetyp>\n" +
                    "               <Ursprung>Värde framtaget med eget system</Ursprung>\n" +
                    "               <Publikation>SLV - Riktlinjer för livsmedel</Publikation>\n" +
                    "               <Metodtyp>Skattat från ingrediensförteckning</Metodtyp>\n" +
                    "               <Framtagningsmetod>Summering av beståndsdelar</Framtagningsmetod>\n" +
                    "               <Referenstyp>Rapport</Referenstyp>\n" +
                    "            </Naringsvarde>\n" +
                    "            <Naringsvarde>\n" +
                    "               <Namn>Salt</Namn>\n" +
                    "               <Forkortning>NaCl</Forkortning>\n" +
                    "               <Varde>0,03</Varde>\n" +
                    "               <Enhet>g</Enhet>\n" +
                    "               <SenastAndrad>2010-06-21T13:41:49.363</SenastAndrad>\n" +
                    "               <Vardetyp>Bästa skattning</Vardetyp>\n" +
                    "               <Ursprung>Värde framtaget med eget system</Ursprung>\n" +
                    "               <Publikation>SLV - Riktlinjer för livsmedel</Publikation>\n" +
                    "               <Metodtyp>Salt beräknat från natrium (totalt)</Metodtyp>\n" +
                    "               <Framtagningsmetod>Beräknad, omräkningsfaktor inkluderad</Framtagningsmetod>\n" +
                    "               <Referenstyp>Rapport</Referenstyp>\n" +
                    "            </Naringsvarde>\n" +
                    "         </Naringsvarden>\n" +
                    "      </Livsmedel>\n" +
                    "   </LivsmedelsLista>\n" +
                    "</LivsmedelDataset>", LivsmedelDataset.class);
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
            //  System.out.println(item.getNameOfItem());
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