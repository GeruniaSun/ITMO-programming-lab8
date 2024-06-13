package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

public class PushManager {
    private static PushController pushController;

    static {
        try {
            FXMLLoader pushLoader = new FXMLLoader(PushManager.class.getResource("/pushWindow.fxml"));
            Parent pushRoot = pushLoader.load();
            Scene pushScene = new Scene(pushRoot);
            Stage pushStage = new Stage();
            pushStage.setScene(pushScene);
            pushStage.setResizable(false);
            pushController = pushLoader.getController();
            pushController.setStage(pushStage);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void alert(String title, Locale locale) {
        try {
            var localizedTitle = title;
            var localizedMessage = "Error";
            pushController.warn(localizedTitle, localizedMessage);
        } catch (FileNotFoundException e){
            throw new RuntimeException();
        }
    }

    public static void info(String title, String info, Locale locale) {
        try {
            var localizedTitle = title;
            var localizedInfo = info;
            pushController.info(localizedInfo, localizedTitle);
        }catch (FileNotFoundException e){
            throw new RuntimeException();
        }
    }

    private static Parent loadFxml(FXMLLoader loader) {
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            System.exit(1);
        }
        return parent;
    }
}
