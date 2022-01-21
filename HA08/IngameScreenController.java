package de.uniks.pmws2122.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import de.uniks.pmws2122.StageManager;
import de.uniks.pmws2122.model.Field;
import de.uniks.pmws2122.model.ModelService;
import de.uniks.pmws2122.model.Player;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

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
        // TODO: Load view references
        this.currentPlayerNameLabel = (Label) this.view.lookup("#currentPlayerNameLabel");
        this.giveUpButton = (Button) this.view.lookup("#giveUpButton");

        // Add action listeners
        this.giveUpButton.setOnAction(this::onGiveUpButtonPressed);

        // TODO: Add property change listeners

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
                // TODO: Remove this line after the property change listeners are implemented
                this.currentPlayerNameLabel.setText(player.getName());        
            }
        );
    }

    public void stop() {
        // TODO: Remove property change listeners from game and current player

        // Stop all sub controller
        this.fieldCons.forEach(FieldSubController::stop);
        this.fieldCons.clear();
    }

    // Action listeners
    private void onGiveUpButtonPressed(ActionEvent event) {
        // TODO: The player after the current player, should be the winner
    }

    // TODO: Property change listeners
    private void onWinnerChanged(PropertyChangeEvent event) {
        // TODO: If there is a new value, show an alert which shows the winner. After a click go to the SetupScreen
    }

    private void onCurrentPlayerChanged(PropertyChangeEvent event) {
        // TODO: If there is a new value, set the "currentPlayerNameLabel" / "currentPlayerColorDisplay" / "currentPlayerActionLabel". Also, add/remove the "onCurrentPlayerActionListener" to the currentPlayer/oldCurrentPlayer.
    }

    private void onCurrentPlayerActionChanged(PropertyChangeEvent event) {
        // TODO: If there is a new value, set the "currentPlayerActionLabel" 
    }
}
