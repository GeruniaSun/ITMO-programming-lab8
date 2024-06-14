package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class WindowLoader {

    private static final HashMap<WindowEnum, Stage> windowMap = new HashMap<>();

    private final static WindowLoader WINDOW_LOADER = new WindowLoader();

    public static WindowLoader getInstance() {
        return WINDOW_LOADER;
    }

    private WindowLoader() {
        URL mainURL = WindowLoader.class.getResource("/mainPage.fxml");
        URL authURL = WindowLoader.class.getResource("/authWindow.fxml");
        URL addURL = WindowLoader.class.getResource("/addForm.fxml");
        URL errURL = WindowLoader.class.getResource("/errorPush.fxml");
        URL respURL = WindowLoader.class.getResource("/responsePush.fxml");

        configureStage(mainURL, 800, 1200, WindowEnum.MAIN_WINDOW);
        configureStage(authURL, 250, 300, WindowEnum.AUTH_WINDOW);
        configureStage(addURL, 500, 300, WindowEnum.ADD_WINDOW);
        configureStage(errURL, 200, 600, WindowEnum.ERROR_WINDOW);
        configureStage(respURL, 200, 600, WindowEnum.RESPONSE_WINDOW);

//        windowMap.get(WindowEnum.ADD_WINDOW).setOnHidden(ae -> {
//            new AddController().clear();
//        });
    }

    public void showWindow(WindowEnum windowType){
        windowMap.get(windowType).show();
    }

    public void showAndWaitWindow(WindowEnum windowType){
        windowMap.get(windowType).showAndWait();
    }

    public void closeWindow(WindowEnum windowType){
        windowMap.get(windowType).close();
    }

    public Stage getWindow(WindowEnum windowType){
        return windowMap.get(windowType);
    }

    private static void configureStage(URL url, int height, int width, WindowEnum stageType){
        var loader = new FXMLLoader();
        loader.setLocation(url);
        var stage = new Stage();
        var root = loadFxml(loader);
        stage.setScene(new Scene(root));
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setResizable(false);
        //var window = loader.getController();
        windowMap.put(stageType, stage);
    }

    private static Parent loadFxml(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("Не удалось загрузить окно: " + loader.getLocation() +
                            "\nиз-за ошибки " + e.getMessage());
            return null;
            //throw new RuntimeException(e);
        }
    }
}
