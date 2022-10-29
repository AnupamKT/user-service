package com.example.userservice.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String VALID_MOBILE_NUMBER_FORMAT = "^\\d{10}$";
    private static final String VALID_EMAIL_ADDRESS_FORMAT = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    public static boolean validateMobileNumberFormat(String number) {
        Pattern pattern = Pattern.compile(VALID_MOBILE_NUMBER_FORMAT);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean validateEmailAddressFormat(String emailAdd) {
        Pattern pattern = Pattern.compile(VALID_EMAIL_ADDRESS_FORMAT);
        Matcher matcher = pattern.matcher(emailAdd);
        return matcher.matches();
    }
}
