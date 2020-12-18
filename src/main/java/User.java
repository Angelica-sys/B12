import java.util.ArrayList;

/**
 * User is a class that represents a user in the application
 *
 * @author Emma Svensson
 * @version 1.0
 */
public class User {
    public String name = "";
    public ArrayList<FoodItem> fooditems = new ArrayList<FoodItem>();
    public int totalAmountB12 = 0;


    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FoodItem> getFooditems() {
        return fooditems;
    }

    public void setFooditems(ArrayList<FoodItem> fooditems) {
        this.fooditems = fooditems;
    }

    public int getTotalAmountB12() {
        return totalAmountB12;
    }

    public void setTotalAmountB12(int totalAmountB12) {
        this.totalAmountB12 = totalAmountB12;
    }
}
