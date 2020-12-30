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
     * @throws SQLException
     */
    public void createTables() throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            statement.executeUpdate("CREATE TABLE users"
                    + "(id INTEGER PRIMARY KEY autoincrement , username TEXT)");
            // IDENTITY(1,1)
            statement.executeUpdate("DROP TABLE IF EXISTS userEats");
            statement.executeUpdate("CREATE TABLE userEats"
                    + "(id INTEGER autoincrement, foodItem TEXT, b12 REAL, FOREIGN KEY(id) REFERENCES users(id))");


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
                user.name = rs.getString("username");
                //Ska vi ha deras fooditems och näringsvärde här också?
                user.totalAmountB12 = rs.getInt("totalAmountB12");
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
    public void addToTableUsers(User user) throws SQLException {
        Statement statement = connection.createStatement();
        String userName = user.getName();
        int id = user.getId();

        String sql1 = "INSERT INTO users (id, username)"
                + "VALUES ("
                + id + ", "
                + "'" + userName + "');";
        statement.executeUpdate(sql1);
        statement.close();
    }

    public void addToTableUserEat(User user) throws SQLException {
        Statement statement = connection.createStatement();
        String userName = user.getName();
        int id = user.getId();
        for (FoodItem item : user.getListOfFoodItem()) {
            String itemName = item.getNameOfItem();
            float b12 = item.getB12inFoodItem();
            String sql2 = "INSERT INTO userEats (id, foodItem, b12)"
                    + "VALUES ("
                    + id + ", "
                    + "'" + itemName + "' "
                    + b12 + ");";
            statement.executeUpdate(sql2);
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
     * @param id is a User-object that contains name of the table to fetch data from.
     * @return user, the creates object.
     * @throws SQLException
     */
    public User fetchFromTableUser(int id) throws SQLException {
        User user = new User();
        String userName = user.getName();
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