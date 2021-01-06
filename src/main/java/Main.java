import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

/**
 * Test class for ConnectionToDatabas, APICache
 * @author Carin Loven
 * @version 1.0
 */
public class Main {

    public Main() throws ClassNotFoundException {}

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        APICache cache = new APICache();
        ConnectingToDatabase connectingToDatabase = new ConnectingToDatabase();
        connectingToDatabase.createTables();
        APIRunner api = new APIRunner(connectingToDatabase, cache);
        // connectingToDatabase.closeConnection();

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
        item4.setNameOfItem("glass");
        item4.setB12inFoodItem(33);
        FoodItem item5 = new FoodItem();
        item5.setNameOfItem("godis");
        item5.setB12inFoodItem(2);
        FoodItem item6 = new FoodItem();
        item6.setNameOfItem("chips");
        item6.setB12inFoodItem(99);

        User user99 = new User();
        user99.setName(Username1);
        user99.setId(id1);
        user99.addFoodItem(item4);
        user99.addFoodItem(item5);
        user99.addFoodItem(item6);

        /*
        String Username2 = "Angelica";
        int id2 = 2;
        FoodItem item7 = new FoodItem();
        item7.setNameOfItem("y");
        item7.setB12inFoodItem(83);
        FoodItem item8 = new FoodItem();
        item8.setNameOfItem("rttt");
        item8.setB12inFoodItem(9);
        FoodItem item9 = new FoodItem();
        item9.setNameOfItem("godis");
        item9.setB12inFoodItem(76);

        User user3 = new User();
        user3.setName(Username2);
        user3.setId(id2);
        user3.addFoodItem(item7);
     //   user3.addFoodItem(item8);
     //   user3.addFoodItem(item9);

 */

      //  connectingToDatabase.createTables();
        connectingToDatabase.addToTableUser(user);
        connectingToDatabase.addToTableItem(user);
        connectingToDatabase.addToTableUser(user99);
        connectingToDatabase.addToTableItem(user99);
        /*
        connectingToDatabase.addToTableUser(user3);
        connectingToDatabase.addToTableItem(user3);

         */
    }
}