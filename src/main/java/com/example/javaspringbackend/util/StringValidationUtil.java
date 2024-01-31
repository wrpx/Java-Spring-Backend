package com.example.javaspringbackend.util;

public class StringValidationUtil {
    private StringValidationUtil() {
    }

    public static boolean isInvalidString(String input) {
        return input != null && !input.matches("[A-Za-z0-9 ]+");
    }
}
