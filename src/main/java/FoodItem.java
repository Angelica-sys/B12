
/**
 * FoodItem is a class that represents a FoodItem in the application
 *
 * @author Emma Svensson
 * @version 1.0
 */
public class FoodItem {
    public String foodname = "";
    public int b12inFoodItem = 0;
    //Hashmap

    public FoodItem() {

    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getB12inFoodItem() {
        return b12inFoodItem;
    }

    public void setB12inFoodItem(int b12inFoodItem) {
        this.b12inFoodItem = b12inFoodItem;
    }
}
