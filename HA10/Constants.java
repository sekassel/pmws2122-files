package de.uniks.pmws2122.icp;

public class Constants {
    // Application
    public static final String LOGIN_SCREEN_TITLE = "ICP Login";
    public static final String CHAT_SCREEN_TITLE = "ICP Chat";

    // Network
    public static final String BASE_URL = "https://icp.uniks.de/api";
    public static final String USER_URL = BASE_URL + "/users";
    public static final String LOGIN_URL = USER_URL + "/login";
    public static final String LOGOUT_URL = USER_URL + "/logout";
    public static final String ALL_CHAT_URL = BASE_URL + "/chat" + "/all";

    // Json
    public static final String JSON_NAME = "name";
    public static final String JSON_FROM = "from";
    public static final String JSON_MSG = "msg";
}
