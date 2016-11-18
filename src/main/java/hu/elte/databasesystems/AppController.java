package hu.elte.databasesystems;

import hu.elte.databasesystems.model.DataObject;
import hu.elte.databasesystems.util.Plot;
import hu.elte.databasesystems.util.ReadFromFile;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Math.abs;

/**
 * Created by Andras Makoviczki on 2016. 11. 16..
 */
public class AppController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @FXML private MenuItem loadMenuItem;
    @FXML private MenuItem closeAppItem;
    @FXML private Stage stage;
    @FXML private Plot plot;
    @FXML private StatusBar statusBar;
    GraphicsContext gc;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initPlot(500,8);
    }

    public void initPlot(Integer defaultSize, Integer range) {
        Pane _pane = plot.getPane();
        NumberAxis _xAxis = plot.getxAxis();
        NumberAxis _yAxis = plot.getyAxis();

        Integer _defaultSize = defaultSize;
        Integer _positiveRange = range;
        Integer _negativeRange = -_positiveRange;

        _pane.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        _pane.setPrefSize(_defaultSize, _defaultSize);
        _pane.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        _xAxis.setLowerBound(_negativeRange);
        _xAxis.setUpperBound(_positiveRange);
        _xAxis.setTickUnit(range);
        _xAxis.setMinorTickVisible(false);
        _xAxis.setPrefWidth(_defaultSize);
        _xAxis.setLayoutY(_defaultSize / 2);

        _yAxis.setLowerBound(_negativeRange);
        _yAxis.setUpperBound(_positiveRange);
        _yAxis.setTickUnit(range);
        _yAxis.setMinorTickVisible(false);
        _yAxis.setPrefHeight(_defaultSize);
        _yAxis.layoutXProperty().bind(
                Bindings.subtract(
                        (_defaultSize / 2) + 1,
                        _yAxis.widthProperty()
                )
        );

        Canvas _canvas = plot.getCanvas();
        _canvas.setWidth(_defaultSize+10);
        _canvas.setHeight(_defaultSize+10);

        gc = _canvas.getGraphicsContext2D();

        drawPoint(0,0,"q",Color.RED);
        _pane.getChildren().setAll(_xAxis, _yAxis,_canvas);
    }

    public void drawPoint(Integer x,Integer y){
        drawPoint(x,y,null,Color.BLUE);
    }

    public void drawPoint(Integer x,Integer y,String label){
        drawPoint(x,y,label,Color.BLUE);
    }

    public void drawPoint(Integer x,Integer y,Boolean labeledByPoints){
        String coordinates = "[X=" + x.toString() + " Y=" + y.toString() + "]";
        drawPoint(x,y,coordinates);
    }

    public void drawPoint(Integer x,Integer y,String label, Color color){
        Double unit = plot.getPane().getPrefWidth() / (2 * plot.getxAxis().getUpperBound());
        Double fromOriginX = plot.getPane().getPrefWidth() / 2 + x * unit;
        Double fromOriginY = plot.getPane().getPrefHeight() / 2 - y * unit;

        if(label != null){
            gc.setStroke(Color.BLACK);
            gc.setFill(Color.BLACK);
            gc.setLineWidth(0.5);
            gc.strokeText(label,fromOriginX + 5, fromOriginY - 5);
        }

        gc.setStroke(color);
        gc.setFill(color);
        gc.setLineWidth(1);
        gc.strokeOval(fromOriginX,fromOriginY,1,1);
    }

    public void createPlot(ArrayList<DataObject> loadedObjects,Integer range){
        gc.clearRect(0,0,plot.getPrefWidth(),plot.getPrefWidth());
        initPlot(500,range);
    }

    public void loadPoints(ArrayList<DataObject> loadedObjects){
        for (DataObject data: loadedObjects) {
            drawPoint(data.getX(),data.getY());
        }
    }

    public void handleLoadButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        FileChooser.ExtensionFilter txtExtension = new FileChooser.ExtensionFilter("Text", "*.txt");
        fileChooser.getExtensionFilters().add(txtExtension);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            ReadFromFile loadFile = new ReadFromFile(file);
            ArrayList<DataObject> objs = loadFile.ParseFile(":");
            createPlot(objs,loadFile.getAbsMaxValue());
            loadPoints(objs);
            statusBar.setText("Loaded: " + loadFile.getTotalLoaded().toString() + " objects");
        }
    }


    public void handleCloseButton(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void handleStartButton(ActionEvent actionEvent) {
        initPlot(500,20);
        /*for (int i = 0; i < 10; ++i) {
            Button button = new Button();
            buttonGrid.add(button, i, 1);
        }*/
    }
}
