package com.pgno248.util;

import java.util.regex.Pattern;

public class AuthUtil {
    // Password: at least 8 chars, 1 upper, 1 lower, 1 digit, 1 special char
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    // Gmail: must end with @gmail.com
    private static final Pattern GMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@gmail\\.com$");

    // Phone: 10 digits (India-style, adjust as needed)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^[6-9]\\d{9}$");

    // Credit Card: 13-19 digits (Luhn check recommended for real use)
    private static final Pattern CARD_PATTERN = Pattern.compile(
            "^\\d{13,19}$");

    // CVV: 3 or 4 digits
    private static final Pattern CVV_PATTERN = Pattern.compile(
            "^\\d{3,4}$");

    public static boolean validatePassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean validateGmail(String email) {
        return email != null && GMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean validatePhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean validateCreditCard(String cardNumber) {
        return cardNumber != null && CARD_PATTERN.matcher(cardNumber).matches();
    }

    public static boolean validateCVV(String cvv) {
        return cvv != null && CVV_PATTERN.matcher(cvv).matches();
    }
}
