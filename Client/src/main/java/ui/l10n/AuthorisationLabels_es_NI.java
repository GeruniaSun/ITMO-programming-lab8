package ui.l10n;

import java.util.ListResourceBundle;

public class AuthorisationLabels_es_NI extends ListResourceBundle {
    public Object[][] getContents() { return contents; }
    private final Object[][] contents = {
            {"Label", "¡Bienvenido"},
            {"Login_field", "Ingrese su nombre de usuario"},
            {"Password_field", "Ingrese su contraseña"},
            {"Login_button", "Iniciar sesión"},
            {"Register_button", "Registrarse"},
            {"invalid_password_message", "Longitud mínima de la contraseña 4 caracteres"},
            {"wrong_password_message", "Contraseña incorrecta"},
            {"user_exists_message", "Este inicio de sesión ya está realizado"},
            {"user_no_exists_message", "Este usuario no existe"}
    };
}
