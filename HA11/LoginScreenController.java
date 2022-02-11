package de.uniks.pmws2122.icp.controller;

import de.uniks.pmws2122.icp.StageManager;
import de.uniks.pmws2122.icp.model.ModelService;
import de.uniks.pmws2122.icp.net.RestService;
import de.uniks.pmws2122.icp.util.JsonUtil;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class LoginScreenController {
    private final ModelService modelService;
    private final Parent view;

    private TextField nicknameTextField;
    private CheckBox rememberMeCheckBox;
    private Button loginButton;

    public LoginScreenController(ModelService modelService, Parent view) {
        this.modelService = modelService;
        this.view = view;
    }

    public void init() {
        // TODO: Load view
        this.nicknameTextField = (TextField) this.view.lookup("#nicknameTextField");
        this.loginButton = (Button) this.view.lookup("#loginButton");

        // Add action listeners
        this.loginButton.setOnAction(this::onLoginButtonPressed);

        // TODO: Load the config.json using the ResourceManager
        // TODO: If the "rememberMe" flag is true, select the "rememberMeCheckBox" and add the "name" from the config to the "nicknameTextField"
    }

    public void stop() {}

    // Action listeners
    private void onLoginButtonPressed(ActionEvent event) {
        String nickname = this.nicknameTextField.getText();
        if (nickname != null && !nickname.isEmpty()) {
            // Make a rest call to login, build the chat and switch to the chat screen if success, otherwise show error message
            boolean success = RestService.login(nickname);
            if (success) {
                this.modelService.buildChat(nickname);
                StageManager.showChatScreen();
            } else {
                new Alert(Alert.AlertType.ERROR, "Can not login, check your username").showAndWait();
            }

            // TODO: If the "rememberMeCheckBox" is selected, create remember me config with the "JsonUtil"
            // TODO: Otherwise save the default content of the config file
        }
    }
}
