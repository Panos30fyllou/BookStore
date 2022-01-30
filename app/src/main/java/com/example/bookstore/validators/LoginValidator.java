package com.example.bookstore.validators;

public class LoginValidator {
    public static boolean ValidateUserCredentials(String username, String password) {
        return Validators.isValidUsername(username) && Validators.isValidPassword(password);
    }
}
