package ui;

import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lt.shgg.commands.Show;
import lt.shgg.database.DatabaseParser;
import lt.shgg.network.Request;
import utils.Authorisator;
import utils.Sender;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class VisualizationController implements Controller{
    private Stage stage;

    private boolean refresh;
    private Locale locale;
    private HashMap<String, Color> colorMap;
    private HashMap<Long, Label> infoMap;
    private Random random;

    @FXML
    public ImageView backButton;
    @FXML
    public AnchorPane anchor;
    @FXML
    public Line yLine;
    @FXML
    public Line xLine;

    @FXML
    public void initialize() {
        colorMap = new HashMap<>();
        infoMap = new HashMap<>();
        random = new Random();
        infoMap.clear();
    }

    public void show() {}

    @FXML
    public void backButtonOnclick(){
        stage.close();
    }

    public void visualise() {
        anchor.getChildren().clear();
        anchor.getChildren().add(backButton);
        anchor.getChildren().add(xLine);
        anchor.getChildren().add(yLine);
        for (var ticket : new DatabaseParser().load().stream().toList()) {
            var owner = ticket.getAuthor();
            if (!colorMap.containsKey(owner)) {
                var r = random.nextDouble();
                var g = random.nextDouble();
                var b = random.nextDouble();
                if (Math.abs(r - g) + Math.abs(r - b) + Math.abs(b - g) < 0.6) {
                    r += (1 - r) / 1.4;
                    g += (1 - g) / 1.4;
                    b += (1 - b) / 1.4;
                }
                colorMap.put(owner, Color.color(r, g, b));
            }
            // рисуем кружок
            var size = Math.min((ticket.getPrice() + 1) / 100, 20);
            var circle = new Circle(size, colorMap.get(owner));
            circle.setStroke(Color.WHITE);
            circle.setStrokeWidth(3);

            double x = ((ticket.getCoordinates().getX()) + 300)%600;
            double y = ((ticket.getCoordinates().getY()) + 200)%400;
            var id = new Text("id:" + ticket.getId());
            var info = new Label("price" + " " + ticket.getPrice());
            info.setVisible(false);

            circle.setOnMouseEntered(mouseEvent -> {
                id.setVisible(false);
                info.setVisible(true);
                circle.setFill(colorMap.get(owner).brighter());
                circle.toFront();
                info.toFront();
            });

            circle.setOnMouseExited(mouseEvent -> {
                id.setVisible(true);
                info.setVisible(false);
                circle.setFill(colorMap.get(owner));
                id.toBack();
                circle.toBack();
                xLine.toBack();
                yLine.toBack();
            });

            anchor.getChildren().add(circle);
            anchor.getChildren().add(id);

            infoMap.put(ticket.getId(), info);
                circle.setCenterX(x);
                circle.setCenterY(y);
                info.translateXProperty().bind(circle.centerXProperty().add(circle.getRadius()));
                info.translateYProperty().bind(circle.centerYProperty().subtract(120));
                id.translateXProperty().bind(circle.centerXProperty().subtract(id.getLayoutBounds().getWidth() / 2));
                id.translateYProperty().bind(circle.centerYProperty().add(id.getLayoutBounds().getHeight() / 20));
                var darker = new FillTransition(Duration.millis(750), circle);
                darker.setFromValue(colorMap.get(owner));
                darker.setToValue(colorMap.get(owner).darker().darker());
                var brighter = new FillTransition(Duration.millis(750), circle);
                brighter.setFromValue(colorMap.get(owner).darker().darker());
                brighter.setToValue(colorMap.get(owner));
                var transition = new SequentialTransition(darker, brighter);
                transition.play();
        }
        for (var id : infoMap.keySet()) {
            anchor.getChildren().add(infoMap.get(id));
        }
        try {
        stage.showAndWait();
        } catch (Exception e) {
            stage.setAlwaysOnTop(true);
        }
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
