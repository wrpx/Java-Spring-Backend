package com.example.javaspringbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    PRODUCT_NOT_FOUND("Product not found", HttpStatus.NOT_FOUND),
    USERNAME_EXISTS("Username already exists", HttpStatus.BAD_REQUEST),
    INVALID_STRING("Invalid string", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    ErrorType(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
