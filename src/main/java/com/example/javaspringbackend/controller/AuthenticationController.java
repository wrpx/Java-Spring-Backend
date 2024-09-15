package com.example.javaspringbackend.controller;

import com.example.javaspringbackend.model.User;
import com.example.javaspringbackend.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials) {
        if (userService.checkUserCredentials(credentials.getEmail(), credentials.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("token", "dummy-token"); // In a real application, generate a proper token
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @Setter
    @Getter
    private static class LoginCredentials {
        private String email;
        private String password;
    }
}