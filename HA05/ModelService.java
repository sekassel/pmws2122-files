package de.uniks.pmws2122.model;

import static de.uniks.pmws2122.Constants.*;

public class ModelService {
	private Game game;

	public void buildGame(String playerNameOne, String playerNameTwo) {
		this.game = new Game().setName("Epic battle");

		// create player one
		Player playerOne = new Player()
				.setName(playerNameOne)
				.setColor(COLOR_BLACK)
				.setInitialPlacedMen(0)
				.setAction("")
				.setGame(this.game);

		// create player two
		new Player()
				.setName(playerNameTwo)
				.setColor(COLOR_WHITE)
				.setInitialPlacedMen(0)
				.setAction("")
				.setPrevious(playerOne)
				.setNext(playerOne)
				.setGame(this.game);

		// create the fields
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

	public Game getGame() {
		return game;
	}
}
