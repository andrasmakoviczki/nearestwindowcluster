package hu.elte.databasesystems.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Andras Makoviczki on 2016. 11. 16.
 */

//Koordináta rendszer
public class Plot extends Pane {

    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Pane pane;
    @FXML
    private Canvas canvas;

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

    public NumberAxis getXAxis() {
        return xAxis;
    }

    public NumberAxis getYAxis() {
        return yAxis;
    }

    public Pane getPane() {
        return pane;
    }

    public Canvas getCanvas() {
        return canvas;
    }

}
