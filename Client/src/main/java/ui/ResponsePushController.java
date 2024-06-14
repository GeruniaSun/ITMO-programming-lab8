package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lt.shgg.network.Response;

import java.util.Locale;
import java.util.ResourceBundle;

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
        var resources = ResourceBundle.getBundle("ui.l10n.Messages", locale);
        //messageLabel.setText(resources.getString(response.getResult()));
        messageLabel.setText(response.getResult());
        changeLanguage(locale);
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

    private void changeLanguage(Locale locale){
        var resources = ResourceBundle.getBundle("ui.l10n.PushLabels", locale);
        titleLabel.setText(resources.getString("Response"));
        okButton.setText(resources.getString("OK"));
    }
}
