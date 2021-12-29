package de.uniks.pmws2122.model;

import static de.uniks.pmws2122.Constants.*;

public class ModelService {
    private Game game;

    // This variables are needed for the moveMan() method
    private Man currentSelectedMan;
    private Field currentSelectedField;

    public Game getGame() {
        return this.game;
    }

    public void buildGame(String playerNameOne, String playerNameTwo) {
        this.game = new Game().setName("Epic battle");

        // Create player one
        Player playerOne = new Player()
                .setName(playerNameOne)
                .setColor(COLOR_BLACK)
                .setInitialPlacedMen(0)
                .setAction("")
                .setGame(this.game);

        // Create player two
        new Player()
                .setName(playerNameTwo)
                .setColor(COLOR_WHITE)
                .setInitialPlacedMen(0)
                .setAction("")
                .setPrevious(playerOne)
                .setNext(playerOne)
                .setGame(this.game);

        // Create the fields
        Field a7 = new Field().setCoordinate("a7").setGame(this.game);
        Field d7 = new Field().setCoordinate("d7").setGame(this.game);
        Field g7 = new Field().setCoordinate("g7").setGame(this.game);
        Field b6 = new Field().setCoordinate("b6").setGame(this.game);
        Field d6 = new Field().setCoordinate("d6").setGame(this.game);
        Field f6 = new Field().setCoordinate("f6").setGame(this.game);
        Field c5 = new Field().setCoordinate("c5").setGame(this.game);
        Field d5 = new Field().setCoordinate("d5").setGame(this.game);
        Field e5 = new Field().setCoordinate("e5").setGame(this.game);
        Field a4 = new Field().setCoordinate("a4").setGame(this.game);
        Field b4 = new Field().setCoordinate("b4").setGame(this.game);
        Field c4 = new Field().setCoordinate("c4").setGame(this.game);
        Field e4 = new Field().setCoordinate("e4").setGame(this.game);
        Field f4 = new Field().setCoordinate("f4").setGame(this.game);
        Field g4 = new Field().setCoordinate("g4").setGame(this.game);
        Field c3 = new Field().setCoordinate("c3").setGame(this.game);
        Field d3 = new Field().setCoordinate("d3").setGame(this.game);
        Field e3 = new Field().setCoordinate("e3").setGame(this.game);
        Field b2 = new Field().setCoordinate("b2").setGame(this.game);
        Field d2 = new Field().setCoordinate("d2").setGame(this.game);
        Field f2 = new Field().setCoordinate("f2").setGame(this.game);
        Field a1 = new Field().setCoordinate("a1").setGame(this.game);
        Field d1 = new Field().setCoordinate("d1").setGame(this.game);
        Field g1 = new Field().setCoordinate("g1").setGame(this.game);

        a7.setRight(d7).setBottom(a4);
        d7.setRight(g7).setBottom(d6);
        g7.setBottom(g4);
        b6.setRight(d6).setBottom(b4);
        d6.setRight(f6).setBottom(d5);
        f6.setBottom(f4);
        c5.setRight(d5).setBottom(c4);
        d5.setRight(e5);
        e5.setBottom(e4);
        a4.setRight(b4).setBottom(a1);
        b4.setRight(c4).setBottom(b2);
        c4.setBottom(c3);
        e4.setRight(f4).setBottom(e3);
        f4.setRight(g4).setBottom(f2);
        g4.setBottom(g1);
        c3.setRight(d3);
        d3.setRight(e3).setBottom(d2);
        b2.setRight(d2);
        d2.setRight(f2).setBottom(d1);
        a1.setRight(d1);
        d1.setRight(g1);
    }

    public void startGame(Player beginner) {
        this.game.setPhase(GAME_PHASE_PLACING).setCurrentPlayer(beginner);
        beginner.setAction(PLAYER_ACTION_PLACING);
        beginner.getNext().setAction(PLAYER_ACTION_PLACING);
    }

    public void checkWinner() {
        // The game can only be won in the game phase moving
        if (this.game.getPhase().equals(GAME_PHASE_MOVING)) {
            // Check each player
            for (Player p : this.game.getPlayers()) {
                // Check if men size is smaller than 3
                if (p.getMen().size() < 3) {
                    // Player was found, his opponent is the winner
                    this.game.setWinner(p.getNext());
                }

                // Also check if a player can not move anymore, if so, he lost // TODO:
                // HAUSAUFGABE
                if (p.getMen().stream().noneMatch(
                        (m) -> (m.getPosition().getLeft() != null && m.getPosition().getLeft().getMan() == null) ||
                                (m.getPosition().getRight() != null && m.getPosition().getRight().getMan() == null) ||
                                (m.getPosition().getTop() != null && m.getPosition().getTop().getMan() == null) ||
                                (m.getPosition().getBottom() != null
                                        && m.getPosition().getBottom().getMan() == null))) {
                    this.game.setWinner(p.getNext());
                }
            }
        }
    }

    public void nextTurn() {
        // Set the game to the next phase // TODO: HAUSAUFGABE
        this.game.setCurrentPlayer(this.game.getCurrentPlayer().getNext());

        this.checkNextPhase();
    }

    public void checkNextPhase() {
        // Also check for to change the phase // TODO: Hausaufgabe
        for (Player p : this.game.getPlayers()) {
            // Only change the phase after all men are placed
            if (p.getInitialPlacedMen() != 9) {
                return;
            }
        }

        if (this.game.getPhase().equals(GAME_PHASE_PLACING)) {
            // Change the phase from "placing" to "moving" and set the player action to
            // moving
            this.game.setPhase(GAME_PHASE_MOVING);
            this.game.getPlayers().forEach((p) -> p.setAction(PLAYER_ACTION_MOVING));
        }
    }

