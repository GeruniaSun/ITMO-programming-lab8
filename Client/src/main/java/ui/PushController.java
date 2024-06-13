package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PushController {
    private Stage stage;
    @FXML
    public Label titleLabel;
    @FXML
    public Label messageLabel;
    @FXML
    public Button okButton;
    @FXML
    public ImageView image;
    @FXML
    public void initialize(){
        okButton.setOnAction(event -> stage.close());
    }
    public void warn(String title, String content) throws FileNotFoundException {
        titleLabel.setText(title);
        titleLabel.setTextFill(Paint.valueOf("#FF0000"));
        messageLabel.setText(content);
        image.setImage(new Image(new FileInputStream("images/questionIcon.png")));
        stage.showAndWait();
    }
    public void info(String title, String content) throws FileNotFoundException {
        titleLabel.setText(title);
        titleLabel.setTextFill(Paint.valueOf("#525252"));
        messageLabel.setText(content);
        image.setImage(new Image(new FileInputStream("images/questionIcon.png")));
        stage.showAndWait();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
