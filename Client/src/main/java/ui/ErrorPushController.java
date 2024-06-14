package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Locale;

public class ErrorPushController implements Controller{
    private Stage stage;
    @FXML
    public Label titleLabel;
    @FXML
    public Label messageLabel;
    @FXML
    public Button okButton;

    @FXML
    private void okButtonOnClick() {
        stage.close();
    }

    public void writeError(Exception e, Locale locale){
        messageLabel.setText(e.getMessage());
        stage.showAndWait();
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
