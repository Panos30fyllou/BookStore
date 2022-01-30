package com.example.bookstore.validators;

public class RegisterValidator {

    public static boolean passwordsMatch(String password, String confirmation){
        return password.equals(confirmation);
    }
}
