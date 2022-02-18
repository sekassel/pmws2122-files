package de.uniks.pmws2122.chat.server.controller;

import de.uniks.pmws2122.chat.server.service.UserService;
import de.uniks.pmws2122.chat.server.util.JsonUtil;
import spark.Request;
import spark.Response;

import javax.json.JsonStructure;
import java.util.ArrayList;
import java.util.List;

public class ChatController {
    private final UserService userService;

    private final List<String> chatMessages;

    public ChatController(UserService userService) {
        this.chatMessages = new ArrayList<>();
        this.userService = userService;
    }

    public void addToGlobalChat(String message) {
        this.chatMessages.add(message);
    }

    public String getAllGlobalMessages(Request req, Response res) {
        JsonStructure json = JsonUtil.chatMessagesToJson(this.chatMessages);

        res.status(200);
        return json.toString();
    }
}
