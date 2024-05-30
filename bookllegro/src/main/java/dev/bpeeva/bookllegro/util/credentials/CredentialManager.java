package dev.bpeeva.bookllegro.util.credentials;

import java.util.regex.Pattern;

public class CredentialManager {

    public static final String RFC_5322_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final int PASSWORD_MIN_LEN = 10;

    /**
     * Checks if username is an email address
     * @param username user email
     * @return boolean value
     */
    public static boolean isEmail(String username) {

        return Pattern.compile(RFC_5322_REGEX)
                .matcher(username)
                .matches();
    }

    /**
     * Checks if user password satisfies criteria
     * @param password  user password
     * @return boolean value
     */
    public static boolean isPasswordValid(String password) {

        return (password.length() >= PASSWORD_MIN_LEN);
    }

}
