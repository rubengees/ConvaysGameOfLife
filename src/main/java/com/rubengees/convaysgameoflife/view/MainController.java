package com.rubengees.convaysgameoflife.view;

import com.rubengees.convaysgameoflife.logic.Board;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.SplittableRandom;

/**
 * Controller, responsible for presenting the content and reacting to user input.
 *
 * @author Ruben Gees
 */
public class MainController implements Initializable {

    private static final Paint BLACK = Paint.valueOf("black");
    private static final Paint WHITE = Paint.valueOf("white");
    private static final Paint GREY = Paint.valueOf("grey");
    private static final String BUTTON_STOP = "Stopp";
    private static final String BUTTON_RUN = "Start";

    @FXML
    Pane tileContainer;
    @FXML
    Slider sliderSizeX;
    @FXML
    Slider sliderSizeY;
    @FXML
    Slider speedSlider;
    @FXML
    Button runButton;

    private Board board;

    private CycleThread cycleThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board(generateEmptyMatrix());

        sliderSizeX.valueProperty().addListener((observable, oldValue, newValue) -> {
            board.setAliveMatrix(generateEmptyMatrix());

            draw();
        });

        sliderSizeY.valueProperty().addListener((observable, oldValue, newValue) -> {
            board.setAliveMatrix(generateEmptyMatrix());

            draw();
        });

        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

        });

        draw();
    }

    /**
     * Randomizes the alive state of the cells.
     *
     * @param actionEvent The event.
     */
    public void randomize(ActionEvent actionEvent) {
        SplittableRandom random = new SplittableRandom();
        int rows = getRows();
        int columns = getColumns();
        boolean[][] aliveMatrix = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                aliveMatrix[i][j] = random.nextInt() >= 0;
            }
        }

        board.setAliveMatrix(aliveMatrix);

        draw();
    }

    /**
     * Starts or stops the automatic cycle calculation and drawing, according if it is currently running or not.
     * The refresh rate is recalculated for each step from the speedSlider.
     *
     * @param actionEvent The event.
     */
    public void run(ActionEvent actionEvent) {
        if (cycleThread == null) {
            cycleThread = new CycleThread();
            cycleThread.start();

            runButton.setText(BUTTON_STOP);
        } else {
            cycleThread.cancel();
            cycleThread = null;

            runButton.setText(BUTTON_RUN);
        }
    }

    /**
     * Calculates and drawes a single step.
     *
     * @param actionEvent The event.
     */
    public void doStep(ActionEvent actionEvent) {
        board.calculateCycle();

        draw();
    }

    /**
     * Resets the states of all cells to dead.
     *
     * @param actionEvent The event.
     */
    public void reset(ActionEvent actionEvent) {
        board.setAliveMatrix(generateEmptyMatrix());

        draw();
    }

    /**
     * Closes the application.
     *
     * @param actionEvent The event.
     */
    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Drawes the current state of the board. Initializes the tiles and adds handler for mouse actions to them.
     */
    private void draw() {
        int rows = getRows();
        int columns = getColumns();

        tileContainer.getChildren().clear();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                Rectangle rectangle = new Rectangle();
                final int finalI = i;
                final int finalJ = j;

                rectangle.setFill(board.getCell(finalI, finalJ).isAlive() ? WHITE : BLACK);

                rectangle.setOnMouseClicked(event -> {
                    board.invertCell(finalI, finalJ);
                    rectangle.setFill(board.getCell(finalI, finalJ).isAlive() ? WHITE : BLACK);
                });

                rectangle.setOnMouseEntered(event -> rectangle.setFill(GREY));

                rectangle.setOnMouseExited(event ->
                        rectangle.setFill(board.getCell(finalI, finalJ).isAlive() ? WHITE : BLACK));

                //Position management
                rectangle.xProperty().bind(tileContainer.widthProperty().divide(rows).multiply(i));
                rectangle.yProperty().bind(tileContainer.heightProperty().divide(columns).multiply(j));

                //Size management
                rectangle.heightProperty().bind(tileContainer.heightProperty().divide(columns).subtract(3));
                rectangle.widthProperty().bind(tileContainer.widthProperty().divide(rows).subtract(3));

                tileContainer.getChildren().add(rectangle);
            }
        }
    }

    /**
     * Generates an empty matrix with the current size.
     *
     * @return THe matrix.
     */
    private boolean[][] generateEmptyMatrix() {
        return new boolean[getRows()][getColumns()];
    }

    /**
     * Returns the current columns as set by the user thorugh the sliderSizeY.
     *
     * @return The columns.
     */
    private int getColumns() {
        return (int) sliderSizeY.valueProperty().get();
    }

    /**
     * Returns the current rows as set by the user through the sliderSizeY.
     *
     * @return The rows.
     */
    private int getRows() {
        return (int) sliderSizeX.valueProperty().get();
    }

    /**
     * Returns the current refresh interval for the automatic drawing as set by the user through the speedSlider.
     *
     * @return The interval.
     */
    private long getInterval() {
        return (long) speedSlider.getValue();
    }

    /**
     * A Thread responsible for the refreshing and drawing of the board. This is potentially running for ever
     * unless the <code>cancel()</code> method is called.
     */
    private class CycleThread extends Thread {
        private volatile boolean cancelled = false;

        @Override
        public void run() {
            while (!cancelled) {
                board.calculateCycle();

                //Run on the JavaFx thread
                Platform.runLater(MainController.this::draw);

                try {
                    Thread.sleep(getInterval());
                } catch (InterruptedException ignored) {

                }
            }
        }

        /**
         * Signalizes this Thread to cancel. It is possible that one cycle is calculated and drawen before
         * it is canceled.
         */
        public void cancel() {
            cancelled = true;
        }
    }
}
