package com.example.javaspringbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    PRODUCT_NOT_FOUND("Product not found", HttpStatus.NOT_FOUND),
    USERNAME_EXISTS("Username already exists", HttpStatus.BAD_REQUEST),
    INVALID_STRING("Invalid string", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS("Email already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ErrorType(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}