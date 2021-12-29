package de.uniks.pmws2122.controller;

import de.uniks.pmws2122.StageManager;
import de.uniks.pmws2122.model.ModelService;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class IngameScreenController {
    private final ModelService modelService;
    private Parent view;

    private Label currentPlayerNameLabel;
    private Button giveUpButton;

    public IngameScreenController(ModelService modelService, Parent view) {
        this.modelService = modelService;
        this.view = view;
    }

    public void init() {
        // Load view references
        this.currentPlayerNameLabel = (Label) this.view.lookup("#currentPlayerNameLabel");
        this.giveUpButton = (Button) this.view.lookup("#giveUpButton");

        // Add action listeners
        this.giveUpButton.setOnAction(this::onGiveUpButtonPressed);

        // Start game and set text for currentPlayerNameLabel
        this.modelService.startGame(this.modelService.getGame().getPlayers().get(0));
        this.currentPlayerNameLabel.setText(modelService.getGame().getCurrentPlayer().getName());
    }

    public void stop() {
    }

    // Action listeners
    private void onGiveUpButtonPressed(ActionEvent event) {
        // Change the view
        StageManager.showSetupScreen();
    }
}
