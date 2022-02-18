package de.uniks.pmws2122.chat.server.util;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import java.io.StringReader;
import java.util.List;

import static de.uniks.pmws2122.chat.server.Constants.*;

public class JsonUtil {
    public static JsonObject parse(String json) {
        return Json.createReader(new StringReader(json)).readObject();
    }

    public static JsonStructure chatMessagesToJson(List<String> chatMessages) {
        JsonArrayBuilder array = Json.createArrayBuilder();
        chatMessages.forEach((s) -> array.add(JsonUtil.parse(s)));
        return array.build();
    }

    public static JsonStructure usersToJson(List<String> users) {
        return Json.createArrayBuilder(users).build();
    }

    public static String buildChatMessage(JsonObject message) {
        return Json.createObjectBuilder(message)
                .add(WS_ACTION, WS_ACTION_CHAT)
                .build().toString();
    }

    public static String buildUserJoinedMessage(String username) {
        return Json.createObjectBuilder()
                .add(WS_ACTION, WS_ACTION_USER_JOINED)
                .add(JSON_NAME, username)
                .build().toString();
    }

    public static String buildUserLeftMessage(String username) {
        return Json.createObjectBuilder()
                .add(WS_ACTION, WS_ACTION_USER_LEFT)
                .add(JSON_NAME, username)
                .build().toString();
    }
}
