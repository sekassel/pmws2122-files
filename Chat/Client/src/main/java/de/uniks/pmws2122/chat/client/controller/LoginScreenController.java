package de.uniks.pmws2122.chat.client.controller;

import de.uniks.pmws2122.chat.client.StageManager;
import de.uniks.pmws2122.chat.client.model.ModelService;
import de.uniks.pmws2122.chat.client.net.RestService;
import de.uniks.pmws2122.chat.client.util.JsonUtil;
import de.uniks.pmws2122.chat.client.util.ResourceManager;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import kong.unirest.json.JSONObject;

import static de.uniks.pmws2122.chat.client.Constants.JSON_NAME;
import static de.uniks.pmws2122.chat.client.Constants.JSON_REMEMBER_ME;

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
        // Load view
        this.nicknameTextField = (TextField) this.view.lookup("#nicknameTextField");
        this.rememberMeCheckBox = (CheckBox) this.view.lookup("#rememberMeCheckBox");
        this.loginButton = (Button) this.view.lookup("#loginButton");

        // Add action listeners
        this.loginButton.setOnAction(this::onLoginButtonPressed);

        // Load the config.json using the ResourceManager
        // If the "rememberMe" flag is true, select the "rememberMeCheckBox" and add the "name" from the config to the "nicknameTextField"
        JSONObject config = ResourceManager.loadConfig();
        if (config.getBoolean(JSON_REMEMBER_ME)) {
            this.rememberMeCheckBox.setSelected(true);
            this.nicknameTextField.setText(config.getString(JSON_NAME));
        }
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

            // If the "rememberMeCheckBox" is selected, create remember me config with the "JsonUtil"
            if (this.rememberMeCheckBox.isSelected()) {
                ResourceManager.saveConfig(JsonUtil.createRememberMeConfig(nickname));
            // Otherwise save the default content of the config file
            } else {
                ResourceManager.saveConfig(JsonUtil.createDefaultConfig());
            }
        }
    }
}
