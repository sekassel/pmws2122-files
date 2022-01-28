package de.uniks.pmws2122.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import de.uniks.pmws2122.StageManager;
import de.uniks.pmws2122.model.Field;
import de.uniks.pmws2122.model.Game;
import de.uniks.pmws2122.model.ModelService;
import de.uniks.pmws2122.model.Player;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static de.uniks.pmws2122.Constants.*;

public class IngameScreenController {
    private final ModelService modelService;
    private Parent view;

    private Label currentPlayerNameLabel;
    private Circle currentPlayerColorDisplay;
    private Label currentPlayerActionLabel;
    private Button giveUpButton;

    // These variables are needed for the property change listener to listen on model changes
    private final PropertyChangeListener winnerChangedListener = this::onWinnerChanged;
    private final PropertyChangeListener currentPlayerChangedListener = this::onCurrentPlayerChanged;
    private final PropertyChangeListener currentPlayerActionChangedListener = this::onCurrentPlayerActionChanged;

    // This variable is needed for the starting/stopping of the field controllers
    private List<FieldSubController> fieldCons = new ArrayList<>();

    public IngameScreenController(ModelService modelService, Parent view) {
        this.modelService = modelService;
        this.view = view;
    }

    public void init() {
        // Load view references
        this.currentPlayerNameLabel = (Label) this.view.lookup("#currentPlayerNameLabel");
        this.currentPlayerColorDisplay = (Circle) this.view.lookup("#currentPlayerColorDisplay");
        this.currentPlayerActionLabel = (Label) this.view.lookup("#currentPlayerActionLabel");
        this.giveUpButton = (Button) this.view.lookup("#giveUpButton");

        // Add action listeners
        this.giveUpButton.setOnAction(this::onGiveUpButtonPressed);

        // Add property change listeners
        this.modelService.getGame().listeners().addPropertyChangeListener(Game.PROPERTY_WINNER, this.winnerChangedListener);
        this.modelService.getGame().listeners().addPropertyChangeListener(Game.PROPERTY_CURRENT_PLAYER, this.currentPlayerChangedListener);

        // Initialize field controller for the game board 
        for (Field field : this.modelService.getGame().getFields()) {
            FieldSubController fCon = new FieldSubController(this.modelService, field, (Circle) this.view.lookup("#" + field.getCoordinate() + "FieldDisplay"));
            fCon.init();
            this.fieldCons.add(fCon);
        }

        // Start game and set text for currentPlayerNameLabel, after player picked a beginner
        ChoiceDialog<Player> chooseChoiceDialog = new ChoiceDialog<>(this.modelService.getGame().getPlayers().get(0), this.modelService.getGame().getPlayers());
        chooseChoiceDialog.setTitle("Choose beginner");
        chooseChoiceDialog.setHeaderText("Choose the player which place the first man");
        chooseChoiceDialog
            .showAndWait()
            .ifPresent((player) -> {
                this.modelService.startGame(player);
            }
        );
    }

    public void stop() {
        // Remove property change listeners from game and current player
        this.modelService.getGame().listeners().removePropertyChangeListener(Game.PROPERTY_WINNER, this.winnerChangedListener);
        this.modelService.getGame().listeners().removePropertyChangeListener(Game.PROPERTY_CURRENT_PLAYER, this.currentPlayerChangedListener);
        this.modelService.getGame().getCurrentPlayer().listeners().removePropertyChangeListener(Player.PROPERTY_ACTION, this.currentPlayerActionChangedListener);

        // Stop all sub controller
        this.fieldCons.forEach(FieldSubController::stop);
        this.fieldCons.clear();
    }

    // Action listeners
    private void onGiveUpButtonPressed(ActionEvent event) {
        // Set the other player as winner
        this.modelService.getGame().setWinner(this.modelService.getGame().getCurrentPlayer().getNext());
    }

    // Property change listeners
    private void onWinnerChanged(PropertyChangeEvent event) {
        // If there is a new value, show an alert which shows the winner. After a click go to the SetupScreen
        if (event.getNewValue() != null) {
            Player winner = (Player) event.getNewValue();
            new Alert(Alert.AlertType.CONFIRMATION, winner.getName() + " has won the game :D")
                .showAndWait()
                .ifPresent((btn) -> StageManager.showSetupScreen());
        }
    }

    private void onCurrentPlayerChanged(PropertyChangeEvent event) {
        // If there is a new value, set the "currentPlayerNameLabel" / "currentPlayerColorDisplay" / "currentPlayerActionLabel". Also, add/remove the "onCurrentPlayerActionListener" to the currentPlayer/oldCurrentPlayer.
        if (event.getNewValue() != null) {
            Player currentPlayer = (Player) event.getNewValue();
            this.currentPlayerNameLabel.setText(currentPlayer.getName());
            this.currentPlayerColorDisplay.setFill(currentPlayer.getColor().equals(COLOR_BLACK) ? Color.BLACK : Color.WHITE);
            this.currentPlayerActionLabel.setText(currentPlayer.getAction());

            if (event.getOldValue() != null) {
                Player oldCurrentPlayer = (Player) event.getOldValue();
                oldCurrentPlayer.listeners().removePropertyChangeListener(Player.PROPERTY_ACTION, this.currentPlayerActionChangedListener);
            }

            currentPlayer.listeners().addPropertyChangeListener(Player.PROPERTY_ACTION, this.currentPlayerActionChangedListener);
        }
    }

    private void onCurrentPlayerActionChanged(PropertyChangeEvent event) {
        // If there is a new value, set the "currentPlayerActionLabel"
        if (event.getNewValue() != null) {
            String newAction = (String) event.getNewValue();
            this.currentPlayerActionLabel.setText(newAction);
        }
    }
}
