package de.uniks.pmws2122.ai;

import static de.uniks.pmws2122.Constants.*;

import de.uniks.pmws2122.model.Field;
import de.uniks.pmws2122.model.Man;
import de.uniks.pmws2122.model.ModelService;
import de.uniks.pmws2122.model.Player;

public class Brain {
    ModelService ms;
    Player computerPlayer;

    public Brain(Player computerPlayer, ModelService ms) {
        this.computerPlayer = computerPlayer;
        this.ms = ms;
    }

    // ----------------------------------------------------------------------
    // TURN METHOD
    // ----------------------------------------------------------------------

    public void turn() {

        // first part of the move

        Man lastPutMan = null;
        if (this.computerPlayer.getAction().equals(PLAYER_ACTION_PLACING)) {
            lastPutMan = this.place();
        } else if (this.computerPlayer.getAction().equals(PLAYER_ACTION_MOVING)) {
            lastPutMan = this.move();
        } else if (this.computerPlayer.getAction().equals(PLAYER_ACTION_FLYING)) {
            lastPutMan = this.fly();
        }

        this.ms.checkMill(lastPutMan);

        // second part: player's action might be changed by now

        if (this.computerPlayer.getAction().equals(PLAYER_ACTION_REMOVE)) {
            this.remove();
        }

        this.ms.checkWinner();
        this.ms.nextTurn();
    }

    // ----------------------------------------------------------------------
    // HELPER METHODS FOR TURNING
    // ----------------------------------------------------------------------

    private Man place() {
        // TODO: set up some more helper variables if needed
        Field chosenField = null;

        // TODO: choose a field where I can place my man

        // print turn
        printPlacing(chosenField);

        // execute my move
        this.ms.placeMan(chosenField);

        return chosenField.getMan();
    }

    private Man move() {
        // TODO: set up some more helper variables if needed
        Man chosenMan = null;
        Field fieldToMoveTo = null;

        // TODO: select a man and a field to move to

        // TODO: change selected man and selected field in model service

        // print turn
        printMoving(chosenMan, fieldToMoveTo);

        // execute my move
        this.ms.moveMan();

        return chosenMan;
    }

    private Man fly() {
        // TODO: set up some more helper variables if needed
        Man chosenMan = null;
        Field fieldToMoveTo = null;

        // TODO: select a man and a field to fly to

        // TODO: change selected man and selected field in model service

        // print turn
        printFlying(chosenMan, fieldToMoveTo);

        // execute my move
        this.ms.moveMan();

        return chosenMan;
    }

    private void remove() {
        // TODO: set up some more helper variables if needed
        Man chosenMan = null;

        // TODO: choose a man to remove
        
        // print turn
        printRemoving(chosenMan);

        // execute my move
        this.ms.removeMan(chosenMan);
    }

    // ----------------------------------------------------------------------
    // HELPER METHODS FOR ...
    // ----------------------------------------------------------------------

    // TODO: some helper methods may be implemented here

    // ----------------------------------------------------------------------
    // PRINTING METHODS
    // ----------------------------------------------------------------------

    private void printPlacing(Field field) {
        String coordinate = field == null ? "null" : field.getCoordinate();

        System.out.println(computerPlayer.getName()
                + " placing on Field [ "
                + coordinate
                + " ]");
    }

    private void printMoving(Man man, Field destination) {
        printChangePos(man, destination, "moving");
    }

    private void printFlying(Man man, Field destination) {
        printChangePos(man, destination, "flying");
    }

    private void printChangePos(Man man, Field destination, String action) {
        String manColor = man == null ? "null" : man.getColor();
        String manOldPos = (man == null || man.getPosition() == null) ? "null" : man.getPosition().getCoordinate();
        String destCoord = destination == null ? "null" : destination.getCoordinate();

        System.out.println(computerPlayer.getName()
                + " " + action
                + " Man [ "
                + manColor
                + " ] from Field [ "
                + manOldPos
                + " ] to Field [ "
                + destCoord
                + " ]");
    }

    private void printRemoving(Man man) {
        String coordinate = "null";
        String manColor = "null";

        if (man != null) {
            manColor = man.getColor();
            if (man.getPosition() != null) {
                coordinate = man.getPosition().getCoordinate();
            }
        }

        System.out.println(computerPlayer.getName()
                + " removing [ "
                + manColor
                + " ] man from Field [ "
                + coordinate
                + " ]");
    }
}
