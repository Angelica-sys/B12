import java.util.HashMap;

/**
 * FoodItem is a class that represents a FoodItem in the application
 *
 * @author Emma Svensson
 * @version 1.0
 */
public class FoodItem {
    public String foodName;
    private double b12inFoodItem;
    private HashMap<String, Integer> hash = new HashMap<String, Integer>();

    public FoodItem() {
    }

    public void setNameOfItem(String foodName) {
        this.foodName = foodName;
    }

    public String getNameOfItem() {
        return foodName;
    }

    public double getB12inFoodItem() {
        return b12inFoodItem;
    }

    public void setB12inFoodItem(double b12inFoodItem) {
        this.b12inFoodItem = b12inFoodItem;
    }
}
