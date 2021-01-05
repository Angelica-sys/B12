import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * @author Angelica Asplund, Emma Svensson, Carin Loven
 * @version 1.0
 */
public class APIRunner {
    private ConnectingToDatabase connectingToDatabase;

    public APIRunner(ConnectingToDatabase connectingToDatabase) throws ClassNotFoundException {
        this.connectingToDatabase = connectingToDatabase;
        port(5000);
        Gson gson = new Gson();

        //TODO Behövs detta?
       /* get("/front", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });  */

        get("/users/", (req, res) -> {
            List<User> users = connectingToDatabase.fetchUserList();
            ArrayList<Map> userList = new ArrayList<Map>();
            for (User user : users) {
                Map map = new HashMap();
                map.put("id", user.id);
                map.put("name", user.name);
                map.put("details", "http://localhost:5000/" + user.id);
                userList.add(map);
            }
            res.type("application/json");
            return gson.toJson(userList);
        });

        get("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            User user = connectingToDatabase.fetchFromTableItem(id);
            List<FoodItem> users = user.getListOfFoodItem();
            ArrayList<Map> foodItem = new ArrayList<Map>();
            for (FoodItem item : users) {
                Map map = new HashMap();
                map.put("foodItem", item.getNameOfItem());
                map.put("b12", item.getB12inFoodItem());
                foodItem.add(map);
            }
            res.type("application/json");
            return gson.toJson(foodItem);
        });

        /*
        post("/users", (req, res) -> {
            try {
                User user = gson.fromJson(req.body(), User.class);
                connectingToDatabase.addToTableUsers(user);
            } catch (Exception e) {
                System.out.println(e);
            }
            return "";
        });  */

        post("/users/", (req, res) -> {
            User user = new User();
            connectingToDatabase.addToTableUser(user);
            res.type("application/json");
            return gson.toJson("user is created");
        });

        /*
        put("/users/id", (req, res) -> {
            try {
                User user = gson.fromJson(req.body(), User.class);
                user.id = Integer.parseInt(req.params("id"));
                connectingToDatabase.updateUser(user);
            } catch (Exception e) {
                System.out.println(e);
            }
            return "";
        }); */

        put("/users/id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));  //todo Behövs :id?
            User user = new User();
            connectingToDatabase.updateUser(user);
            res.type("application/json");
            return gson.toJson("unicorn has been updated");
        });

        delete("/users/id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));   //todo Behövs :id?
            connectingToDatabase.deleteUser(id);
            res.type("application/json");
            return gson.toJson("user has been deleted");
        });

        //delete (/:id, (req, res) ->{
        // return "You have deleted a food item in a users list"; });
    }
}