//ProductNotFoundException.java
package com.example.javaspringbackend.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
