package com.rubengees.view;

import com.rubengees.logic.Board;
import com.rubengees.logic.Cell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainController implements Initializable {

    private static final Paint BlACK = Paint.valueOf("black");
    private static final Paint WHITE = Paint.valueOf("white");

    @FXML
    TilePane tileContainer;
    @FXML
    Slider sliderSizeX;
    @FXML
    Slider sliderSizeY;
    @FXML
    Slider speedSlider;

    private Board board;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board(generateEmptyMatrix());

        sliderSizeX.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                                Number newValue) {
                board.setAliveMatrix(generateEmptyMatrix());

                draw();
            }
        });

        sliderSizeY.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                                Number newValue) {
                board.setAliveMatrix(generateEmptyMatrix());

                draw();
            }
        });

        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                                Number newValue) {

            }
        });

        tileContainer.setHgap(3);
        tileContainer.setVgap(3);

        draw();
    }

    public void randomize(ActionEvent actionEvent) {

    }

    public void run(ActionEvent actionEvent) {

    }

    public void doStep(ActionEvent actionEvent) {

    }

    private void draw() {
        Cell[][] cells = board.getCells();
        int rows = getRows();
        int columns = getColumns();

        tileContainer.getChildren().clear();
        tileContainer.setPrefColumns(getColumns());
        tileContainer.setPrefRows(getRows());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Rectangle rectangle = new Rectangle(400 / rows - 3 * (rows - 1), 400 / columns - 3 * (columns - 1),
                        cells[i][j].isAlive() ? WHITE : BlACK);

                tileContainer.getChildren().add(rectangle);
            }
        }
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
}
