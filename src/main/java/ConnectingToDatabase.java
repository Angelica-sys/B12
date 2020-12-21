import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * This class connects to a database, creates tables, saves- and fetches data.
 *
 * @author Carin Loven
 * @version 1.0
 */
public class ConnectingToDatabase {
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
     * @param user is a user-object that contains name of the user and FoodItem-objects.
     * @throws SQLException
     */
    public void createTableUser(User user) throws SQLException {
        String userName = user.getName();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS '" + userName + "'");
            statement.executeUpdate("CREATE TABLE " + userName
                    + "(FoodItems TEXT, B12 INTEGER)");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User user = new User();

                user.id = rs.getInt("id");
                user.name = rs.getString("name");
                //Ska vi ha deras fooditems och näringsvärde här också?

                users.add(user);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Adds data to a table.
     * @param user is a User-object that contains name of the user and FoodItem-objects.
     * @throws SQLException
     */
    public void addToTableUser(User user) throws SQLException {
        Statement statement = connection.createStatement();
        String userName = user.getName();
        for (FoodItem item : user.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            int b12 = item.getB12inFoodItem();
            String sql = "INSERT INTO '" + userName + "'(FoodItems, B12) "
                    + "VALUES ('"
                    + itemName + "', "
                    + "" + b12 + ");";
            statement.executeUpdate(sql);
        }
        statement.close();
    }

    /**
     * Updates an existing user.
     *
     * @param user A user object.
     */
    public void updateUser(User user) {
        try {
            Statement statement = connection.createStatement();

            String sql = "UPDATE users SET id = " + user.id + ", "
                    + "name = '" + user.name + "', "
                    + "WHERE id = " + user.id + ";";

            statement.executeUpdate(sql);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches data from the database and creates a User-object to copulate the data.
     * @param u is a User-object that contains name of the table to fetch data from.
     * @return user, the creates object.
     * @throws SQLException
     */
    public User fetchFromTableUser(User u) throws SQLException {
        User user = new User();
        String userName = u.getName();
        user.setName(userName);
        try {
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery("SELECT * FROM '" + userName + "'");
            while (response.next()) {
                FoodItem item = new FoodItem();
                item.setNameOfItem(response.getString("FoodItems"));
                item.setB12inFoodItem(response.getInt("B12"));
                user.addFoodItem(item);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("No " + userName + " Table");
            // e.printStackTrace();
        }
        return user;
    }

    /**
     * Deletes a table in the database.
     * @param user is a User-object that contains name of the table to delete.
     */
    public void deleteTableUser(User user) {
        String userName = user.getName();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS '" + userName + "'");
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
        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM users WHERE id = " + id);

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