package com.rubengees.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO: Describe Class
 *
 * @author Ruben Gees
 */
public class MainController implements Initializable {

    @FXML
    TilePane tileContainer;
    @FXML
    Slider sliderSizeX;
    @FXML
    Slider sliderSizeY;
    @FXML
    Slider speedSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void randomize(ActionEvent actionEvent) {

    }

    public void onSpeedChanged(Event event) {

    }

    public void run(ActionEvent actionEvent) {

    }

    public void doStep(ActionEvent actionEvent) {

    }

    public void onSizeXChanged(Event event) {

    }

    public void onSizeYChanged(Event event) {

    }
}
