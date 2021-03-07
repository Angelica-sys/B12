import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * This class connects to a database, creates tables, saves- and fetches data.
 *
 * @author Emma Svensson, Carin Loven, Angelica Asplund
 * @version 1.0
 */
public class ConnectingToDatabase {
    private int count;
    Connection connection = null;

    public ConnectingToDatabase() throws ClassNotFoundException {
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
     *
     * @throws SQLException
     */
    public void createTables() throws SQLException {
        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user"
                    + "(id INTEGER PRIMARY KEY, user_name TEXT)");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS item"
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, item_name TEXT, user_id INTEGER, b12 DOUBLE, " +
                    "FOREIGN KEY(user_id) REFERENCES user(id))");

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds data to table user
     *
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

    /**
     * Adds data to table item
     *
     * @param user is a User-object that contains name of the user and FoodItem-objects.
     * @throws SQLException
     */
    public void addToTableItem(User user) throws SQLException {
        Statement statement = connection.createStatement();
        int id = user.getId();
        for (FoodItem item : user.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            double b12 = item.getB12inFoodItem();
            String sql2 = "INSERT INTO item (item_name, user_id, b12) "
                    + "VALUES ('"
                    + itemName + "', "
                    + id + ", "
                    + b12 + ");";
            statement.executeUpdate(sql2);
        }
        statement.close();
    }

    /**
     * Fetches a list of items.
     *
     * @return A user object with its items.
     */
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
             e.printStackTrace();
        }
        return user;
    }

    /**
     * Fetches a list of all users.
     *
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

    /**
     * Deletes a users all fooditems from the database.
     *
     * @param id The id of a user
     */
    public void deleteUsersAllItem(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM item WHERE user_id = " + id + "");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a user from the database.
     *
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