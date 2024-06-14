package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lt.shgg.network.Response;

import java.util.Locale;

public class ResponsePushController implements Controller{
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

    public void writeResponse(Response response, Locale locale){
        messageLabel.setText(response.getResult());
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
