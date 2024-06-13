package ui;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;

public class App extends Application {
    public void start(Stage stage) throws IOException {
        var loader = WindowLoader.getInstance();
        loader.showWindow(WindowEnum.AUTH_WINDOW);
    }
    public static void main(String... args) {
        launch(args);
    }
}