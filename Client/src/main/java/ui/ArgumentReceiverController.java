package ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Locale;

public class ArgumentReceiverController implements Controller{
    @FXML
    private TextField field;
    private Stage stage;

    public void show(Locale locale){
        field.setPromptText("enter argument");
        stage.showAndWait();
    }

    @FXML
    public void cancelButtonOnClick(){
        stage.close();
    }

    @FXML
    public void readArgs(){
        MainPageController.args = field.getText();
        stage.close();
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
