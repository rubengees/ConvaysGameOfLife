package com.rubengees.view;

import com.rubengees.logic.Board;
import com.rubengees.logic.Cell;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.TilePane;
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

        tileContainer.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //draw();
            }
        });

        tileContainer.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //draw();
            }
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
        tileContainer.setPrefRows(rows);
        tileContainer.setPrefColumns(columns);

        //Start to iterate from the columns, as the Tilepain populate itself also from the columns first
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Rectangle rectangle = new Rectangle(400 / columns, 400 / rows,
                        cells[j][i].isAlive() ? WHITE : BLACK);
                final int finalI = i;
                final int finalJ = j;

                rectangle.setArcHeight(15);
                rectangle.setArcWidth(15);

                rectangle.setOnMouseClicked(event -> {
                    board.invertCell(finalJ, finalI);
                    rectangle.setFill(board.getCell(finalJ, finalI).isAlive() ? WHITE : BLACK);
                });

                rectangle.setOnMouseEntered(event -> rectangle.setFill(GREY));

                rectangle.setOnMouseExited(event ->
                        rectangle.setFill(board.getCell(finalJ, finalI).isAlive() ? WHITE : BLACK));

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
