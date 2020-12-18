import java.sql.SQLException;

public class main_DB {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String Username = "Sven";

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
        user.addFoodItem(item1);
        user.addFoodItem(item2);
        user.addFoodItem(item3);

        ConnectingToDatabase connectingToDatabase = new ConnectingToDatabase();
        connectingToDatabase.createTableUser(user);

        connectingToDatabase.addToTableUser(user);
        User p = connectingToDatabase.fetchFromTableUser(user);

        String name1 = p.getName();
        System.out.println("Name: " + name1);
        for (FoodItem item : p.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            int b12 = item.getB12inFoodItem();
            System.out.println(itemName + ", " + b12);
        }

        User u = new User();
        u.setName(Username);

        FoodItem item5 = new FoodItem();
        item5.setNameOfItem("macka");
        item5.setB12inFoodItem(1);
        u.addFoodItem(item5);
        FoodItem item6 = new FoodItem();
        item6.setNameOfItem("kaviar");
        item6.setB12inFoodItem(32);
        u.addFoodItem(item6);
        FoodItem item7 = new FoodItem();
        item7.setNameOfItem("ägg");
        item7.setB12inFoodItem(87);
        u.addFoodItem(item7);

        connectingToDatabase.addToTableUser(u);

        User p1 = connectingToDatabase.fetchFromTableUser(user);
        String name2 = p1.getName();
        System.out.println("Name: " + name2);
        for (FoodItem item : p1.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            int b12 = item.getB12inFoodItem();
            System.out.println(itemName + ", " + b12);
        }

        connectingToDatabase.deleteTableUser(user);

        User p2 = connectingToDatabase.fetchFromTableUser(user);
        String name3 = p2.getName();
        for (FoodItem item : p2.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            int b12 = item.getB12inFoodItem();
            System.out.println(itemName + ", " + b12);
        }
        connectingToDatabase.closeConnection();
    }
}