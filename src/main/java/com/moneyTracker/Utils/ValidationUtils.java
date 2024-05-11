package com.moneyTracker.Utils;

import com.moneyTracker.CustomExceptions.ValidationException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class ValidationUtils {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static void validateEmail(String email) throws ValidationException {
        if (email == null || email.isEmpty()) {
            throw new ValidationException("Email field is empty");
        }
        if (!email.matches(EMAIL_PATTERN)) {
            throw new ValidationException("Email is not valid");
        }
    }

    public static void validatePassword(String password) throws ValidationException {
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password field is empty");
        }
        if (!password.matches(PASSWORD_PATTERN)) {
            throw new ValidationException("Invalid password");
        }
    }

    public static void validateConfirmPassword(String password, String confirmPassword) throws ValidationException {
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            throw new ValidationException("Confirm password field is empty");
        }
        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Passwords do not match");
        }
    }

    public static void validateAccountId(String accountId) throws ValidationException {
        if (accountId == null || accountId.isEmpty()) {
            throw new ValidationException("Account ID is required");
        }
        if (!accountId.matches("\\d{4} \\d{4} \\d{4} \\d{4}")) {
            throw new ValidationException("ID format: 1234 1234 1234 1234");
        }
    }

    public static void validateAccountOwner(String owner) throws ValidationException {
        if (owner == null || owner.trim().isEmpty()) {
            throw new ValidationException("Account owner is required");
        }
        if (!owner.matches("[A-Z]* [A-Z]*")) {
            throw new ValidationException("Owner format: FIRSTNAME LASTNAME");
        }
    }

    public static void validateExpirationDate(String date) throws ValidationException {
        if (date == null || !date.matches("\\d{2}/\\d{4}")) {
            throw new ValidationException("Expiration date format: MM/YYYY");
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            sdf.setLenient(false);
            Date expDate = sdf.parse(date);
            if (expDate.before(new Date())) {
                throw new ValidationException("Expiration date must be in the future");
            }
        } catch (ParseException e) {
            throw new ValidationException("Invalid date format");
        }
    }

    public static void validateBalance(String balance) throws ValidationException {
        if (balance == null || balance.isEmpty()) {
            throw new ValidationException("Balance is required");
        }
        try {
            double bal = Double.parseDouble(balance);
            if (bal < 0) {
                throw new ValidationException("Balance >= 0");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Balance must be a valid number");
        }
    }
}
