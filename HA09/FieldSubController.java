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

        // Add property change listeners
        this.model.listeners().addPropertyChangeListener(Field.PROPERTY_MAN, this.manPlacedListener);
    }

    public void stop() {
        // Remove property change listeners from field
        this.model.listeners().removePropertyChangeListener(Field.PROPERTY_MAN, this.manPlacedListener);
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
        // The action of the current player needs to be "placing"
        if (this.modelService.getGame().getCurrentPlayer().getAction().equals(PLAYER_ACTION_PLACING)) {
            // Place a man
            this.modelService.placeMan(this.model);

            // Check for a mill
            this.modelService.checkMill(this.model.getMan());

            // Check whether the action of the current player is "remove" now. If yes exit this method
            if (this.modelService.getGame().getCurrentPlayer().getAction().equals(PLAYER_ACTION_REMOVE)) {
                return;
            }

            // Check for a winner
            this.modelService.checkWinner();

            // Next turn
            this.modelService.nextTurn();
        }
    }

    private void gamePhasePlacingManNotNull() {
        // The action of the current player needs to be "remove"
        if (this.modelService.getGame().getCurrentPlayer().getAction().equals(PLAYER_ACTION_REMOVE)) {
            // The man of "this field" needs be owned by the opponent of the current player
            if (this.model.getMan().getOwner().equals(this.modelService.getGame().getCurrentPlayer().getNext())) {
                // Remove the man
                this.modelService.removeMan(this.model.getMan());

                //  Check for a winner
                this.modelService.checkWinner();

                // Next turn
                this.modelService.nextTurn();
            }
        }
    }

    private void gamePhaseMovingManNull() {
        // Helper variables
        Man selectedMan = this.modelService.getCurrentSelectedMan();
        Field selectedField = this.modelService.getCurrentSelectedField();
        String playerAction = this.modelService.getGame().getCurrentPlayer().getAction();

        // There needs to be a selected man and a not selected field, also the player action should be "moving" or "flying"
        if (selectedMan != null && selectedField == null && (playerAction.equals(PLAYER_ACTION_MOVING) || playerAction.equals(PLAYER_ACTION_FLYING))) {
            // Get the position of the current selected man
            Field startField = selectedMan.getPosition();
            // Check whether the clicked field is a direct neighbor of "this field" or that the player action is "flying"
            if (((startField.getRight() != null && startField.getRight().equals(this.model)) ||
                 (startField.getLeft() != null && startField.getLeft().equals(this.model)) || 
                 (startField.getTop() != null && startField.getTop().equals(this.model)) ||
                 (startField.getBottom() != null && startField.getBottom().equals(this.model))) ||
                 playerAction.equals(PLAYER_ACTION_FLYING)) {
                // Set the current selected field in the ModelService to "this field"
                this.modelService.setCurrentSelectedField(this.model);

                // Move man
                this.modelService.moveMan();

                // Check for a mill
                this.modelService.checkMill(this.model.getMan());

                // Check whether the action of the current player is "remove" now. If yes exit this method
                if (this.modelService.getGame().getCurrentPlayer().getAction().equals(PLAYER_ACTION_REMOVE)) {
                    return;
                }

                //  Check for a winner
                this.modelService.checkWinner();

                // Next turn
                this.modelService.nextTurn();
            }
        }
    }

    private void gamePhaseMovingManNotNull() {
        // Helper variables
        Man selectedMan = this.modelService.getCurrentSelectedMan();
        Field selectedField = this.modelService.getCurrentSelectedField();
        Player currentPlayer = this.modelService.getGame().getCurrentPlayer();
        String playerAction = currentPlayer.getAction();

        // Check that the current selected man is null, the current selected field is null, the owner of the man on "this field" is the current player and that the action of the current player is "moving" or "flying"
        if (selectedMan == null && selectedField == null && this.model.getMan().getOwner().equals(currentPlayer) && (playerAction.equals(PLAYER_ACTION_MOVING) || playerAction.equals(PLAYER_ACTION_FLYING))) {
            // Set the current selected man in the ModelService to "this field man"
            this.modelService.setCurrentSelectedMan(this.model.getMan());

            // Change the color of this circle so the user can see the selected man
            this.view.setFill(Color.AQUAMARINE);
        // Check that the current selected man is not null, the current selected field is null, the owner of the man on "this field" is the current player, the man of "this field" is the current selected man
        // and that the action of the current player is "moving" or "flying"
        } else if (selectedMan != null && selectedField == null && this.model.getMan().getOwner().equals(currentPlayer) && this.model.getMan().equals(selectedMan) && (playerAction.equals(PLAYER_ACTION_MOVING) || playerAction.equals(PLAYER_ACTION_FLYING))) {
            // Set the current selected man in the ModelService to null
            this.modelService.setCurrentSelectedMan(null);

            // Change the color of this circle to the color of the owner of the man on "this field"
            this.view.setFill(this.model.getMan().getColor().equals(COLOR_BLACK) ? Color.BLACK : Color.WHITE);
        // Check that the player action of the current player is "remove" 
        } else if (playerAction.equals(PLAYER_ACTION_REMOVE)) {
            // The man of this field needs to be owned by the opponent of the current player
            if (this.model.getMan().getOwner().equals(currentPlayer.getNext())) {
                // Remove the man from "this field"
                this.modelService.removeMan(this.model.getMan());
    
                // Check for a winner
                this.modelService.checkWinner();
    
                // Next turn
                this.modelService.nextTurn();
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

    // Property change listeners
    private void onManPlacedChanged(PropertyChangeEvent event) {
        // If there is a new value, change the color of the circle depending of the player color and change to size to 15.0
        if (event.getNewValue() != null) {
            Man newMan = (Man) event.getNewValue();
            this.view.setFill(newMan.getColor().equals(COLOR_BLACK) ? Color.BLACK : Color.WHITE);
            this.view.setRadius(15.0);
        // Otherwise and there is an old value, change the color back to black and change the size to 7.0
        } else if (event.getOldValue() != null) {
            this.view.setFill(Color.BLACK);
            this.view.setRadius(7.0);
        }
    }
}
