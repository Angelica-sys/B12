import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * In APICache we create a cache to store Livsmedelsverkets API every time a
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

    public APICache (){
        content = new StringBuffer();
        fetchFromAPI();
        // deserialization();
    }

    /**
     * Gets data from Livsmedelsverket and saves it in local memory.
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
            System.out.println(dataAsXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deserialization() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            FoodItemContainer foodItemContainer
                    = xmlMapper.readValue(dataAsXML, FoodItemContainer.class);
            foodItemObjectInMemory = foodItemContainer;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FoodItem getFoodObject(int index) {
        return foodItemObjectInMemory.getFoodItemObject(index);
    }

    public static void main(String[] args){
        APICache cache = new APICache();
    }
}




