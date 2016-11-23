package hu.elte.databasesystems;

import hu.elte.databasesystems.model.DataObject;
import hu.elte.databasesystems.model.NWC;
import hu.elte.databasesystems.model.QualifiedWindow;
import hu.elte.databasesystems.model.RTree;
import hu.elte.databasesystems.view.Plot;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Andras Makoviczki on 2016. 11. 16.
 */
public class AppController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    private QualifiedWindow qwin;
    private RTree tree;


    @FXML private TextField numObjText;
    @FXML private TextField lengthText;
    @FXML private TextField widthText;

    @FXML private MenuItem loadMenuItem;
    @FXML private MenuItem closeAppItem;
    @FXML private Stage stage;
    @FXML private Plot plot;
    @FXML private StatusBar statusBar;
    @FXML private Button startButton;

    private GraphicsContext gc;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initPlot(500,8);
    }

    private void initPlot(Integer defaultSize, Integer range) {
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
        _canvas.setWidth(_defaultSize+50);
        _canvas.setHeight(_defaultSize+50);

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
            gc.setLineWidth(0.25);
            gc.strokeText(label,fromOriginX + 5, fromOriginY - 5);
        }

        gc.setStroke(color);
        gc.setFill(color);
        gc.setLineWidth(2);
        gc.strokeOval(fromOriginX,fromOriginY,1,1);
    }

    public void createPlot(RTree<Integer,DataObject> loadedObjects,Integer range){
        gc.clearRect(0,0,plot.getPrefWidth(),plot.getPrefWidth());
        initPlot(500,range);
    }

    public void loadPoints(RTree<Integer,DataObject> loadedObjects){
        for (DataObject data: loadedObjects) {
            drawPoint(data.getX(),data.getY());
        }
    }

    public void loadPoints(RTree<Integer,DataObject> loadedObjects,Boolean label){
        for (DataObject data: loadedObjects) {
            drawPoint(data.getX(),data.getY(),label);
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
            this.tree = loadFile.ParseFile(":");
            tree.setParents();
            createPlot(tree,loadFile.getAbsMaxValue());
            loadPoints(tree);
            statusBar.setText("Loaded: " + loadFile.getTotalLoaded().toString() + " objects");
        }
    }


    public void drawWindow(QualifiedWindow qwin){
        Double unit = plot.getPane().getPrefWidth() / (2 * plot.getxAxis().getUpperBound());
        Double fromOriginX = plot.getPane().getPrefWidth() / 2 + qwin.getR().getLeftTop().getX() * unit;
        Double fromOriginY = plot.getPane().getPrefHeight() / 2 - qwin.getR().getLeftTop().getY() * unit;

        gc.setStroke(Color.GOLD);
        gc.setFill(Color.GOLD);
        gc.setLineWidth(0.5);
        gc.setLineDashes(0,0);

        gc.strokeRect(fromOriginX,fromOriginY,qwin.getSearchRegion().getLength()*unit,qwin.getSearchRegion().getWidth()*unit);
    }

    public void drawSearchRegion(QualifiedWindow qwin){
        Double unit = plot.getPane().getPrefWidth() / (2 * plot.getxAxis().getUpperBound());
        Double fromOriginX = plot.getPane().getPrefWidth() / 2 + qwin.getR().getLeftTop().getX() * unit;
        Double fromOriginY = plot.getPane().getPrefHeight() / 2 - qwin.getR().getLeftTop().getY() * unit;

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.setLineWidth(0.5);
        gc.setLineDashes(5,5);

       gc.strokeRect(fromOriginX,fromOriginY,qwin.getLength()*unit,qwin.getWidth()*unit);
    }


    public void handleCloseButton(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void handleStartButton(ActionEvent actionEvent) {
        if (tree == null) {
            statusBar.setText("Load some files!");
        } else {
            if(widthText.getText().equals("") || lengthText.getText().equals("") || numObjText.getText().equals("") ) {
                statusBar.setText("Fill the fields!");
            } else {
                String s1 = widthText.getText();
                String s2  = lengthText.getText();
                String s3 = numObjText.getText();

                Integer width = Integer.parseInt(s1);
                Integer length = Integer.parseInt(s2);
                Integer num = Integer.parseInt(s3);

                NWC nwc = new NWC();
                QualifiedWindow qwin = nwc.run(tree,width,length,num);
                if (qwin != null) {
                    gc.clearRect(0, 0, plot.getPrefWidth(), plot.getPrefWidth());
                    drawPoint(0, 0, "q", Color.RED);
                    loadPoints(qwin.getSqwin(), true);
                    drawSearchRegion(qwin);
                    drawWindow(qwin);
                    statusBar.setText("Min distance: " + nwc.getDistBest() + "\t Number of objects: " + qwin.getSqwin().getSize());
                } else {
                    statusBar.setText("Not found!");
                }

            }
        }
    }
}
