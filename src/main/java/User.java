import java.util.ArrayList;

/**
 * User is a class that represents a user in the application
 *
 * @author Emma Svensson
 * @version 1.0
 */
public class User {
    public String name = "";
    public int id = 0;
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
    public int totalAmountB12 = 0;

    public User(){}

    public User(int id){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FoodItem> getListOfFoodItem() {
        return foodItems;
    }

    public void addFoodItem(FoodItem foodItem) {
        foodItems.add(foodItem);
    }

    public int getTotalAmountB12() {
        return totalAmountB12;
    }

    public void setTotalAmountB12(int totalAmountB12) {
        this.totalAmountB12 = totalAmountB12;
    }
}