package de.uniks.pmws2122.chat.server.service;

import de.uniks.pmws2122.chat.server.socket.ApplicationSocket;
import de.uniks.pmws2122.chat.server.util.Watchdog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private final ApplicationSocket applicationSocket;

    private final List<String> onlineUsers;
    private final Map<String, Watchdog<String>> watchdogs;

    public UserService(ApplicationSocket applicationSocket) {
        this.applicationSocket = applicationSocket;
        this.onlineUsers = new ArrayList<>();
        this.watchdogs = new ConcurrentHashMap<>();
    }

    public void login(String username) {
        this.onlineUsers.add(username);

        // Start a watchdog thread that logout the user after 15 min
        Watchdog<String> watchdog = new Watchdog<>(username, 1000 * 60 * 15, this::logout);
        this.watchdogs.put(username, watchdog);
        watchdog.start();

        this.applicationSocket.sendUserJoined(username);
    }

    public void logout(String username) {
        this.onlineUsers.remove(username);

        final Watchdog<String> watchdog = this.watchdogs.remove(username);
        if (watchdog != null) {
            watchdog.cancel();
        }

        this.applicationSocket.sendUserLeft(username);
        this.applicationSocket.killConnection(username, "Logout");
    }

    public void userDoneAction(String username) {
        this.watchdogs.get(username).recalculateEndTime();
    }

    public boolean isUserLoggedIn(String username) {
        return this.onlineUsers.contains(username);
    }

    public List<String> getOnlineUsers() {
        return this.onlineUsers;
    }
}
