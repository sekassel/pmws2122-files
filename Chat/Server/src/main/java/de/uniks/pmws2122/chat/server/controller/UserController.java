package de.uniks.pmws2122.chat.server.controller;

import de.uniks.pmws2122.chat.server.service.UserService;
import de.uniks.pmws2122.chat.server.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;

import javax.json.JsonObject;

import static de.uniks.pmws2122.chat.server.Constants.JSON_NAME;

public class UserController {
    private final Logger logger = LogManager.getLogger("UserController");

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public String login(Request req, Response res) {
        // Validate the body
        String body = req.body();
        JsonObject jsonBody;

        try {
            jsonBody = JsonUtil.parse(body);

            // Check the structure of the json {"name": "walah"}
            if (jsonBody.getString(JSON_NAME).isEmpty()) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            res.status(400);
            return "Can not parse login message, message should be like: {\"name\": \"Sebastian\"}";
        }

        String userName = jsonBody.getString(JSON_NAME);

        if (this.userService.isUserLoggedIn(userName)) {
            res.status(400);
            return "Name already taken";
        }

        this.userService.login(userName);

        res.status(200);
        return "OK";
    }

    public String logout(Request req, Response res) {
        // Validate the body
        String body = req.body();
        JsonObject jsonBody;

        try {
            jsonBody = JsonUtil.parse(body);

            // Check the structure of the json {"name": "walah"}
            if (jsonBody.getString(JSON_NAME).isEmpty()) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            res.status(400);
            return "Can not parse logout message, message should be like: {\"name\": \"Sebastian\"}";
        }

        String userName = jsonBody.getString(JSON_NAME);

        if (!this.userService.isUserLoggedIn(userName)) {
            res.status(400);
            return "Log in first";
        }

        this.userService.logout(userName);

        res.status(200);
        return "OK";
    }

    public String getAllLoggedInUser(Request req, Response res) {
        res.status(200);
        return JsonUtil.usersToJson(this.userService.getOnlineUsers()).toString();
    }
}
