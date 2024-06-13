package ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lt.shgg.data.User;
import lt.shgg.database.DatabaseManager;
import utils.Authorisator;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthorisationController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    private String invalidPasswordMessage = "Min password length is 4";
    private String wrongPasswordMessage = "Wrong password";
    private String userExistsMessage = "This login already exists";
    private String userNoExistsMessage = "No user with this login";
    @FXML
    private void registerButtonOnClick(ActionEvent actionEvent) {

        var login = loginField.getText();
        var password = passwordField.getText();
        var user = Authorisator.loginCheck(login);
        if (user != null) startWrongInputAnimation(loginField, userExistsMessage);
        else {
            if (Authorisator.passwordCheck(password)) {
                Authorisator.saveUser(new User(login, password));
                welcomeLabel.setText("победа");
                // TODO отдать login в добрые руки
                var windowLoader = WindowLoader.getInstance();
                windowLoader.closeWindow(WindowEnum.AUTH_WINDOW);
                windowLoader.showWindow(WindowEnum.MAIN_WINDOW);
            }
            else startWrongInputAnimation(passwordField, invalidPasswordMessage);
        }
    }

    @FXML
    private void loginButtonOnClick(ActionEvent actionEvent){

        var login = loginField.getText();
        var password = passwordField.getText();

        var user = Authorisator.loginCheck(login);
        if (user == null) {
            startWrongInputAnimation(loginField, userNoExistsMessage);
        }
        else {
            if (Authorisator.passwordCheck(password, user.getPassword())) {
                welcomeLabel.setText("победа");
                // TODO отдать login в добрые руки
                var windowLoader = WindowLoader.getInstance();
                windowLoader.closeWindow(WindowEnum.AUTH_WINDOW);
                windowLoader.showWindow(WindowEnum.MAIN_WINDOW);
            } else startWrongInputAnimation(passwordField, wrongPasswordMessage);
        }
    }

    private void startWrongInputAnimation(TextField textField, String message) {

        welcomeLabel.setText(message);
        textField.clear();

        // анимация встряски
        var translateTransition = new TranslateTransition(Duration.millis(200));
        translateTransition.setNode(textField);
        translateTransition.setFromX(0);
        translateTransition.setFromY(0);
        translateTransition.setByX(5);
        translateTransition.setByY(5);
        translateTransition.setCycleCount(4);
        translateTransition.setAutoReverse(true);

        translateTransition.playFromStart();
    }

    @FXML
    private void ruButtonOnClick(ActionEvent actionEvent) {
        var locale = new Locale("ru", "RU");
        ResourceBundle resources = ResourceBundle.getBundle("ui.l10n.AuthorisationLabels", locale);
        changeLabels(resources);
    }

    @FXML
    private void trButtonOnClick(ActionEvent actionEvent) {
        var locale = new Locale("tr");
        var resources = ResourceBundle.getBundle("ui.l10n.AuthorisationLabels", locale);
        changeLabels(resources);
    }

    @FXML
    private void caButtonOnClick(ActionEvent actionEvent) {
        var locale = new Locale("ca");
        var resources = ResourceBundle.getBundle("ui.l10n.AuthorisationLabels", locale);
        changeLabels(resources);
    }

    @FXML
    private void esButtonOnClick(ActionEvent actionEvent) {
        var locale = new Locale("es", "NI");
        var resources = ResourceBundle.getBundle("ui.l10n.AuthorisationLabels", locale);
        changeLabels(resources);
    }

    private void changeLabels(ResourceBundle resources){
        welcomeLabel.setText(resources.getString("Label"));
        loginField.setPromptText(resources.getString("Login_field"));
        passwordField.setPromptText(resources.getString("Password_field"));
        loginButton.setText(resources.getString("Login_button"));
        registerButton.setText(resources.getString("Register_button"));
        invalidPasswordMessage = resources.getString("invalid_password_message");
        wrongPasswordMessage = resources.getString("wrong_password_message");
        userExistsMessage = resources.getString("user_exists_message");
        userNoExistsMessage = resources.getString("user_no_exists_message");
    }
}
