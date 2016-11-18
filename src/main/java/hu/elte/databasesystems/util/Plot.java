package hu.elte.databasesystems.util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Andras Makoviczki on 2016. 11. 16..
 */
public class Plot extends Pane {

    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Pane pane;
    @FXML private Canvas canvas;

    public Plot() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/plot.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public NumberAxis getxAxis() {
        return xAxis;
    }

    public void setxAxis(NumberAxis xAxis) {
        this.xAxis = xAxis;
    }

    public NumberAxis getyAxis() {
        return yAxis;
    }

    public void setyAxis(NumberAxis yAxis) {
        this.yAxis = yAxis;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
