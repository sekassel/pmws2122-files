package de.uniks.pmws2122.icp.net;

import de.uniks.pmws2122.icp.util.JsonUtil;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;

import static de.uniks.pmws2122.icp.Constants.*;

public class RestService {
    public static boolean login(String nickname) {
        // Perform a post rest call with the nickname as a json body to login
        // Return true if success, otherwise false
        HttpResponse<String> answer = Unirest.post(LOGIN_URL).body(JsonUtil.createLoginLogout(nickname)).asString();
        return answer.isSuccess();
    }

    public static boolean logout(String nickname) {
        // Perform a post rest call with the nickname as a json body to logout
        // Return true if success, otherwise false
        HttpResponse<String> answer = Unirest.post(LOGOUT_URL).body(JsonUtil.createLoginLogout(nickname)).asString();
        return answer.isSuccess();
    }

    public static JSONArray getAllMessages() {
        // Perform a get rest call and transform the response to a json array to get all chat messages
        // Return the json array if success, otherwise return an empty json array
        HttpResponse<JsonNode> answerJson = Unirest.get(ALL_CHAT_URL).asJson();
        if (answerJson.isSuccess()) {
            return answerJson.getBody().getArray();
        } else {
            return new JSONArray();
        }
    }

    public static JSONArray getAllLoggedInUsers() {
        // TODO: Perform a get rest call and transform the response to a json array to get all logged in users
        // TODO: Return the json array if success, otherwise return nan empty json array
    }
}
