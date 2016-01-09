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
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainControllerTest extends ApplicationTest {

    private Pane tileContainer;
    private Button randomButton;
    private Button runButton;
    private Button stepButton;
    private Slider sizeXSlider;
    private Slider sizeYSlider;
    private Slider speedSlider;

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
        randomButton = lookup("#randomButton").queryFirst();
        runButton = lookup("#runButton").queryFirst();
        stepButton = lookup("#stepButton").queryFirst();
        sizeXSlider = lookup("#sizeXSlider").queryFirst();
        sizeYSlider = lookup("#sizeYSlider").queryFirst();
        speedSlider = lookup("#speedSlider").queryFirst();
    }

    @Test
    public void testInitState() throws Exception {
        assertEquals(3 * 3, tileContainer.getChildren().size());
        assertEquals("Zufällig", randomButton.getText());
        assertEquals("Start", runButton.getText());
        assertEquals("Schritt", stepButton.getText());
        assertEquals(3, sizeXSlider.getValue(), 0);
        assertEquals(3, sizeYSlider.getValue(), 0);
        assertEquals(800, speedSlider.getValue(), 0);
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
