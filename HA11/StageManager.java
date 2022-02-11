package de.uniks.pmws2122.icp;

import de.uniks.pmws2122.icp.controller.ChatScreenController;
import de.uniks.pmws2122.icp.controller.LoginScreenController;
import de.uniks.pmws2122.icp.model.ModelService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kong.unirest.Unirest;

import java.util.Objects;

import static de.uniks.pmws2122.icp.Constants.CHAT_SCREEN_TITLE;
import static de.uniks.pmws2122.icp.Constants.LOGIN_SCREEN_TITLE;

public class StageManager extends Application {
    private static Stage primaryStage;

    private static ModelService modelService;

    private static LoginScreenController loginCon;
    private static ChatScreenController chatCon;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        showLoginScreen();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        cleanup();

        // Perform a logout if there is a current chat session with an current nickname
        if (modelService.getCurrentChatSession() != null) {
            String currentNickname = modelService.getCurrentChatSession().getCurrentNickname();
            if (currentNickname != null) {
                RestService.logout(modelService.getCurrentChatSession().getCurrentNickname());
            }
        }

        // Shutdown the unirest client, close all open requests
        Unirest.shutDown();
    }

    public static void showLoginScreen() {
        cleanup();
        try {
            // Load view
            Parent root = FXMLLoader.load(Objects.requireNonNull(StageManager.class.getResource("view/LoginScreen.fxml")));
            Scene scene = new Scene(root);

            // Create model service
            modelService = new ModelService();

            // Init controller
            loginCon = new LoginScreenController(modelService, root);
            loginCon.init();

            // Display
            primaryStage.setTitle(LOGIN_SCREEN_TITLE);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Error while loading login screen");
            e.printStackTrace();
        }
    }

    public static void showChatScreen() {
        cleanup();
        try {
            // Load view
            Parent root = FXMLLoader.load(Objects.requireNonNull(StageManager.class.getResource("view/ChatScreen.fxml")));
            Scene scene = new Scene(root);

            // Init controller
            chatCon = new ChatScreenController(modelService, root);
            chatCon.init();

            // Display
            primaryStage.setTitle(CHAT_SCREEN_TITLE);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Error while loading chat screen");
            e.printStackTrace();
        }
    }

    private static void cleanup() {
        if (loginCon != null) {
            loginCon.stop();
            loginCon = null;
        }
        if (chatCon != null) {
            chatCon.stop();
            chatCon = null;
        }
    }
}
