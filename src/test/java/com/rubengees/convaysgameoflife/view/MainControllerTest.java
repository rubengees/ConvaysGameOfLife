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
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link MainController}.
 *
 * @author Ruben Gees
 */
public class MainControllerTest extends ApplicationTest {

    private Pane tileContainer;
    private Button runButton;
    private Slider sizeXSlider;
    private Slider sizeYSlider;

    @Override
    public void start(Stage stage) throws Exception {
        //noinspection ConstantConditions
        Scene scene = new Scene(FXMLLoader.load(Utils.getResource(getClass(), "main.fxml")), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Before
    public void setUp() throws Exception {
        tileContainer = lookup("#tileContainer").queryFirst();
        runButton = lookup("#runButton").queryFirst();
        sizeXSlider = lookup("#sizeXSlider").queryFirst();
        sizeYSlider = lookup("#sizeYSlider").queryFirst();
    }

    @Test
    public void testStartToStop() throws Exception {
        clickOn(runButton);

        assertEquals("Stopp", runButton.getText());
    }

    @Test
    public void testStopToStart() throws Exception {
        clickOn(runButton);
        clickOn(runButton);

        assertEquals("Start", runButton.getText());
    }

    @Test
    public void testRectangleChangeColorOnClick() throws Exception {
        Rectangle first = (Rectangle) tileContainer.getChildren().get(0);

        clickOn(first);

        assertEquals(Paint.valueOf("white"), first.getFill());
    }

    @Test
    public void testBoardResizeX() throws Exception {
        Platform.runLater(() -> {
            sizeXSlider.setValue(20);

            assertEquals(3 * 20, tileContainer.getChildren().size());
        });
    }

    @Test
    public void testBoardResizeY() throws Exception {
        Platform.runLater(() -> {
            sizeYSlider.setValue(20);

            assertEquals(3 * 20, tileContainer.getChildren().size());
        });
    }

    @Test
    public void testBoardResizeXY() throws Exception {
        Platform.runLater(() -> {
            sizeXSlider.setValue(20);
            sizeYSlider.setValue(20);

            assertEquals(20 * 20, tileContainer.getChildren().size());
        });
    }

}
