import java.util.ArrayList;

public class FoodItemContainer {
    ArrayList<FoodItem> fooditems;


    public FoodItemContainer() {

    }

    public FoodItem getFoodItemObject(int index) {
        return fooditems.get(index);
    }
}
