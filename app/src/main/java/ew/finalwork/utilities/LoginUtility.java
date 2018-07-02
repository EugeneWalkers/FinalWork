package ew.finalwork.utilities;

public class LoginUtility {
    public static boolean isLoginValid(String login){
        return login.contains("@");
    }
    public static boolean isPasswordValid(String pass){
        return !pass.equals("");
    }
}
