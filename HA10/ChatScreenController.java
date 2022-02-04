package de.uniks.pmws2122.icp.controller;

import de.uniks.pmws2122.icp.StageManager;
import de.uniks.pmws2122.icp.model.ModelService;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChatScreenController {
    private final ModelService modelService;
    private final Parent view;

    private TabPane chatTabPane;
    private Tab allTab;
    private TextField messageTextField;
    private Button sendButton;
    private ListView<String> userListView;
    private Button leaveButton;

    private final PropertyChangeListener allMessagesChangedListener = this::onAllMessagesChanged;

    public ChatScreenController(ModelService modelService, Parent view) {
        this.modelService = modelService;
        this.view = view;
    }

    public void init() {
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

         // TODO: Make a rest call to load all chat messages, if not success show a error message
    }

    public void stop() {
        // TODO: Remove property change listeners
    }

    // Property change listeners
    private void onAllMessagesChanged(PropertyChangeEvent event) {
        // TODO: If there is a new value, add the message to the content of the all tab
    }

    // Action listeners
    private void onSendButtonPressed(ActionEvent event) {
        String chatMessage = this.messageTextField.getText();
        if (chatMessage != null && !chatMessage.isEmpty()) {
            // TODO: Make a rest call to send the chat message if not success show a error message
            System.out.println(chatMessage);
            this.messageTextField.setText("");

            // TODO: Clear all chat messages from the current chat session / all tab and load the new from the server via Rest
        }
    }

    private void onLeaveButtonPressed(ActionEvent event) {
        // TODO: Make a rest call to logout and switch to the login screen if success, otherwise show error message
        System.out.println("Logout");
        StageManager.showLoginScreen();
    }
}
