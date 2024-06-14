package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lt.shgg.network.Response;

import java.util.Locale;

public class ResponsePushController {
    @FXML
    public Label titleLabel;
    @FXML
    public Label messageLabel;
    @FXML
    public Button okButton;

    @FXML
    private void okButtonOnClick() {
        WindowLoader.getInstance().closeWindow(WindowEnum.RESPONSE_WINDOW);
    }

    public void writeResponse(Response response, Locale locale){
        WindowLoader.getInstance().showWindow(WindowEnum.RESPONSE_WINDOW);
        messageLabel.setText(response.getResult());
    }
}
