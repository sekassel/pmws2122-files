package de.uniks.pmws2122.icp.net;

import static de.uniks.pmws2122.icp.Constants.*;

public class RestService {
    public static boolean login(String nickname) {
        // TODO: Perform a post rest call with the nickname as a json body to login
        // TODO: Return true if success, otherwise false
    }

    public static boolean logout(String nickname) {
        // TODO: Perform a post rest call with the nickname as a json body to logout
        // TODO: Return true if success, otherwise false
    }

    public static boolean sendChatMessage(String from, String message) {
        // TODO: Perform a post rest call with a from and message attribute as a json body to send a chat message
        // TODO: Return true if success, otherwise false
    }

    public static JSONArray getAllMessages() {
        // TODO: Perform a get rest call and transform the response to a json array to get all chat messages
        // TODO: Return the json array if success, otherwise return an empty json array
    }
}
