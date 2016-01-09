package com.rubengees.convaysgameoflife.view;

import com.google.gson.JsonSyntaxException;
import com.rubengees.convaysgameoflife.logic.Board;
import com.rubengees.convaysgameoflife.logic.JsonUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
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
    private static final String BLINKER = "blinker.json";
    private static final String PULSAR = "pulsar.json";
    private static final String OCTAGON = "octagon.json";
    private static final String GLIDER = "glider.json";
    private static final String FILE_CHOOSER_TITLE = "Speicherort wählen";
    private static final String FILE_CHOOSER_DEFAULT_FILE_NAME = "export.json";
    private static final String FILE_CHOOSER_FILTER_TITLE = "Json Dateien";
    private static final String FILE_CHOOSER_FILTER = "*.json";
    private static final String ERROR_IO = "Konnte nicht auf Datei zugreifen.";
    private static final String ERROR_JSON = "Die Datei ist beschädigt.";

    @FXML
    Parent root;

    @FXML
    Pane tileContainer;

    @FXML
    Slider sizeXSlider;
    @FXML
    Slider sizeYSlider;
    @FXML
    Slider speedSlider;

    @FXML
    Button runButton;

    private Board board;

    private CycleThread cycleThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board(generateEmptyMatrix());

        sizeXSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            board.setAliveMatrix(generateEmptyMatrix());

            draw();
        });

        sizeYSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            board.setAliveMatrix(generateEmptyMatrix());

            draw();
        });

        draw();
    }

    /**
     * Randomizes the alive state of the cells.
     */
    public void randomize() {
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
     */
    public void run() {
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
     * Calculates and draws a single step.
     */
    public void doStep() {
        board.calculateCycle();

        draw();
    }

    /**
     * Resets the states of all cells to dead.
     */
    public void reset() {
        board.setAliveMatrix(generateEmptyMatrix());

        draw();
    }

    /**
     * Closes the application.
     */
    public void close() {
        System.exit(0);
    }

    /**
     * Draws the current state of the board. Initializes the tiles and adds handler for mouse actions to them.
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

    public void doImport() {
        File file = generateFileChooser().showOpenDialog(root.getScene().getWindow());

        if (file != null) {
            try {
                drawFromImport(JsonUtils.deserialize(file));
            } catch (IOException e) {
                showIOError();
            } catch (JsonSyntaxException e) {
                showJsonError();
            }
        }
    }

    public void doExport() {
        File file = generateFileChooser().showSaveDialog(root.getScene().getWindow());

        if (file != null) {
            try {
                JsonUtils.serialize(board.toAliveMatrix(), file);
            } catch (IOException e) {
                showIOError();
            }
        }
    }

    public void doImportBlinker() {
        doImportFromResource(BLINKER);
    }

    public void doImportPulsar() {
        doImportFromResource(PULSAR);
    }

    public void doImportOctagon() {
        doImportFromResource(OCTAGON);
    }

    public void doImportGlider() {
        doImportFromResource(GLIDER);
    }

    private void doImportFromResource(@NotNull String resource) {

        try {
            drawFromImport(JsonUtils.deserialize(getClass(), resource));
        } catch (IOException e) {
            showIOError();
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
     * Returns the current columns as set by the user through the sizeYSlider.
     *
     * @return The columns.
     */
    private int getColumns() {
        return (int) sizeYSlider.valueProperty().get();
    }

    /**
     * Returns the current rows as set by the user through the sizeYSlider.
     *
     * @return The rows.
     */
    private int getRows() {
        return (int) sizeXSlider.valueProperty().get();
    }

    /**
     * Returns the current refresh interval for the automatic drawing as set by the user through the speedSlider.
     *
     * @return The interval.
     */
    private long getInterval() {
        return (long) speedSlider.getValue();
    }

    private void drawFromImport(@NotNull boolean[][] aliveMatrix) {
        sizeXSlider.setValue(aliveMatrix.length);
        sizeYSlider.setValue(aliveMatrix[0].length);

        board.setAliveMatrix(aliveMatrix);

        draw();
    }

    private FileChooser generateFileChooser() {
        FileChooser result = new FileChooser();

        result.setTitle(FILE_CHOOSER_TITLE);
        result.setInitialFileName(FILE_CHOOSER_DEFAULT_FILE_NAME);
        result.getExtensionFilters().add(new FileChooser.ExtensionFilter(FILE_CHOOSER_FILTER_TITLE, FILE_CHOOSER_FILTER));

        return result;
    }

    private void showIOError() {
        new Alert(Alert.AlertType.ERROR, ERROR_IO, ButtonType.CLOSE).showAndWait();
    }

    private void showJsonError() {
        new Alert(Alert.AlertType.ERROR, ERROR_JSON, ButtonType.CLOSE).showAndWait();
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
         * Signalizes this Thread to cancel. It is possible that one cycle is calculated and drawn before
         * it is canceled.
         */
        public void cancel() {
            cancelled = true;
        }
    }
}
