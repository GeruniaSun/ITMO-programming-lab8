package ui.l10n;

import java.util.ListResourceBundle;

public class AuthorisationLabels_ru_RU extends ListResourceBundle {
    public Object[][] getContents() { return contents; }
    private final Object[][] contents = {
            {"Label", "52 на связи, орлы! Как слышно?"},
            {"Login_field", "Введите логин"},
            {"Password_field", "Введите пароль"},
            {"Login_button", "Войти"},
            {"Register_button", "Зарегистрироваться"},
            {"invalid_password_message", "Минимальная длина пароля 4 символа"},
            {"wrong_password_message", "Неправильный пароль"},
            {"user_exists_message", "Этот логин уже занят"},
            {"user_no_exists_message", "Такого пользователя не существует"}
    };
}
