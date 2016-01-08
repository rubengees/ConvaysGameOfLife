package com.rubengees.convaysgameoflife.view;

import com.rubengees.convaysgameoflife.logic.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * The entry point for the application.
 *
 * @author Ruben Gees
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL resource = Utils.getResource(getClass(), "main.fxml");

        if (resource != null) {
            Parent root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);

            //Prevents weird effects when making the window to small.
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(600);

            primaryStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "A needed resource was not found.", ButtonType.CLOSE);

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
