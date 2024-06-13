package ui.l10n;

import java.util.ListResourceBundle;

public class AuthorisationLabels_ca extends ListResourceBundle {
    public Object[][] getContents() { return contents; }
    private final Object[][] contents = {
            {"Label", "Benvingut!"},
            {"Login_field", "Introduïu el login"},
            {"Password_field", "Introduïu la contrasenya"},
            {"Login_button", "Inicia la sessió"},
            {"Register_button", "Registrar-se"},
            {"invalid_password_message", "La longitud mínima de la contrasenya és de 4 caràcters"},
            {"wrong_password_message", "Contrasenya incorrecta"},
            {"user_exists_message", "Aquest usuari ja està ocupat"},
            {"user_no_exists_message", "Aquest usuari no existeix"}
    };
}
