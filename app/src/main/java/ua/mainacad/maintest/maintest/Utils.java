package ua.mainacad.maintest.maintest;

public class Utils {

    static boolean isEmailValid(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (email.trim().isEmpty()) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        if (email.indexOf("@") != email.lastIndexOf("@")) {
            return false;
        }
        return "mydomain.com".equals(email.substring(email.indexOf("@") + 1));
    }

}
