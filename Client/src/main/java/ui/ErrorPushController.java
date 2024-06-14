package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Locale;

public class ErrorPushController {
    @FXML
    public Label titleLabel;
    @FXML
    public Label messageLabel;
    @FXML
    public Button okButton;

    @FXML
    private void okButtonOnClick(ActionEvent actionEvent) {
        WindowLoader.getInstance().closeWindow(WindowEnum.ERROR_WINDOW);
    }

    public void writeError(Exception e, Locale locale){
        WindowLoader.getInstance().showAndWaitWindow(WindowEnum.ERROR_WINDOW);
        messageLabel.setText(e.getMessage());
    }
}
