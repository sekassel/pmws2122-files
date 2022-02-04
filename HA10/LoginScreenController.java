package de.uniks.pmws2122.icp.controller;

import de.uniks.pmws2122.icp.StageManager;
import de.uniks.pmws2122.icp.model.ModelService;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginScreenController {
    private final ModelService modelService;
    private final Parent view;

    private TextField nicknameTextField;
    private Button loginButton;

    public LoginScreenController(ModelService modelService, Parent view) {
        this.modelService = modelService;
        this.view = view;
    }

    public void init() {
        // Load view
        this.nicknameTextField = (TextField) this.view.lookup("#nicknameTextField");
        this.loginButton = (Button) this.view.lookup("#loginButton");

        // Add action listeners
        this.loginButton.setOnAction(this::onLoginButtonPressed);
    }

    public void stop() {}

    // Action listeners
    private void onLoginButtonPressed(ActionEvent event) {
        String nickname = this.nicknameTextField.getText();
        if (nickname != null && !nickname.isEmpty()) {
            // TODO: Make a rest call to login, build the chat and switch to the chat screen if success, otherwise show error message
            System.out.println("Login with " + nickname);
            StageManager.showChatScreen();
        }
    }
}
