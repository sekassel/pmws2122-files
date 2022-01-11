package de.uniks.pmws2122.controller;

import java.util.ArrayList;
import java.util.List;

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

    // This variable is needed for the starting/stopping of the field controllers
    private List<FieldSubController> fieldCons = new ArrayList<>();

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

        // TODO: Initialize field controller for the game board 

        // TODO: Start game and set text for currentPlayerNameLabel, after player picked a beginner
        this.modelService.startGame(this.modelService.getGame().getPlayers().get(0));
        this.currentPlayerNameLabel.setText(modelService.getGame().getCurrentPlayer().getName());
    }

    public void stop() {
        // TODO: Stop all sub controller
    }

    // Action listeners
    private void onGiveUpButtonPressed(ActionEvent event) {
        // Change the view
        StageManager.showSetupScreen();
    }
}
