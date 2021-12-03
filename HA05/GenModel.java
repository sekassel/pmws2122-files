package de.uniks.pmws2122.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.builder.reflect.Link;

import java.util.List;

public class GenModel implements ClassModelDecorator {

    class Game {
        String name;
        String phase;

        @Link("game")
        List<Player> players;
        @Link("game")
        List<Man> men;
        @Link("game")
        List<Field> fields;
        @Link("currentGame")
        Player currentPlayer;
        @Link("wonGame")
        Player winner;
    }

    class Player {
        String name;
        String color;
        String action;
        int initialPlacedMen;

        @Link("owner")
        List<Man> men;
        @Link("players")
        Game game;
        @Link("currentPlayer")
        Game currentGame;
        @Link("winner")
        Game wonGame;
        @Link("previous")
        Player next;
        @Link("next")
        Player previous;
    }

    class Man {
        String color;

        @Link("men")
        Game game;
        @Link("men")
        Player owner;
        @Link("man")
        Field position;
    }

    class Field {
        String coordinate;

        @Link("fields")
        Game game;
        @Link("position")
        Man man;
        @Link("left")
        Field right;
        @Link("right")
        Field left;
        @Link("bottom")
        Field top;
        @Link("top")
        Field bottom;
    }

    @Override
    public void decorate(ClassModelManager mm) {
        mm.haveNestedClasses(GenModel.class);
    }
}
