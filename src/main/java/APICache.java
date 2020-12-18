import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * In APICache we create a cache to store Livsmedelsverkets API every time a
 * klient connects, to keep it updated.
 *
 * @author Emma Svensson, Carin Loven
 * @version 1.0
 */
public class APICache {
    private FoodItemContainer foodItemObjectInMemory;

    public APICache() throws JsonProcessingException {
        FetchDataFromLivsmedelsverket(); //detta var enligt Johans kod
        //jag tänker vi borde kalla på invalidateCache() istället ???
    }

    /**
     * Gets data from Livsmedelsverket and saves it in local memory.
     */
    private void FetchDataFromLivsmedelsverket() throws JsonProcessingException {

        // Hämta data från extern tjänst, från livsmedelsverket
        String dataAsXML = "";

        XmlMapper xmlMapper = new XmlMapper();
        FoodItemContainer foodItemContainer
                = xmlMapper.readValue(dataAsXML, FoodItemContainer.class);
        foodItemObjectInMemory = foodItemContainer;
    }

    public FoodItem getFoodObject(int index) {
        return foodItemObjectInMemory.getFoodItemObject(index);
    }

    public void invalidateCache() throws JsonProcessingException {
        FetchDataFromLivsmedelsverket();
    }
}




