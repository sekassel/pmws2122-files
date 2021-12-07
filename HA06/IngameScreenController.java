package de.uniks.pmws2122.controller;

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

        // Add action listeners

        // WIP: Start game and set text for currentPlayerNameLabel

    }

    public void stop() {
    }

    // Action listeners
    private void onGiveUpButtonPressed(ActionEvent event) {
        // Change the view
    }
}
