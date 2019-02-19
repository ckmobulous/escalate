package com.escalate.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mahesh on 16/1/17.
 */

public class Validator {
    public final static String NUMBER_PATTERN = "^[0-9]$";
    public final static String ALL_PATTERN = "^[a-zA-Z0-9!@#$&()\\\\-`.+,/\\\"]*$";
    public final static String ALPHABET_PATTERN = "^[a-zA-Z\\s]+$ ";
    public final static String EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
    public final static String PHONE_NO_PATTERN = "^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$";

    public static boolean isValidNickname(String nickname) {
        if (nickname == null) nickname = "";
        String regexSt = "^[a-zA-Z0-9]+([a-zA-Z0-9](_|.| )[a-zA-Z0-9])*[a-zA-Z0-9]+$"; //with digit
        return nickname.matches(regexSt);
    }

    public static boolean isAlphaNumeric(String s) {
        String pattern = "^[a-zA-Z0-9]*$";
        return s.matches(pattern);
    }

    public static boolean isValidLastName(String lastName) {
        String regexStr = "^$|\\s+";
        String regexSt = "^[a-zA-Z\\s]+$";
        return lastName.matches(regexSt) || lastName.matches(regexStr);
    }

    public static boolean isValidFirstName(String name) {
        String regexSt = "^[a-zA-Z\\s]+$";
        return name != null && name.length() > 2 && name.matches(regexSt);
    }

    public static boolean isValidFullName(String name) {
        String regexSt = "^[a-z ,.'-]+$";
        //return name != null && name.length() > 2 && name.matches(regexSt);
        return name != null && name.length() > 2 ;
    }

    public static boolean isValidPhone(String phone) {
        String regexStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$";
        return phone.matches(regexStr);
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean isValidPAN(String pan) {
        String regexStr = "[A-Za-z]{5}\\d{4}[A-Za-z]{1}";
        return pan.matches(regexStr);
    }

    public static boolean isValidIFSC(String ifsc) {
        String regexStr = "^[A-Za-z]{4}\\d{7}$";
        return ifsc.matches(regexStr);
    }

    /*public static boolean isValidPassword(String testString) {
        return (testString.length() >= 6&&testString.length()<=10);
    }*/

    public static boolean isValidPassword(String password) {
        //Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d).{6,10})").matcher(password);
        String regex = "((?=(.*[A-Za-z]){4,})(?=(.*\\\\d){2,})(?!.*[~`!@#$%^&*?+-]))";
        String regexOne =  "((?=.*[a-z])(?=.*\\\\d).{6,10})";
        Matcher matcher = Pattern.compile(regexOne).matcher(password);
        return matcher.matches();
    }

    public static boolean validatePassword(String password){

//        final String regex = "((?=(.*[A-Za-z]){7,})(?=(.*\\d){2,})(?!.*[~`!@#$%^&*?+-]))";
        // final String string = "4Aasd2@#";
        final String regex = "^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z0-9]{7,20}$";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(password);

        if (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
            return true;
        }else{
            System.out.println("No match");
            return false;
        }

    }

    public static boolean isValidAlias(String value) {
        String regexSt = "^[a-zA-Z\\s]+$";
        return value != null && value.length() > 2 && value.matches(regexSt);
    }
}
