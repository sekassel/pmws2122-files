package de.uniks.pmws2122.controller;

import de.uniks.pmws2122.StageManager;
import de.uniks.pmws2122.model.ModelService;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SetupScreenController {
    private final ModelService modelService;
    private Parent view;

    private TextField playerBlackTextField;
    private TextField playerWhiteTextField;
    private Button createGameButton;

    public SetupScreenController(ModelService modelService, Parent view) {
        this.modelService = modelService;
        this.view = view;
    }

    public void init() {
        // Load view references
        this.playerBlackTextField = (TextField) this.view.lookup("#playerNameBlackInput");
        this.playerWhiteTextField = (TextField) this.view.lookup("#playerNameWhiteInput");
        this.createGameButton = (Button) this.view.lookup("#createGameButton");

        // Add action listeners
        this.createGameButton.setOnAction(this::onCreateGameButtonPressed);
    }

    public void stop() {
    }

    // action listeners
    private void onCreateGameButtonPressed(ActionEvent event) {
        // Check if both text fields are filled
        if (this.playerBlackTextField.getText().isEmpty() || this.playerWhiteTextField.getText().isEmpty()) {
            return;
        }

        // Build the game
        this.modelService.buildGame(this.playerBlackTextField.getText(), this.playerWhiteTextField.getText());

        // Change the view
        StageManager.showIngameScreen();
    }
}
