import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

/**
 * Test class for ConnectionToDatabas, APICache
 * @author Carin Loven
 * @version 1.0
 */
public class Main {
    private String url;
    private ConnectingToDatabase connectingToDatabase;
    private APICache cache;
    private APIRunner api;
    private String dataAsJSON;

    public Main() throws ClassNotFoundException {
        //cache = new Test_APICaching();
        connectingToDatabase = new ConnectingToDatabase();
        api = new APIRunner(connectingToDatabase);
        // connectingToDatabase.closeConnection();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Main m = new Main();
        m.testDataBase();
        m.tryGETusers();
    }

    private void testDataBase() throws SQLException {
        String Username = "Sven ";
        int id = 0;
        FoodItem item1 = new FoodItem();
        item1.setNameOfItem("äpple");
        item1.setB12inFoodItem(33);
        User user = new User();
        user.setName(Username);
        user.setId(id);
        user.addFoodItem(item1);

        /*
        FoodItem item2 = new FoodItem();
        item2.setNameOfItem("päron");
        item2.setB12inFoodItem(2);
        FoodItem item3 = new FoodItem();
        item3.setNameOfItem("banan");
        item3.setB12inFoodItem(99);
        user.addFoodItem(item2);
        user.addFoodItem(item3);

        String Username1 = "Emma";
        int id1 = 1;
        FoodItem item4 = new FoodItem();
        item4.setNameOfItem("päron");
        item4.setB12inFoodItem(33);
        FoodItem item5 = new FoodItem();
        item5.setNameOfItem("p");
        item5.setB12inFoodItem(2);
        FoodItem item6 = new FoodItem();
        item6.setNameOfItem("t");
        item6.setB12inFoodItem(99);

        User user99 = new User();
        user99.setName(Username1);
        user99.setId(id1);
        user99.addFoodItem(item4);
        user99.addFoodItem(item5);
        user99.addFoodItem(item6);

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
        user3.addFoodItem(item8);
        user3.addFoodItem(item9);
         */

        connectingToDatabase.createTables();
        connectingToDatabase.addToTableUser(user);
        connectingToDatabase.addToTableItem(user);

        /*
        connectingToDatabase.addToTableUser(user99);
        connectingToDatabase.addToTableItem(user99);
        connectingToDatabase.addToTableUser(user3);
        connectingToDatabase.addToTableItem(user3);

        for (User users : connectingToDatabase.fetchUserList()) {
            System.out.print(users.getName());
            System.out.println();
            System.out.print(users.getId());
            System.out.println();
        }

        User p = connectingToDatabase.fetchFromTableItem(1);
        for (FoodItem item : p.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            float b12 = item.getB12inFoodItem();
            System.out.println(itemName + ", " + b12);
        }
        /*

        connectingToDatabase.deleteUsersAllItem(1);
        connectingToDatabase.deleteUser(1);
        connectingToDatabase.deleteItem(2, "rttt");

        /*
        User p1 = connectingToDatabase.fetchFromTableItem(2);
        System.out.println("Name: " + p1.getName());
        for (FoodItem item : p1.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            float b12 = item.getB12inFoodItem();
            System.out.println(itemName + ", " + b12);
        }

        for (User users : connectingToDatabase.fetchUserList()) {
            System.out.print(users.getName());
            System.out.println();
            System.out.print(users.getId());
            System.out.println();
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

        User p2 = connectingToDatabase.fetchFromTableUser(2);
        String name3 = p2.getName();
        System.out.println("Name: " + name3);
        for (FoodItem item : p2.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            float b12 = item.getB12inFoodItem();
            System.out.println(itemName + ", " + b12);
        }

         */
    }

    public void tryGETusers() {
        url = ("http://localhost:5000/users/");
        //url = ("http://localhost:5000/users/0");
        System.out.println(url);
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlConnection.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println(status);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                dataAsJSON = inputLine;
            }
            System.out.println(dataAsJSON);
            in.close();
            con.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryPOSTUser() {
        url = ("http://localhost:5000/users/");
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlConnection.openConnection();
            con.setRequestMethod("POST");
            int status = con.getResponseCode();
            System.out.println(status);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}