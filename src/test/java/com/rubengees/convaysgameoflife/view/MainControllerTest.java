package com.rubengees.convaysgameoflife.view;

import com.rubengees.convaysgameoflife.logic.Utils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        //noinspection ConstantConditions
        Scene scene = new Scene(FXMLLoader.load(Utils.getResource(getClass(), "main.fxml")), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testInitState() throws Exception {

    }

    @Test
    public void testStartToStop() throws Exception {
        clickOn("#runButton");

        Assert.assertEquals("Stopp", ((Button) lookup("#runButton").queryFirst()).getText());
    }

    @Test
    public void testStopToStart() throws Exception {
        clickOn("#runButton");
        clickOn("#runButton");

        Assert.assertEquals("Start", ((Button) lookup("#runButton").queryFirst()).getText());
    }

    @Test
    public void testRectangleChangeColorOnClick() throws Exception {
        Rectangle first = (Rectangle) ((Pane) lookup("#tileContainer").queryFirst()).getChildren().get(0);

        clickOn(first);

        Assert.assertEquals(Paint.valueOf("white"), first.getFill());
    }

    @Test
    public void testBoardResizeX() throws Exception {
        Slider slider = lookup("#sizeXSlider").queryFirst();

        Platform.runLater(() -> {
            slider.setValue(20);

            Assert.assertEquals(3 * 20, ((Pane) lookup("#tileContainer").queryFirst()).getChildren().size());
        });
    }

    @Test
    public void testBoardResizeY() throws Exception {
        Slider slider = lookup("#sizeYSlider").queryFirst();

        Platform.runLater(() -> {
            slider.setValue(20);

            Assert.assertEquals(3 * 20, ((Pane) lookup("#tileContainer").queryFirst()).getChildren().size());
        });
    }

    @Test
    public void testBoardResizeXY() throws Exception {
        Slider sliderX = lookup("#sizeXSlider").queryFirst();
        Slider sliderY = lookup("#sizeYSlider").queryFirst();

        Platform.runLater(() -> {
            sliderX.setValue(20);
            sliderY.setValue(20);

            Assert.assertEquals(20 * 20, ((Pane) lookup("#tileContainer").queryFirst()).getChildren().size());
        });
    }

}
