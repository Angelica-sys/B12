import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * Here is our internal and external API using spark
 *
 * @author Angelica Asplund, Emma Svensson, Carin Loven
 * @version 1.0
 */
public class APIRunner {
    private ConnectingToDatabase connectingToDatabase;

    public APIRunner(ConnectingToDatabase connectingToDatabase, APICache cache) throws ClassNotFoundException {
        port(5000);
        staticFiles.location("/public");
        this.connectingToDatabase = connectingToDatabase;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer(cache));
        Gson gbuilder = gsonBuilder.create();
        Gson gson = new Gson();
        System.out.println("API runs");


        get("/users/", (req, res) -> {
            List<User> users = connectingToDatabase.fetchUserList();
            ArrayList<Map> userList = new ArrayList<Map>();
            for (User user : users) {
                Map map = new HashMap();
                map.put("id", user.id);
                map.put("name", user.name);
                userList.add(map);
            }
            res.type("application/json");
            return gbuilder.toJson(userList);
        });

        get("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            User user = connectingToDatabase.fetchFromTableItem(id);
            List<FoodItem> users = user.getListOfFoodItem();
            ArrayList<Map> foodItem = new ArrayList<Map>();
            for (FoodItem item : users) {
                String B12 = Double.toString(item.getB12inFoodItem());
                String b12 = B12.replace(".", ",");
                Map map = new HashMap();
                map.put("foodItem", item.getNameOfItem());
                map.put("b12", b12);
                foodItem.add(map);
            }
            res.type("application/json");
            return gbuilder.toJson(foodItem);
        });


        post("/users/", (req, res) -> {
            try {
                User user = gson.fromJson(req.body(), User.class);
                System.out.println("user: " + user.getName() + " " + user.getId());
                connectingToDatabase.addToTableUser(user);
            } catch (Exception e) {
                System.out.println(e);
            }
            return "user added";
        });


        put("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            User user = gbuilder.fromJson(req.body(), User.class);
            System.out.println("user: " + user.getName());
            for (FoodItem item : user.getListOfFoodItem()) {
                System.out.println("user: " + item.getB12inFoodItem());
            }
            connectingToDatabase.addToTableItem(user);
            res.type("application/json");
            return gbuilder.toJson("foodItem has been updated");
        });


        delete("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            connectingToDatabase.deleteUser(id);
            res.type("application/json");
            return gbuilder.toJson("user has been deleted");
        });

    }
}