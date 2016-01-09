package com.rubengees.convaysgameoflife.view;

import com.rubengees.convaysgameoflife.logic.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * The entry point for the application.
 *
 * @author Ruben Gees
 */
public class Main extends Application {

    public static final String ERROR_RESOURCE_NOT_FOUND = "A needed resource was not found.";
    private static final String LAYOUT = "main.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL resource = Utils.getResource(getClass(), LAYOUT);

        if (resource != null) {
            Parent root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);
            URL icon = Utils.getResource(getClass(), "icon.png");

            primaryStage.setScene(scene);

            //Prevents weird effects when making the window to small.
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(600);

            primaryStage.setTitle("Conway's Game of Life");

            if (icon != null) {
                primaryStage.getIcons().add(new Image(icon.openStream()));
            }

            primaryStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, ERROR_RESOURCE_NOT_FOUND, ButtonType.CLOSE);

            alert.showAndWait();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        //Cancels eventually running Threads.
        System.exit(0);
    }
}
