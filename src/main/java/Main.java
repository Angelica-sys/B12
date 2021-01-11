import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

/**
 * Starts the server
 *
 * @author Carin Loven
 * @version 1.0
 */
public class Main {

    public Main() throws ClassNotFoundException {
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        APICache cache = new APICache();
        ConnectingToDatabase connectingToDatabase = new ConnectingToDatabase();
        connectingToDatabase.createTables();
        APIRunner api = new APIRunner(connectingToDatabase, cache);
        // connectingToDatabase.closeConnection();
    }
}