    public void placeMan(Field field) {
        // Check that this field is empty // TODO: HAUSAUFGABE
        if (field.getMan() != null) {
            return;
        }

        // Check that the game phase / player action is "placing"
        if (!(this.game.getPhase().equals(GAME_PHASE_PLACING) && this.game.getCurrentPlayer().getAction().equals(PLAYER_ACTION_PLACING))) {
            return;
        }

        // Place the man
        new Man()
                .setOwner(this.game.getCurrentPlayer())
                .setColor(this.game.getCurrentPlayer().getColor())
                .setGame(this.game)
                .setPosition(field);

        // Increase the initialPlacesMen variable from the current player
        this.game.getCurrentPlayer().setInitialPlacedMen(this.game.getCurrentPlayer().getInitialPlacedMen() + 1);
    }

    public void moveMan() {
        // Check if a man and field are selected TODO: HAUSAUFGABE
        if (this.currentSelectedMan == null || this.currentSelectedField == null) {
            return;
        }

        // Check that the game phase and player action is "moving"
        if (!(this.game.getPhase().equals(GAME_PHASE_MOVING) && this.game.getCurrentPlayer().getAction().equals(PLAYER_ACTION_MOVING))) {
            return;
        }

        // Move the man
        this.currentSelectedMan.setPosition(this.currentSelectedField);

        // Reset the current selected man and field
        this.currentSelectedMan = null;
        this.currentSelectedField = null;
    }

    public void removeMan(Man man) {
        // Check that the man is not empty TODO: HAUSAUFGABE
        if (man == null) {
            return;
        }

        // Check that the player action is "removing"
        if (!this.game.getCurrentPlayer().getAction().equals(PLAYER_ACTION_REMOVE)) {
            return;
        }

        // Get the old owner of the man
        Player oldOwner = man.getOwner();

        // Remove the man
        man.removeYou();

        // Set the player action of the old owner to "flying" if the current game phase is "moving" and the player´s man are 3
        if (this.game.getPhase().equals(GAME_PHASE_MOVING) && oldOwner.getMen().size() == 3) {
            oldOwner.setAction(PLAYER_ACTION_FLYING);
        }

        // Set the player action of the current player depending on the game phase and the number of his men
        if (this.game.getPhase().equals(GAME_PHASE_PLACING)) {
            this.game.getCurrentPlayer().setAction(PLAYER_ACTION_PLACING);
        } else if (this.game.getPhase().equals(GAME_PHASE_MOVING)) {
            if (game.getCurrentPlayer().getMen().size() > 3) {
                this.game.getCurrentPlayer().setAction(PLAYER_ACTION_MOVING);
            } else {
                this.game.getCurrentPlayer().setAction(PLAYER_ACTION_FLYING);
            }
        }
    }
    
    public void checkMill(Man lastPutMan) {
        this.checkVerticalMill(lastPutMan);
        this.checkHorizontalMill(lastPutMan);
    }

    public void checkVerticalMill(Man lastPutMan) {
        // Also check for vertical mill // TODO: HAUSAUFGABE
        Field currentField = lastPutMan.getPosition();
        String currentColor = lastPutMan.getColor();

        Field neighbor1 = null;
        Field neighbor2 = null;

        // Check if field of man has top and bottom neighbor
        if (currentField.getTop() != null && currentField.getBottom() != null) {
            neighbor1 = currentField.getTop();
            neighbor2 = currentField.getBottom();
        }

        // Check if field of man has top neighbor with top neighbor
        if (currentField.getTop() != null && currentField.getTop().getTop() != null) {
            neighbor1 = currentField.getTop();
            neighbor2 = neighbor1.getTop();
        }

        // Check if field of man has bottom neighbor with bottom neighbor
        if (currentField.getBottom() != null && currentField.getBottom().getBottom() != null) {
            neighbor1 = currentField.getBottom();
            neighbor2 = neighbor1.getBottom();
        }

        if (neighbor1 == null || neighbor2 == null) {
            return;
        }

        // Check neighbors for men and colors
        if (neighbor1.getMan() != null
                && neighbor1.getMan().getColor().equals(currentColor)
                && neighbor2.getMan() != null
                && neighbor2.getMan().getColor().equals(currentColor)) {
            // Mill found, change man owner action to "remove"
            lastPutMan.getOwner().setAction(PLAYER_ACTION_REMOVE);
        }
    }

    public void checkHorizontalMill(Man lastPutMan) {
        Field currentField = lastPutMan.getPosition();
        String currentColor = lastPutMan.getColor();

        Field neighbor1 = null;
        Field neighbor2 = null;

        // Check if field of man has left and right neighbor
        if (currentField.getLeft() != null && currentField.getRight() != null) {
            neighbor1 = currentField.getLeft();
            neighbor2 = currentField.getRight();
        }

        // Check if field of man has left neighbor with left neighbor
        if (currentField.getLeft() != null && currentField.getLeft().getLeft() != null) {
            neighbor1 = currentField.getLeft();
            neighbor2 = neighbor1.getLeft();
        }

        // Check if field of man has right neighbor with right neighbor
        if (currentField.getRight() != null && currentField.getRight().getRight() != null) {
            neighbor1 = currentField.getRight();
            neighbor2 = neighbor1.getRight();
        }

        if (neighbor1 == null || neighbor2 == null) {
            return;
        }

        // Check neighbors for men and colors
        if (neighbor1.getMan() != null
                && neighbor1.getMan().getColor().equals(currentColor)
                && neighbor2.getMan() != null
                && neighbor2.getMan().getColor().equals(currentColor)) {
            // Mill found, change man owner action to "remove"
            lastPutMan.getOwner().setAction(PLAYER_ACTION_REMOVE);
        }
    }
}
