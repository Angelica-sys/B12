import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * This class connects to a database, creates tables, saves- and fetches data.
 *
 * @author Carin Loven, Emma Svensson, Angelica Asplund
 * @version 1.0
 */
public class ConnectingToDatabase {
    private int count;
    Connection connection = null;

    public ConnectingToDatabase() throws ClassNotFoundException {
        count = 0;
        Class.forName("org.sqlite.JDBC");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:b12.db");
            if (connection != null) {
                System.out.println("Connected to sqlite");
                DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connection to sqlite");
        }
    }

    /**
     * Creates a table in the database.
     * @throws SQLException
     */
    public void createTables() throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            statement.executeUpdate("CREATE TABLE user"
                    + "(id INTEGER PRIMARY KEY, user_name TEXT)");

            statement.executeUpdate("DROP TABLE IF EXISTS item");
            statement.executeUpdate("CREATE TABLE item"
                    // date DATETIME DEFAULT CURRENT_TIMESTAMP
                    + "(surregate_key INTEGER PRIMARY KEY, item_name TEXT, user_id INTEGER, b12 DOUBLE, " +
                    "FOREIGN KEY(user_id) REFERENCES user(id))");
            // , PRIMARY KEY(item_name, user_id)
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds data to a table.
     * @param user is a User-object that contains name of the user and FoodItem-objects.
     * @throws SQLException
     */
    public void addToTableUser(User user) throws SQLException {
        Statement statement = connection.createStatement();
        String userName = user.getName();
        int id = user.getId();
        String sql1 = "INSERT INTO user (id, user_name)"
                + "VALUES ("
                + id + ", "
                + "'" + userName + "');";
        statement.executeUpdate(sql1);
        statement.close();
    }

    public void addToTableItem(User user) throws SQLException {
        Statement statement = connection.createStatement();
        int id = user.getId();
        for (FoodItem item : user.getListOfFoodItem()) {
            count++;
            String itemName = item.getNameOfItem();

            double b12 = item.getB12inFoodItem();
            System.out.println("b12 adToTable: " + b12);
            String sql2 = "INSERT INTO item (surregate_key, item_name, user_id, b12) "
                    + "VALUES (" + count + ", '"
                    + itemName + "', "
                    + id + ", "
                    + b12 + ");";
            statement.executeUpdate(sql2);
        }
        statement.close();
    }

    /**
     * Updates an existing user.
     * @param user A user object.
     */
    public void updateUser(User user) {
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE user SET id = " + user.id + ", "
                    + "user_name = '" + user.name + "', "
                    + "WHERE id = " + user.id + ";";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User fetchFromTableItem(int id) throws SQLException {
        User user = new User();
        try {
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery("SELECT * FROM item " +
                    "WHERE user_id = " + id + "");
            while (response.next()) {
                FoodItem item = new FoodItem();
                item.setNameOfItem(response.getString("item_name"));
                item.setB12inFoodItem(response.getDouble("b12"));
                user.addFoodItem(item);
            }
            statement.executeQuery("SELECT * FROM user " +
                    "WHERE id = " + id + "");
            String name = response.getString("user_name");
            user.setName(name);
            statement.close();
        } catch (SQLException e) {
            System.out.println("No column with user_id " + id);
            // e.printStackTrace();
        }

        for (FoodItem item: user.getListOfFoodItem()){
            System.out.println("b12 fetch: " + item.getB12inFoodItem());
        }
        return user;
    }

    /**
     * Fetches a list of all users.
     * @return A list of users.
     */
    public List<User> fetchUserList() {
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user");
            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.name = rs.getString("user_name");
                users.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void deleteUsersAllItem(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM item WHERE user_id = " + id + "");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int id, String item_name) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM item WHERE user_id = " + id + " AND "
                    + "item_name = '" + item_name + "'");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a user from the database.
     * @param id The id of a user
     */
    public void deleteUser(int id) {
        deleteUsersAllItem(id);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM user WHERE id = " + id);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes connection to database.
     */
    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Closing connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}