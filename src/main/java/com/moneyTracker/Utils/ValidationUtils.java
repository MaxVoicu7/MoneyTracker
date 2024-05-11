package com.moneyTracker.Utils;

import com.moneyTracker.CustomExceptions.ValidationException;

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
}
