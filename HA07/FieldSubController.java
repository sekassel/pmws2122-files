package de.uniks.pmws2122.controller;

import de.uniks.pmws2122.model.Field;
import de.uniks.pmws2122.model.ModelService;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class FieldSubController {
    private final ModelService modelService;
    private final Field model;
    private final Circle view;

    public FieldSubController(ModelService modelService, Field model, Circle view) {
        this.modelService = modelService;
        this.model = model;
        this.view = view;
    }

    public void init() {
        // TODO: Add mouse listeners
    }

    public void stop() {
    }

    // Action listeners
    private void onFieldClicked(MouseEvent event) {
    }

    // TODO: Mouse hovers over field
    private void onFieldMouseHoverEnter(MouseEvent event) {
        // TODO: Change the view 
    }

    // TODO: Mouse leaves the field 
    private void onFieldMouseHoverExit(MouseEvent event) {
        // TODO: Change the view
    }
}
