import java.util.HashMap;

/**
 * FoodItem is a class that represents a FoodItem in the application
 *
 * @author Emma Svensson
 * @version 1.0
 */
public class FoodItem {
    public String foodname;
    private int b12inFoodItem;
    private HashMap<String, Integer> hash = new HashMap<String, Integer>();;

    public FoodItem() {}

    public String getNameOfItem() {
        return foodname;
    }

    public void setNameOfItem(String foodname) {
        this.foodname = foodname;
    }

    public int getB12inFoodItem() {
        return b12inFoodItem;
    }

    public void setB12inFoodItem(int b12inFoodItem) {
        this.b12inFoodItem = b12inFoodItem;
    }
}
