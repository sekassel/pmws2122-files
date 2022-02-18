package de.uniks.pmws2122.chat.client.net;

import de.uniks.pmws2122.chat.client.util.JsonUtil;
import kong.unirest.JsonNode;

import javax.websocket.*;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import static de.uniks.pmws2122.chat.client.Constants.NOOP;

@ClientEndpoint
public class WebsocketClient {
    private final Timer noopTimer;
    private final Consumer<JsonNode> callback;

    private Session session;

    public WebsocketClient(String endpoint, Consumer<JsonNode> callback) {
        // Create a new timer and save the callback
        this.noopTimer = new Timer();
        this.callback = callback;

        // Create and connect the websocket client
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(endpoint));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        // Save the session
        this.session = session;

        // Start the timer to send noop messages every 30 seconds
        this.noopTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText(NOOP);
                        session.getBasicRemote().flushBatch();
                    } catch (Exception e) {
                        System.err.println("Can not send NOOP");
                    }
                }
            }
        }, 0, 1000 * 30);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        // Set the session to null
        this.session = null;

        // Print the close reason for debug purpose
        System.out.println("Websocket closed with reason: " + reason.getReasonPhrase());

        // Cancel the timer
        this.noopTimer.cancel();
    }

    @OnMessage
    public void onMessage(String message) {
        // Parse the message into a JsonNode
        JsonNode msg = JsonUtil.parse(message);

        // Call the callback
        this.callback.accept(msg);
    }

    public void sendMessage(String message) {
        // If the current session is not null and open, send the message
        if (this.session != null && this.session.isOpen()) {
            try {
                this.session.getBasicRemote().sendText(message);
                this.session.getBasicRemote().flushBatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() throws Exception {
        // Cancel the timer
        this.noopTimer.cancel();

        // If the current session is not null and open, close it with the reason "Fine exit"
        if (this.session != null && this.session.isOpen()) {
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Fine exit"));
        }
    }
}
