package de.uniks.pmws2122.chat.server.socket;

import de.uniks.pmws2122.chat.server.controller.ChatController;
import de.uniks.pmws2122.chat.server.service.UserService;
import de.uniks.pmws2122.chat.server.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import javax.json.JsonObject;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static de.uniks.pmws2122.chat.server.Constants.*;

@WebSocket
public class ApplicationSocket {
    private final Logger logger = LogManager.getLogger("ChatSocket");

    private final Map<String, Session> userSessionMap = new ConcurrentHashMap<>();
    private final Queue<Session> clients = new ConcurrentLinkedQueue<>();

    private UserService userService;
    private ChatController chatController;

    public ApplicationSocket() {}

    @OnWebSocketConnect
    public void onNewConnection(Session session) {
        if (!session.getUpgradeRequest().getParameterMap().containsKey(JSON_NAME)) {
            this.sendMessage("Missing username as parameter, connect with /ws/application?name=Albert", session);
            session.close(1000, "Missing username as parameter, connect with /ws/application?name=Albert");
            return;
        }

        String username = session.getUpgradeRequest().getParameterMap().get(JSON_NAME).get(0);

        if (username == null || username.isEmpty()) {
            this.sendMessage("Missing username as parameter, connect with /ws/application?name=Albert", session);
            session.close(1000, "Missing username as parameter, connect with /ws/application?name=Albert");
            return;
        }

        if (!this.userService.isUserLoggedIn(username)) {
            this.sendMessage("Log in first", session);
            session.close(1000, "Log in first");
            return;
        }

        this.userSessionMap.put(username, session);
        this.clients.add(session);
    }

    @OnWebSocketClose
    public void onConnectionClose(Session session, int statusCode, String reason) {
        String username = session.getUpgradeRequest().getParameterMap().get(JSON_NAME).get(0);
        if (username == null || username.isEmpty()) {
            this.userSessionMap.remove(username);
        }

        this.clients.remove(session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        String username = session.getUpgradeRequest().getParameterMap().get(JSON_NAME).get(0);
        this.userService.userDoneAction(username);

        if (message.equals(WS_NOOP)) {
            return;
        }

        // Chat message in form of
        // { "from": "Natascha", "msg": "Ja moin" }                                                  -> Global
        // { "from": "Natascha", "to": "Sebastian", "msg": "Kommste hierher, siehste die scheiße!" } -> Private
        JsonObject msg;
        try {
            msg = JsonUtil.parse(message);
            if (msg.getString(JSON_FROM).isEmpty() || msg.getString(JSON_MSG).isEmpty()) {
                throw new RuntimeException("Wrong format");
            }
        } catch (Exception e) {
            this.sendMessage("Wrong format, try {\"from\": \"...\", \"msg\": \"...\"} or {\"from\": \"...\", \"to\": \"...\", \"msg\": \"...\"}", session);
            return;
        }

        try {
            String to = msg.getString(JSON_TO);
            Session other = this.userSessionMap.get(to);
            if (other != null) {
                this.sendMessage(JsonUtil.buildChatMessage(msg), other);
            } else {
                this.sendMessage("User is not online", session);
            }
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                this.clients.stream().filter(Session::isOpen).forEach((s) -> this.sendMessage(JsonUtil.buildChatMessage(msg), s));
                this.chatController.addToGlobalChat(message);
            }
        }
    }

    @OnWebSocketError
    public void onSocketError(Throwable e) {
        if (!(e instanceof EofException)) {
            this.logger.debug("Error on websocket:");
            e.printStackTrace();
        }
    }

    public void sendUserJoined(String username) {
        this.clients.stream().filter(Session::isOpen).forEach((s) -> this.sendMessage(JsonUtil.buildUserJoinedMessage(username) ,s));
    }

    public void sendUserLeft(String username) {
        this.clients.stream().filter(Session::isOpen).forEach((s) -> this.sendMessage(JsonUtil.buildUserLeftMessage(username) ,s));
    }

    public void killConnection(String username, String reason) {
        if (this.userSessionMap.containsKey(username)) {
            Session toKill = this.userSessionMap.get(username);
            if (toKill.isOpen()) {
                this.userSessionMap.remove(username);
                this.clients.remove(toKill);
                toKill.close(1000, reason);
            }
        }
    }

    private void sendMessage(String message, Session session) {
        try {
            session.getRemote().sendString(message);
            session.getRemote().flush();
        } catch (Exception e) {
            System.err.println("Can not send chat message");
            e.printStackTrace();
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }
}
