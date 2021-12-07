package de.uniks.pmws2122.controller;

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

        // Add action listeners

    }

    public void stop() {
    }

    // action listeners
    private void onCreateGameButtonPressed(ActionEvent event) {
        // Check if both text fields are filled

        // Build the game

        // Change the view
    }
}
