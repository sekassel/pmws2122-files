package de.uniks.pmws2122.icp.util;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;

import static de.uniks.pmws2122.icp.Constants.*;

public class JsonUtil {
    public static JsonNode parse(String json) {
        return new JsonNode(json);
    }

    public static String createLoginLogout(String nickname) {
        return new JSONObject()
                .put(JSON_NAME, nickname)
                .toString();
    }

    public static String createChatMessage(String from, String message) {
        return new JSONObject()
                .put(JSON_FROM, from)
                .put(JSON_MSG, message)
                .toString();
    }

    public static String createRememberMeConfig(String nickname) {
        return new JSONObject()
                .put(JSON_REMEMBER_ME, true)
                .put(JSON_NAME, nickname)
                .toString();
    }

    public static String createDefaultConfig() {
        return new JSONObject()
                .put(JSON_REMEMBER_ME, false)
                .put(JSON_NAME, "")
                .toString();
    }
}
