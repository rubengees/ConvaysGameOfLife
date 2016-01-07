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
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainController implements Initializable {

    private static final Paint BLACK = Paint.valueOf("black");
    private static final Paint WHITE = Paint.valueOf("white");
    private static final Paint GREY = Paint.valueOf("grey");

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

    public void run(ActionEvent actionEvent) {
        if (cycleThread == null) {
            cycleThread = new CycleThread();
            cycleThread.start();

            runButton.setText("Stop");
        } else {
            cycleThread.cancel();
            cycleThread = null;

            runButton.setText("Run");
        }
    }

    public void doStep(ActionEvent actionEvent) {
        board.calculateCycle();

        draw();
    }

    public void reset(ActionEvent actionEvent) {
        board.setAliveMatrix(generateEmptyMatrix());

        draw();
    }

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void draw() {
        int rows = getRows();
        int columns = getColumns();

        tileContainer.getChildren().clear();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                Rectangle rectangle = new Rectangle();
                final int finalI = i;
                final int finalJ = j;

                rectangle.setArcHeight(15);
                rectangle.setArcWidth(15);

                rectangle.setFill(board.getCell(finalI, finalJ).isAlive() ? WHITE : BLACK);

                rectangle.setOnMouseClicked(event -> {
                    board.invertCell(finalI, finalJ);
                    rectangle.setFill(board.getCell(finalI, finalJ).isAlive() ? WHITE : BLACK);
                });

                rectangle.setOnMouseEntered(event -> rectangle.setFill(GREY));

                rectangle.setOnMouseExited(event ->
                        rectangle.setFill(board.getCell(finalI, finalJ).isAlive() ? WHITE : BLACK));

                rectangle.xProperty().bind(tileContainer.widthProperty().divide(rows).multiply(i));
                rectangle.yProperty().bind(tileContainer.heightProperty().divide(columns).multiply(j));
                rectangle.heightProperty().bind(tileContainer.heightProperty().divide(columns));
                rectangle.widthProperty().bind(tileContainer.widthProperty().divide(rows));
                tileContainer.getChildren().add(rectangle);
            }
        }
    }

    private boolean[][] generateEmptyMatrix() {
        return new boolean[getRows()][getColumns()];
    }

    private int getColumns() {
        return (int) sliderSizeY.valueProperty().get();
    }

    private int getRows() {
        return (int) sliderSizeX.valueProperty().get();
    }

    private long getInterval() {
        return (long) speedSlider.getValue();
    }

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

        public void cancel() {
            cancelled = true;
        }

        public boolean isCancelled() {
            return cancelled;
        }
    }
}
