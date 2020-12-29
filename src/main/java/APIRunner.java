import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class APIRunner {

    public static void main(String[] args) throws Exception {
        port(5000);

        ConnectingToDatabase connectingToDatabase = new ConnectingToDatabase();

        Gson gson = new Gson();

       /* get("/front", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });  */


    //Get list of users
        get("/", (req, res) -> {
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

    //Get user by id
        get("/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));
            User user = connectingToDatabase.fetchFromTableUser(new User(Integer.parseInt(req.params(":id"))));
            res.type("application/json");
            return gson.toJson(user);
        });
        //Get nutrition value for specific item

    // get ("/:id, (req, res) -> {
        // return "Here is your requested food item"});


        post("/", (req, res) -> {
            try {
                User user = gson.fromJson(req.body(), User.class);
                connectingToDatabase.addToTableUsers(user);
            } catch (Exception e) {
                System.out.println(e);
            }
            return "";
        });
      /*  put("/:id", (req, res) -> {

            try {
                User user = gson.fromJson(req.body(), User.class);
                user.id = Integer.parseInt(req.params("id"));
                connectingToDatabase.updateUserInformation(user);
            } catch (Exception e) {
                System.out.println(e);
            }
            return "";

        });*/

        delete("/:id", (req, res) -> {
            connectingToDatabase.deleteTableUser(new User(Integer.parseInt(req.params(":id"))));
            return "";
        });

        //delete (/:id, (req, res) ->{
             // return "You have deleted a food item in a users list"; });

    }
}

