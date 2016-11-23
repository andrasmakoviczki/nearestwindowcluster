package hu.elte.databasesystems;

import hu.elte.databasesystems.model.DataObject;
import hu.elte.databasesystems.model.NWC;
import hu.elte.databasesystems.model.QualifiedWindow;
import hu.elte.databasesystems.model.RTree;
import hu.elte.databasesystems.model.util.ReadFromFile;
import hu.elte.databasesystems.view.Plot;
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
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Andras Makoviczki on 2016. 11. 16.
 */
public class AppController implements Initializable {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    private RTree tree;

    @SuppressWarnings({"FalseWarning", "CanBeFinal"})
    @FXML
    private TextField numObjText;
    @SuppressWarnings({"FalseWarning", "CanBeFinal"})
    @FXML
    private TextField lengthText;
    @SuppressWarnings({"FalseWarning", "CanBeFinal"})
    @FXML
    private TextField widthText;
    @FXML
    private MenuItem loadMenuItem;
    @FXML
    @SuppressWarnings("FalseWarning")
    private MenuItem closeAppItem;
    @SuppressWarnings({"FalseWarning", "CanBeFinal", "unused"})
    @FXML
    private Stage stage;
    @SuppressWarnings({"FalseWarning", "CanBeFinal"})
    @FXML
    private Plot plot;
    @SuppressWarnings({"FalseWarning", "CanBeFinal"})
    @FXML
    private StatusBar statusBar;
    @FXML
    private Button startButton;

    private GraphicsContext gc;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initPlot(8);
    }

    private void initPlot(Integer range) {
        Pane _pane = plot.getPane();
        NumberAxis _xAxis = plot.getXAxis();
        NumberAxis _yAxis = plot.getYAxis();

        _pane.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        _pane.setPrefSize(500, 500);
        _pane.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        _xAxis.setLowerBound(-range);
        _xAxis.setUpperBound(range);
        _xAxis.setTickUnit(range);
        _xAxis.setMinorTickVisible(false);
        _xAxis.setPrefWidth(500);
        _xAxis.setLayoutY(500 / 2);

        _yAxis.setLowerBound(-range);
        _yAxis.setUpperBound(range);
        _yAxis.setTickUnit(range);
        _yAxis.setMinorTickVisible(false);
        _yAxis.setPrefHeight(500);
        _yAxis.layoutXProperty().bind(
                Bindings.subtract(
                        (500 / 2) + 1,
                        _yAxis.widthProperty()
                )
        );

        Canvas _canvas = plot.getCanvas();
        _canvas.setWidth(500 + 50);
        _canvas.setHeight(500 + 50);

        gc = _canvas.getGraphicsContext2D();

        drawPoint(0, 0, "q", Color.RED);
        _pane.getChildren().setAll(_xAxis, _yAxis, _canvas);
    }

    private void drawPoint(Integer x, Integer y) {
        drawPoint(x, y, null, Color.BLUE);
    }

    private void drawPoint(Integer x, Integer y, String label, Color color) {
        Double unit = plot.getPane().getPrefWidth() / (2 * plot.getXAxis().getUpperBound());
        Double fromOriginX = plot.getPane().getPrefWidth() / 2 + x * unit;
        Double fromOriginY = plot.getPane().getPrefHeight() / 2 - y * unit;

        if (label != null) {
            gc.setStroke(Color.BLACK);
            gc.setFill(Color.BLACK);
            gc.setLineWidth(0.25);
            gc.strokeText(label, fromOriginX + 5, fromOriginY - 5);
        }

        gc.setStroke(color);
        gc.setFill(color);
        gc.setLineWidth(5);
        gc.strokeOval(fromOriginX, fromOriginY, 1, 1);
    }

    private void createPlot(Integer range) {
        gc.clearRect(0, 0, plot.getPrefWidth(), plot.getPrefWidth());
        initPlot(range);
    }

    private void loadPoints(RTree<Integer, DataObject> loadedObjects) {
        for (DataObject data : loadedObjects) {
            drawPoint(data.getX(), data.getY());
        }
    }

    @SuppressWarnings("UnusedParameters")
    public void handleLoadButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        FileChooser.ExtensionFilter txtExtension = new FileChooser.ExtensionFilter("Text", "*.txt");
        fileChooser.getExtensionFilters().add(txtExtension);

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            ReadFromFile loadFile = new ReadFromFile(file);
            this.tree = loadFile.ParseFile();
            tree.setParents();
            createPlot(loadFile.getAbsMaxValue());
            //noinspection unchecked
            loadPoints(tree);
            statusBar.setText("Loaded: " + loadFile.getTotalLoaded().toString() + " objects");
        }
    }


    private void drawWindow(QualifiedWindow qwin) {
        Double unit = plot.getPane().getPrefWidth() / (2 * plot.getXAxis().getUpperBound());
        Double fromOriginX = plot.getPane().getPrefWidth() / 2 + qwin.getR().getLeftTop().getX() * unit;
        Double fromOriginY = plot.getPane().getPrefHeight() / 2 - qwin.getR().getLeftTop().getY() * unit;

        gc.setStroke(Color.GOLD);
        gc.setFill(Color.GOLD);
        gc.setLineWidth(1);
        gc.setLineDashes(0, 0);

        gc.strokeRect(fromOriginX, fromOriginY, qwin.getSearchRegion().getLength() * unit, qwin.getSearchRegion().getWidth() * unit);
    }

    private void drawSearchRegion(QualifiedWindow qwin) {
        Double unit = plot.getPane().getPrefWidth() / (2 * plot.getXAxis().getUpperBound());
        Double fromOriginX = plot.getPane().getPrefWidth() / 2 + qwin.getR().getLeftTop().getX() * unit;
        Double fromOriginY = plot.getPane().getPrefHeight() / 2 - qwin.getR().getLeftTop().getY() * unit;

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.setLineWidth(1);
        gc.setLineDashes(5, 5);

        gc.strokeRect(fromOriginX, fromOriginY, qwin.getLength() * unit, qwin.getWidth() * unit);
    }


    @SuppressWarnings("UnusedParameters")
    public void handleCloseButton(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    @SuppressWarnings("UnusedParameters")
    public void handleStartButton(ActionEvent actionEvent) {
        if (tree == null) {
            statusBar.setText("Load some files!");
        } else {
            if (widthText.getText().equals("") || lengthText.getText().equals("") || numObjText.getText().equals("")) {
                statusBar.setText("Fill the fields!");
            } else {
                Integer width = Integer.parseInt(widthText.getText());
                Integer length = Integer.parseInt(lengthText.getText());
                Integer num = Integer.parseInt(numObjText.getText());

                NWC nwc = new NWC();
                @SuppressWarnings("unchecked") QualifiedWindow qwin = nwc.run(tree, width, length, num);
                if (qwin != null) {
                    gc.clearRect(0, 0, plot.getPrefWidth(), plot.getPrefWidth());
                    drawPoint(0, 0, "q", Color.RED);
                    loadPoints(qwin.getSqwin());
                    drawSearchRegion(qwin);
                    drawWindow(qwin);
                    statusBar.setText("Min distance: " + BigDecimal.valueOf(nwc.getDistBest()).setScale(3, BigDecimal.ROUND_HALF_UP)
                            + "\t Number of objects: " + qwin.getSqwin().getSize());
                } else {
                    statusBar.setText("Not found!");
                }

            }
        }
    }
}
