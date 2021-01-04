import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Test class for ConnectionToDatabas, APICache
 * @author Carin Loven
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        APICache cache = new APICache();

        String Username = "Sven ";
        int id = 0;
        FoodItem item1 = new FoodItem();
        item1.setNameOfItem("äpple");
        item1.setB12inFoodItem(33);
        FoodItem item2 = new FoodItem();
        item2.setNameOfItem("päron");
        item2.setB12inFoodItem(2);
        FoodItem item3 = new FoodItem();
        item3.setNameOfItem("banan");
        item3.setB12inFoodItem(99);

        User user = new User();
        user.setName(Username);
        user.setId(id);
        user.addFoodItem(item1);
        user.addFoodItem(item2);
        user.addFoodItem(item3);

        String Username1 = "Emma";
        int id1 = 1;
        FoodItem item4 = new FoodItem();
        item1.setNameOfItem("p");
        item1.setB12inFoodItem(33);
        FoodItem item5 = new FoodItem();
        item2.setNameOfItem("r");
        item2.setB12inFoodItem(2);
        FoodItem item6 = new FoodItem();
        item3.setNameOfItem("t");
        item3.setB12inFoodItem(99);

        User user99 = new User();
        user99.setName(Username1);
        user99.setId(id1);
        user99.addFoodItem(item4);
        user99.addFoodItem(item5);
        user99.addFoodItem(item6);

        ConnectingToDatabase connectingToDatabase = new ConnectingToDatabase();
        connectingToDatabase.createTables();
        connectingToDatabase.addToTableUsers(user);
        connectingToDatabase.addToTableUserEat(user);
        connectingToDatabase.addToTableUsers(user99);
        connectingToDatabase.addToTableUserEat(user99);

        for (User users : connectingToDatabase.fetchUserList()) {
            System.out.print(users.getName());
            System.out.println();
            System.out.print(users.getId());
            System.out.println();
        }
        User p = connectingToDatabase.fetchFromTableUser(1);

        String name1 = p.getName();
        System.out.println("Name: " + name1);
        for (FoodItem item : p.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            float b12 = item.getB12inFoodItem();
            System.out.println(itemName + ", " + b12);
        }

        User u = new User();
        u.setName(Username);

        FoodItem item8 = new FoodItem();
        item8.setNameOfItem("macka");
        item8.setB12inFoodItem(1);
        u.addFoodItem(item8);
        FoodItem item9 = new FoodItem();
        item9.setNameOfItem("kaviar");
        item9.setB12inFoodItem(32);
        u.addFoodItem(item9);
        FoodItem item7 = new FoodItem();
        item7.setNameOfItem("ägg");
        item7.setB12inFoodItem(87);
        u.addFoodItem(item7);

        connectingToDatabase.addToTableUsers(u);

        User p1 = connectingToDatabase.fetchFromTableUser(2);
        String name2 = p1.getName();
        System.out.println("Name: " + name2);
        for (FoodItem item : p1.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            float b12 = item.getB12inFoodItem();
            System.out.println(itemName + ", " + b12);
        }

        connectingToDatabase.deleteTableUser(user);

        User p2 = connectingToDatabase.fetchFromTableUser(2);
        String name3 = p2.getName();
        System.out.println("Name: " + name3);
        for (FoodItem item : p2.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            float b12 = item.getB12inFoodItem();
            System.out.println(itemName + ", " + b12);
        }
        connectingToDatabase.closeConnection();

    }
}