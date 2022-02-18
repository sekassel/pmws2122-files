package de.uniks.pmws2122.chat.server;

public class Constants {
    // Server config
    public static final int PORT = 3000;
    public static final String API_PREFIX = "/api";
    public static final String WS_APPLICATION = "/ws/application";
    public static final String API_CHAT = "/chat";
    public static final String API_LOGIN = "/login";
    public static final String API_LOGOUT = "/logout";
    public static final String API_USERS = "/users";
    public static final String GLOBAL_CHAT = "/all";

    // Json
    public static final String JSON_NAME = "name";
    public static final String JSON_FROM = "from";
    public static final String JSON_TO = "to";
    public static final String JSON_MSG = "msg";

    // Websocket
    public static final String WS_NOOP = "noop";
    public static final String WS_ACTION = "action";
    public static final String WS_ACTION_CHAT = "chat";
    public static final String WS_ACTION_USER_JOINED = "userJoined";
    public static final String WS_ACTION_USER_LEFT = "userLeft";

}
