import java.util.List;

import static spark.Spark.*;

public class APIRunner {

    public static void main(String[] args) throws Exception {
        port(5000);



        get("/", (req, res) -> {
            return "Hello world";
        });


        get("/:id", (req, res) -> {
            return "Hello world";
        });

        post("/", (req, res) -> {

            return "Hello world";
        });
        put("/:id", (req, res) -> {

            return "Hello world ";

        });

        delete("/:id", (req, res) -> {

            return "Hello world";
        });

    }
}

