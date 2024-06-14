package utils;

import lt.shgg.data.User;
import lt.shgg.database.DatabaseManager;
import lt.shgg.database.PasswordHasher;

import java.util.Scanner;

public class Authorisator {
    public static User user;

    public static User authorise(){
        var in = new Scanner(System.in);
        System.out.println("Вы уже смешарик?\n" +
                "Введите YES, если у вас уже есть аккаунт, и что-угодно в противном случае: ");
        if (in.nextLine().equalsIgnoreCase("YES")) return enter();
        else return registration();
    }

    public static User loginCheck(String login){
        var databaseManager = new DatabaseManager();
        if (databaseManager.findUser(login).isEmpty()){
            return null;
        } else return new User(login, databaseManager.findUser(login));
    }

    public static Boolean passwordCheck(String input, String password){
        return password.equals(PasswordHasher.passwordHash(input));
    }

    public static Boolean passwordCheck(String password){
        return  password != null && password.length() >= 4;
    }

    public static void saveUser(User user){
        var databaseManager = new DatabaseManager();
        databaseManager.addUser(user);
    }

    public static User getUser() {
        return user;
    }

    private static User registration(){
        var in = new Scanner(System.in);
        var novichok = new User();
        var databaseManager = new DatabaseManager();
        while (true) {
            System.out.println("Хорошо, давайте создадим аккаунт, это совсем не больно\n" +
                    "Придумайте и введите себе крутой логин");
            var login = in.nextLine();
            try {
                if (databaseManager.findUser(login).isEmpty()){
                novichok.setLogin(login);
                break;
                } else System.out.println("Этот логин занят, будь не таким как все");
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        while (true) {
            System.out.println("Класс! Теперь придумайте и введите хитрый пароль");
            var password = in.nextLine();
            try {
                novichok.setPassword(password);
                break;
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println(databaseManager.addUser(novichok));
        return novichok;
    }

    private static User enter(){
        var in = new Scanner(System.in);
        System.out.println("Отлично! вы уже смешарик!\n" +
                "Введите логин");
        var databaseManager = new DatabaseManager();
        var passwordDB = "";
        var trueLogin = "";
        while (true){
            var login = in.nextLine();
            if (databaseManager.findUser(login).isEmpty()) {
                System.out.println("Пользователя с таким логином нет");
                System.out.println("Введите логин");
            } else {
                passwordDB = databaseManager.findUser(login);
                trueLogin = login;
                break;
            }
        }
        System.out.println("Такого знаем. Но вы ли это?\n" +
                "Введите пароль");
        while (true){
            var password = in.nextLine();
            if (passwordDB.equals(PasswordHasher.passwordHash(password))) {
                System.out.println("Угадали!");
                return new User(trueLogin, password);
            } else System.out.println("Не угадали");
        }
    }
}

