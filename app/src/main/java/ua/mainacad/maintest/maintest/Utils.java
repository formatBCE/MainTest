package ua.mainacad.maintest.maintest;

public class Utils {

    public static boolean isEmailValid(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (email.trim().isEmpty()) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        return email.indexOf("@") == email.lastIndexOf("@");
    }

}
