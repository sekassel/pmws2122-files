package de.uniks.pmws2122.chat.client;

public class Constants {
    // Application
    public static final String LOGIN_SCREEN_TITLE = "ICP Login";
    public static final String CHAT_SCREEN_TITLE = "ICP Chat";
    public static final String NOOP = "noop";

    // Network
    public static final String BASE_URL = "https://icp.uniks.de";   // http://localhost:3000 for local development with a local server instance
    public static final String WS_URL = "wss://icp.uniks.de";       // http://localhost:3000 for local development with a local server instance

    public static final String API_PREFIX = "/api";
    public static final String WS_PREFIX = "/ws";

    public static final String USER_URL = BASE_URL + API_PREFIX + "/users";
    public static final String ALL_CHAT_URL = BASE_URL + API_PREFIX + "/chat" + "/all";

    public static final String LOGIN_URL = USER_URL + "/login";
    public static final String LOGOUT_URL = USER_URL + "/logout";

    public static final String WS_APPLICATION = WS_URL + WS_PREFIX + "/application?name=";

    // Json
    public static final String JSON_REMEMBER_ME = "rememberMe";
    public static final String JSON_NAME = "name";
    public static final String JSON_FROM = "from";
    public static final String JSON_MSG = "msg";
    public static final String JSON_ACTION = "action";
    public static final String JSON_ACTION_CHAT = "chat";
    public static final String JSON_ACTION_USER_JOINED = "userJoined";
    public static final String JSON_ACTION_USER_LEFT = "userLeft";
}
