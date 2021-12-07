package de.uniks.pmws2122;

import de.uniks.pmws2122.controller.IngameScreenController;
import de.uniks.pmws2122.controller.SetupScreenController;
import de.uniks.pmws2122.model.ModelService;
import javafx.application.Application;
import javafx.stage.Stage;

public class StageManager extends Application {
    private static Stage primaryStage;

    private static ModelService modelService;

    private static SetupScreenController setupScreenController;
    private static IngameScreenController ingameScreenController;

    @Override
    public void start(Stage stage) {
        // start application
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        cleanup();
    }

    public static void showSetupScreen() {
        cleanup();

        // load setup view

        // create model builder

        // init controller

        // display scene on primary stage

    }

    public static void showIngameScreen() {
        cleanup();

        // load ingame view

        // init controller

        // display scene on primary stage

    }

    private static void cleanup() {
        if (setupScreenController != null) {
            setupScreenController.stop();
            setupScreenController = null;
        }
        if (ingameScreenController != null) {
            ingameScreenController.stop();
            ingameScreenController = null;
        }
    }
}
