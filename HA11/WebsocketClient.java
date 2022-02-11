package de.uniks.pmws2122.icp.net;

import kong.unirest.JsonNode;

import javax.websocket.*;
import java.util.Timer;
import java.util.function.Consumer;

@ClientEndpoint
public class WebsocketClient {
    private final Timer noopTimer;
    private final Consumer<JsonNode> callback;

    private Session session;

    public WebsocketClient(String endpoint, Consumer<JsonNode> callback) {
        // TODO: Create a new timer and save the callback

        // TODO: Create and connect the websocket client
    }

    @OnOpen
    public void onOpen(Session session) {
        // TODO: Save the session

        // TODO: Start the timer to send noop messages every 30 seconds
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        // TODO: Set the session to null

        // TODO: Print the close reason for debug purpose

        // TODO: Cancel the timer
    }

    @OnMessage
    public void onMessage(String message) {
        // TODO: Parse the message into a JsonNode

        // TODO: Call the callback
    }

    public void sendMessage(String message) {
        // TODO: If the current session is not null and open, send the message
    }

    public void stop() throws Exception {
        // TODO: Cancel the timer

        // TODO: If the current session is not null and open, close it with the reason "Fine exit"
    }
}
