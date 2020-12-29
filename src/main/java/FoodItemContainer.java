import java.util.ArrayList;

/**
 * FoodItem class
 *
 * @author Emma Svensson
 * @version 1.0
 */

public class FoodItemContainer {
    ArrayList<FoodItem> fooditems;


    public FoodItemContainer() {

    }

    public FoodItem getFoodItemObject(int index) {
        return fooditems.get(index);
    }
}
