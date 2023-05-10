package com.example.works;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    //apsirasome sablonus regex
    private static final String USERNAME_PATTERN="^[A-Za-z]{3,20}$";
    private static final String PASSWORD_PATTERN="^[A-Za-z0-9.!@_]{5,20}$";
    private static final String EMAIL_PATTERN="^[A-Za-z0-9@._-]{10,50}$";

    //validuoja ivestus vartotojo duomenis
    //jeigu vartotojo ivesti duomenys atitinka auksciau ivesta sablona grazinsime - true priesingu atveju false
    public static boolean isUserNameValid(String username){
        //sukuriamas sablonas pagal eiluteje (final String) aprasytas taisykles
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        //patikrina ar vartotojo ivesti duomenys atitinka auksciau aprasyta sablona
        Matcher matcher = pattern.matcher(username);
        //true kai atitnka, false neatitinka
        return matcher.find();
    }
    public static boolean isPasswordValid(String password){
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
    public static boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
