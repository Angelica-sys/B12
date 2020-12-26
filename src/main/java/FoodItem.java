import java.util.HashMap;

/**
 * FoodItem is a class that represents a FoodItem in the application
 *
 * @author Emma Svensson, Carin Loven
 * @version 1.0
 */
public class FoodItem {
    public String foodName;
    private float b12inFoodItem;
    private HashMap<String, Integer> hash = new HashMap<String, Integer>();;

    public FoodItem() {}

    public void setNameOfItem(String foodName) {
        this.foodName = foodName;
    }

    public String getNameOfItem() {
        return foodName;
    }

    public float getB12inFoodItem() {
        return b12inFoodItem;
    }

    public void setB12inFoodItem(float b12inFoodItem) {
        this.b12inFoodItem = b12inFoodItem;
    }
}
