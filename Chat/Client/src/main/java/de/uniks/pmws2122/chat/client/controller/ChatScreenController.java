package de.uniks.pmws2122.chat.client.controller;

import de.uniks.pmws2122.chat.client.StageManager;
import de.uniks.pmws2122.chat.client.model.Chat;
import de.uniks.pmws2122.chat.client.model.ChatMessage;
import de.uniks.pmws2122.chat.client.model.ModelService;
import de.uniks.pmws2122.chat.client.model.User;
import de.uniks.pmws2122.chat.client.net.RestService;
import de.uniks.pmws2122.chat.client.net.WebsocketClient;
import de.uniks.pmws2122.chat.client.util.JsonUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import static de.uniks.pmws2122.chat.client.Constants.*;

public class ChatScreenController {
    private final ModelService modelService;
    private final Parent view;

    private final Map<String, Consumer<JSONObject>> socketHandler = new LinkedHashMap<>();

    private WebsocketClient appClient;

    private TabPane chatTabPane;
    private Tab allTab;
    private TextField messageTextField;
    private Button sendButton;
    private ListView<String> userListView;
    private Button leaveButton;

    private final PropertyChangeListener allMessagesChangedListener = this::onAllMessagesChanged;
    private final PropertyChangeListener onlineUsersChangedListener = this::onOnlineUsersChanged;

    public ChatScreenController(ModelService modelService, Parent view) {
        this.modelService = modelService;
        this.view = view;
    }

    public void init() {
        // Fill the socket handler map with possible actions "chat", "userJoined" and "userLeft" and add the corresponding handler function
        this.socketHandler.put(JSON_ACTION_CHAT, this::handleChatAction);
        this.socketHandler.put(JSON_ACTION_USER_JOINED, this::handleUserJoinedAction);
        this.socketHandler.put(JSON_ACTION_USER_LEFT, this::handleUserLeftAction);

        // Start the websocket client
        this.appClient = new WebsocketClient(WS_APPLICATION + this.modelService.getCurrentChatSession().getCurrentNickname(), this::onIncomingMessages);

        // Load view references
        this.chatTabPane = (TabPane) this.view.lookup("#chatTabPane");
        this.allTab = this.chatTabPane.getTabs().get(0);
        this.messageTextField = (TextField) this.view.lookup("#messageTextField");
        this.sendButton = (Button) this.view.lookup("#sendButton");
        this.userListView = (ListView<String>) this.view.lookup("#userListView");
        this.leaveButton = (Button) this.view.lookup("#leaveButton");

        // Add action listeners
        this.sendButton.setOnAction(this::onSendButtonPressed);
        this.leaveButton.setOnAction(this::onLeaveButtonPressed);

        // Add property change listeners
        this.modelService.getCurrentChatSession().listeners().addPropertyChangeListener(Chat.PROPERTY_ALL_MESSAGES, this.allMessagesChangedListener);
        this.modelService.getCurrentChatSession().listeners().addPropertyChangeListener(Chat.PROPERTY_ONLINE_USER, this.onlineUsersChangedListener);

        // Make a rest call to load all chat messages, if not success show an error message
        try {
            this.modelService.buildAllChatMessages(RestService.getAllMessages());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Can not load global chat").showAndWait();
        }

        // Make rest call to load the logged in users, if not success show an error message
        try {
            this.modelService.buildUsers(RestService.getAllLoggedInUsers());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Can not load logged in users").showAndWait();
        }
    }

    public void stop() {
        // Remove property change listeners
        this.modelService.getCurrentChatSession().listeners().removePropertyChangeListener(Chat.PROPERTY_ALL_MESSAGES, this.allMessagesChangedListener);
        this.modelService.getCurrentChatSession().listeners().removePropertyChangeListener(Chat.PROPERTY_ONLINE_USER, this.onlineUsersChangedListener);

        // Stop the websocket client
        try {
            this.appClient.stop();
        } catch (Exception e) {
            System.err.println("Error during closing the websocket client");
            e.printStackTrace();
        }

        // Clear the socket handler map
        this.socketHandler.clear();
    }

    // Property change listeners
    private void onAllMessagesChanged(PropertyChangeEvent event) {
        // If there is a new value, add the message to the content of the all tab
        if (event.getNewValue() != null) {
            ChatMessage newMessage = (ChatMessage) event.getNewValue();
            ((VBox)((ScrollPane)this.allTab.getContent()).getContent()).getChildren().add(new Label(newMessage.getFrom() + ": " + newMessage.getMsg()));
        }
    }

    private void onOnlineUsersChanged(PropertyChangeEvent event) {
        // If there is a new value, add the user to the list view
        if (event.getNewValue() != null) {
            User newUser = (User) event.getNewValue();
            this.userListView.getItems().add(newUser.getName());
        }
        // If there is no new value but an old value, remove the user from the list view
        else if (event.getOldValue() != null) {
            User oldUser = (User) event.getOldValue();
            this.userListView.getItems().remove(oldUser.getName());
        }
    }

    // Action listeners
    private void onSendButtonPressed(ActionEvent event) {
        String chatMessage = this.messageTextField.getText();
        // In HA9 print message on console, otherwise send message via REST
        if (chatMessage != null && !chatMessage.isEmpty()) {
            this.messageTextField.clear();

            // Send the chat message over the websocket client
            this.appClient.sendMessage(JsonUtil.createChatMessage(this.modelService.getCurrentChatSession().getCurrentNickname(), chatMessage));
        }
    }

    private void onLeaveButtonPressed(ActionEvent event) {
        // Make a rest call to logout and switch to the login screen if success, otherwise show error message
        boolean success = RestService.logout(this.modelService.getCurrentChatSession().getCurrentNickname());
        if (success) {
            StageManager.showLoginScreen();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can not login, check your username").showAndWait();
        }
    }

    // Websocket and handler
    private void onIncomingMessages(JsonNode message) {
        // Call the correct handler from the socket handler map depending of the action from the message
        // If there is no corresponding handler in the map, make a error print to console
        JSONObject json = message.getObject();
        try {
            this.socketHandler.get(json.getString(JSON_ACTION)).accept(json);
        } catch (Exception e) {
            System.err.println("Handler for action " + json.getString(JSON_ACTION) + " not found");
            e.printStackTrace();
        }
    }

    private void handleChatAction(JSONObject message) {
        // Build the chat message with the model service
        // ATTENTION!!! This call comes from a different Thread, use Platform.runLater()
        Platform.runLater(() -> this.modelService.buildAllChatMessage(message));
    }

    private void handleUserJoinedAction(JSONObject message) {
        // Build the user with model service
        // ATTENTION!!! This call comes from a different Thread, use Platform.runLater()
        Platform.runLater(() -> this.modelService.buildUser(message.getString(JSON_NAME)));
    }

    private void handleUserLeftAction(JSONObject message) {
        // Remove the user with the model service
        // ATTENTION!!! This call comes from a different Thread, use Platform.runLater()
        Platform.runLater(() -> this.modelService.removeUser(message.getString(JSON_NAME)));
    }
}
