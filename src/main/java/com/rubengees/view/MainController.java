package com.rubengees.view;

import com.rubengees.logic.Board;
import com.rubengees.logic.Cell;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainController implements Initializable {

    private static final Paint BLACK = Paint.valueOf("black");
    private static final Paint WHITE = Paint.valueOf("white");

    @FXML
    TilePane tileContainer;
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
        Random random = new Random();
        int rows = getRows();
        int columns = getColumns();
        boolean[][] aliveMatrix = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                aliveMatrix[i][j] = random.nextInt(2) == 1;
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

    private long getInterval() {
        return (long) speedSlider.getValue();
    }

    public void doStep(ActionEvent actionEvent) {
        board.calculateCycle();

        draw();
    }

    private void draw() {
        Cell[][] cells = board.getCells();
        int rows = getRows();
        int columns = getColumns();

        tileContainer.getChildren().clear();
        tileContainer.setPrefColumns(getColumns());
        tileContainer.setPrefRows(getRows());

        List<Rectangle> rectangles = new ArrayList<>(rows * columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                final int x = i;
                final int y = j;

                Rectangle rectangle = new Rectangle(400 / rows, 400 / columns,
                        cells[x][y].isAlive() ? WHITE : BLACK);

                rectangle.setArcHeight(15);
                rectangle.setArcWidth(15);

                rectangle.setOnMouseClicked(event -> {
                    board.invertCell(x, y);

                    rectangle.setFill(board.getCell(x, y).isAlive() ? WHITE : BLACK);
                });

                rectangles.add(rectangle);
            }
        }

        tileContainer.getChildren().addAll(rectangles);
    }

    private boolean[][] generateEmptyMatrix() {
        int rows = getRows();
        int columns = getColumns();
        boolean[][] result = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = false;
            }
        }

        return result;
    }

    private int getColumns() {
        return (int) sliderSizeY.valueProperty().get();
    }

    private int getRows() {
        return (int) sliderSizeX.valueProperty().get();
    }

    private class CycleThread extends Thread {
        private volatile boolean cancelled = false;

        @Override
        public void run() {
            while (!cancelled) {
                board.calculateCycle();

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
