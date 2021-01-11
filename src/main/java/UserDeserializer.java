import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Converting Json to Java
 *
 * @author Carin Loven
 * @version 1.0
 */
public class UserDeserializer implements JsonDeserializer<User> {
    private APICache cache;

    public UserDeserializer(APICache cache) {
        this.cache = cache;
    }

    @Override
    public User deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        User user = new User();
        JsonObject obj = (JsonObject) jsonElement;
        user.setName(obj.get("name").getAsString());
        user.setId(obj.get("id").getAsInt());
        for (JsonElement item : obj.get("foodItems").getAsJsonArray()) {
            user.addFoodItem(cache.getFoodItem(item.getAsString()));
        }
        return user;
    }
}