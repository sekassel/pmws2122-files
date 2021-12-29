package de.uniks.pmws2122;

import de.uniks.pmws2122.controller.IngameScreenController;
import de.uniks.pmws2122.controller.SetupScreenController;
import de.uniks.pmws2122.model.ModelService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager extends Application {
    private static Stage primaryStage;

    private static ModelService modelService;

    private static SetupScreenController setupScreenController;
    private static IngameScreenController ingameScreenController;

    @Override
    public void start(Stage stage) {
        // start application
        primaryStage = stage;
        showSetupScreen();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        cleanup();
    }

    public static void showSetupScreen() {
        cleanup();

        try {

            // load view
            Parent root = FXMLLoader.load(StageManager.class.getResource("SetupScreen.fxml"));
            Scene scene = new Scene(root);

            // create model builder
            modelService = new ModelService();

            // init controller
            setupScreenController = new SetupScreenController(modelService, root);
            setupScreenController.init();

            // display
            primaryStage.setTitle(Constants.SETUP_SCREEN_TITLE);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Error while loading start screen");
            e.printStackTrace();
        }
    }

    public static void showIngameScreen() {
        cleanup();

        try {
            // load view
            Parent root = FXMLLoader.load(StageManager.class.getResource("IngameScreen.fxml"));
            Scene scene = new Scene(root);

            // init controller
            ingameScreenController = new IngameScreenController(modelService, root);
            ingameScreenController.init();

            // display
            primaryStage.setTitle(Constants.INGAME_SCREEN_TITLE);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Error while loading game board screen");
            e.printStackTrace();
        }
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
