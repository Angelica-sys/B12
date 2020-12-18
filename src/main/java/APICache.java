

/**
 * In APICache we create a cache to store Livsmedelsverkets API every time a
 * klient connects, to keep it updated.
 *
 * @author Emma Svensson, Carin Loven
 * @version 1.0
 */
public class APICache {

    private FoodItemContainer foodItemObjectInMemory;  //Vad ska denna klassen vara/Resresentera egentligen?

    public APICache() {
        // När vi skapar cachen, fyll den med livsmedelsverkets api
        FetchDataFromLivsmedelsverket(); //detta var enligt Johans kod
        //jag tänker vi borde kalla på invalidateCache() istället ???
    }

    /**
     * Gets data from Livsmedelsverket and saves it in local memory.
     */
    private void FetchDataFromLivsmedelsverket() {
        // Hämta data från extern tjänst, frånm livsmedelsverket
        String dataAsXML;
        FoodItemContainer foodItemContainer = Jackson.fromXML(dataAsJson, FoodItemContainer.class);

        // Ett alternativ är att ni bara sparar er data i minnet.
        foodItemObjectInMemory = foodItemContainer;   // varför gör vi detta?
    }

    public FoodItem getFoodObject(int index) {

        // Om vi använder minnet
        return foodItemObjectInMemory.getFoodItemObject(index);
    }

    public void invalidateCache() {
        FetchDataFromLivsmedelsverket();
    }
}




