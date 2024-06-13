package ui.l10n;

import java.util.ListResourceBundle;

public class AuthorisationLabels_tr extends ListResourceBundle {
    public Object[][] getContents() { return contents; }
    private final Object[][] contents = {
            {"Label", "Ho? geldiniz!"},
            {"Login_field", "Kullan?c? ad?n?z? girin"},
            {"Password_field", "Parolan?z? girin"},
            {"Login_button", "Giri? yap"},
            {"Register_button", "Kay?t ol"},
            {"invalid_password_message", "Minumum ?ifre uzunlu?u 4 karakter"},
            {"wrong_password_message", "Yanl?? ?ifre"},
            {"user_exists_message", "Bu kullan?c? ad? zaten al?nm??"},
            {"user_no_exists_message", "Böyle bir kullan?c? bulunmamaktad?r"}
    };
}
