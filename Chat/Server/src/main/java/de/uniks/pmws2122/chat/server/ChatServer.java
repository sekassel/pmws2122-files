package de.uniks.pmws2122.chat.server;

import de.uniks.pmws2122.chat.server.controller.ChatController;
import de.uniks.pmws2122.chat.server.controller.UserController;
import de.uniks.pmws2122.chat.server.service.UserService;
import de.uniks.pmws2122.chat.server.socket.ApplicationSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;

import static de.uniks.pmws2122.chat.server.Constants.*;
import static spark.Spark.*;

public class ChatServer {
    private final Logger logger = LogManager.getLogger("Server");

    private final UserController userController;
    private final ChatController chatController;

    private final UserService userService;

    private final ApplicationSocket applicationSocket;

    public ChatServer() {
        this.applicationSocket = new ApplicationSocket();

        this.userService = new UserService(applicationSocket);
        this.applicationSocket.setUserService(this.userService);

        this.userController = new UserController(this.userService);
        this.chatController = new ChatController(this.userService);
        this.applicationSocket.setChatController(this.chatController);
    }

    public void init() {
        // Start the server
        port(PORT);

        // Define routs
        webSocket(WS_APPLICATION, this.applicationSocket);
        before("*", (res, req) -> req.header("Access-Control-Allow-Origin", "*"));
        before("*", (res, req) -> req.header("Access-Control-Allow-Headers", "*"));
        before("*", (res, req) -> req.header("Content-Type", "application/json"));
        before("*", (res, a) -> logger.debug(res.uri() + " from " + res.host()));

        path(API_PREFIX, () -> {
            path(API_CHAT, () -> {
                path(GLOBAL_CHAT, () -> {
                    get("", chatController::getAllGlobalMessages);
                });
            });
            path(API_USERS, () -> {
                post(API_LOGIN, userController::login);
                post(API_LOGOUT, userController::logout);
                get("", userController::getAllLoggedInUser);
            });
        });

        // Error handling
        notFound((request, response) -> "This aren't the droids you're looking for");
        internalServerError(((request, response) -> "Server tired, server sleeping"));

        // Catch all exceptions
        exception(Exception.class, (e, req, res) -> {
            if (!(e instanceof EOFException)) {
                logger.error("Something went wrong on the server:");
                e.printStackTrace();
            }
        });
    }

    public void stopServer() {
        stop();
    }
}
