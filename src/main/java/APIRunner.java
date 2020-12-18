import com.google.gson.Gson;

import static spark.Spark.*;

public class APIRunner {

    public static void main(String[] args) throws Exception {
        port(5000);

        ConnectingToDatabase connectingToDatabase = new ConnectingToDatabase();

        Gson gson = new Gson();

    //Get list of users
        get("/", (req, res) -> {
            return "Here is the list of users";
        });
    //Get user by id
        get("/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));
            User user = connectingToDatabase.fetchFromTableUser(id);
            res.type("application/json");
            return gson.toJson(user);
        });
        //Get nutrition value for specific item

    // get ("/:id, (req, res) -> {
        // return "Here is your requested food item"});


        post("/", (req, res) -> {

            return "You have registered a user";
        });
        put("/:id", (req, res) -> {

            return "You have updated you users nutrition list";

        });

        delete("/:id", (req, res) -> {

            return "You have deleted a user";
        });

        //delete (/:id, (req, res) ->{
             // return "You have deleted a food item in a users list"; });

    }
}

