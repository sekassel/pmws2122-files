package de.uniks.pmws2122.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.uniks.pmws2122.model.Field;
import de.uniks.pmws2122.model.Man;
import de.uniks.pmws2122.model.ModelService;
import de.uniks.pmws2122.model.Player;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static de.uniks.pmws2122.Constants.*;

public class FieldSubController {
    private final ModelService modelService;
    private final Field model;
    private final Circle view;

    // Property change listener for model changes
    private final PropertyChangeListener manPlacedListener = this::onManPlacedChanged;

    public FieldSubController(ModelService modelService, Field model, Circle view) {
        this.modelService = modelService;
        this.model = model;
        this.view = view;
    }

    public void init() {
        // Add mouse listeners
        this.view.setOnMouseReleased(this::onFieldClicked);
        this.view.setOnMouseEntered(this::onFieldMouseHoverEnter);
        this.view.setOnMouseExited(this::onFieldMouseHoverExit);

        // TODO: Add property change listeners
    }

    public void stop() {
        // TODO: Remove property change listeners from field
    }

    // Action listeners
    private void onFieldClicked(MouseEvent event) {
        // Current game phase is "placing"
        if (this.modelService.getGame().getPhase().equals(GAME_PHASE_PLACING)) {
            // The clicked field is empty
            if (this.model.getMan() == null) {
                this.gamePhasePlacingManNull();
            // The clicked field has a man
            } else {
                this.gamePhasePlacingManNotNull();
            }
        // Current game phase is "moving"
        } else {
            // The clicked field is empty
            if (this.model.getMan() == null) {
                this.gamePhaseMovingManNull();
            // The clicked field has a man
            } else {
                this.gamePhaseMovingManNotNull();
            }
        }
    }

    private void gamePhasePlacingManNull() {
        // TODO: The action of the current player needs to be "placing"
        if (...) {
            // TODO: Place a man

            // TODO: Check for a mill

            // TODO: Check whether the action of the current player is "remove" now. If yes exit this method

            // TODO: Check for a winner

            // TODO: Next turn
        }
    }

    private void gamePhasePlacingManNotNull() {
        // TODO: The action of the current player needs to be "remove"
        if (...) {
            // TODO: The man of "this field" needs to be owned by the opponent of the current player 
            if (...) {
                // TODO: Remove the man

                // TODO: Check for a winner

                // TODO: Next turn
            }
        }
    }

    private void gamePhaseMovingManNull() {
        // Helper variables
        Man selectedMan = this.modelService.getCurrentSelectedMan();
        Field selectedField = this.modelService.getCurrentSelectedField();
        String playerAction = this.modelService.getGame().getCurrentPlayer().getAction();

        // TODO: There needs to be a selected man and a not selected field, also the player action should be "moving" or "flying"
        if (...) {
            // Get the position of the current selected man
            Field startField = selectedMan.getPosition();

            // TODO: Check whether the clicked field is a direct neighbor of "this field" or that the player action is "flying"
            if (...) {
                // TODO: Set the current selected field in the ModelService to "this field"

                // TODO: Move man

                // TODO: Check for a mill

                // TODO: Check whether the action of the current player is "remove" now. If yes exit this method

                // TODO: Check for a winner

                // TODO: Next turn
            }
        }
    }

    private void gamePhaseMovingManNotNull() {
        // Helper variables
        Man selectedMan = this.modelService.getCurrentSelectedMan();
        Field selectedField = this.modelService.getCurrentSelectedField();
        Player currentPlayer = this.modelService.getGame().getCurrentPlayer();
        String playerAction = currentPlayer.getAction();

        // TODO: Check that the current selected man is null, the current selected field is null, the owner of the man on "this field" is the current player and that the action of the current player is "moving" or "flying"
        if (...) {
            // TODO: Set the current selected man in the ModelService to "this field man"

            // TODO: Change the color of this circle so the user can see the selected man

        // TODO: Check that the current selected man is not null, the current selected field is null, the owner of the man on "this field" is the current player, the man of "this field" is the current selected man
        // TODO: and that the action of the current player is "moving" or "flying"
        } else if (...) {
            // TODO: Set the current selected man in the ModelService to null

            // TODO: Change the color of this circle to the color of the owner of the man on "this field"

        // TODO: Check that the player action of the current player is "remove"
        } else if (...) {
            // TODO: The man of this field needs to be owned by the opponent of the current player
            if (...) {
                // TODO: Remove the man from "this field"

                // TODO: Check for a winner

                // TODO: Next turn
            }
        }
    }

    // Mouse hovers over field
    private void onFieldMouseHoverEnter(MouseEvent event) {
        // Change the view
        if (this.model.getMan() == null) {
            this.view.setFill(Color.GRAY);
            this.view.setRadius(12.0);
        }
    }

    // Mouse leaves the field 
    private void onFieldMouseHoverExit(MouseEvent event) {
        // Change the view
        if (this.model.getMan() == null) {
            this.view.setFill(Color.BLACK);
            this.view.setRadius(7.0);
        }
    }

    // TODO: Property change listeners
    private void onManPlacedChanged(PropertyChangeEvent event) {
        // TODO: If there is a new value, change the color of the circle depending of the player color and change to size to 15.0
        // TODO: Otherwise and there is an old value, change the color back to black and the size to 7.0
    }
}
