package de.uniks.pmws2122.icp.controller;

import de.uniks.pmws2122.icp.StageManager;
import de.uniks.pmws2122.icp.model.Chat;
import de.uniks.pmws2122.icp.model.ChatMessage;
import de.uniks.pmws2122.icp.model.ModelService;
import de.uniks.pmws2122.icp.net.RestService;
import de.uniks.pmws2122.icp.net.WebsocketClient;
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
        // TODO: Fill the socket handler map with possible actions "chat", "userJoined" and "userLeft" and add the corresponding handler function

        // TODO: Start the websocket client

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

         // TODO: Add property change listeners
         this.modelService.getCurrentChatSession().listeners().addPropertyChangeListener(Chat.PROPERTY_ALL_MESSAGES, this.allMessagesChangedListener);

         // Make a rest call to load all chat messages, if not success show a error message
         try {
            this.modelService.buildAllChatMessages(RestService.getAllMessages());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Can not load global chat").showAndWait();
        }

        // TODO: Make rest call to load the logged in users, if not success show an error message
    }

    public void stop() {
        // TODO: Remove property change listeners
        this.modelService.getCurrentChatSession().listeners().removePropertyChangeListener(Chat.PROPERTY_ALL_MESSAGES, this.allMessagesChangedListener);

        // TODO: Stop the websocket client

        // TODO: Clear the socket handler map
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
        // TODO: If there is a new value, add the user to the list view

        // TODO: Or if there is no new value but an old value, remove the user from the list view
    }

    // Action listeners
    private void onSendButtonPressed(ActionEvent event) {
        String chatMessage = this.messageTextField.getText();
        if (chatMessage != null && !chatMessage.isEmpty()) {
            // TODO: Send the chat message over the websocket client
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
        // TODO: Call the correct handler from the socket handler map depending of the action from the message
        // TODO: If there is no corresponding handler in the map, make a error print to console
    }

    private void handleChatAction(JSONObject message) {
        // TODO: Build the chat message with the model service
        // TODO: ATTENTION!!! This call comes from a different Thread, use Platform.runLater()
    }

    private void handleUserJoinedAction(JSONObject message) {
        // TODO: Build the user with model service
        // TODO: ATTENTION!!! This call comes from a different Thread, use Platform.runLater()
    }

    private void handleUserLeftAction(JSONObject message) {
        // TODO: Remove the user with the model service
        // TODO: ATTENTION!!! This call comes from a different Thread, use Platform.runLater()
    }
}
