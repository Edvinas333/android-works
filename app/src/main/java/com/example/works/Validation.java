package com.example.works;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String USERNAME_PATTERN = "^[A-Za-z]{3,20}$";
    private static final String PASSWORD_PATTERN = "^[A-Za-z0-9.!@_]{5,20}$";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9@._-]{10,50}$";

    public static boolean isUserNameValid(String username) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
