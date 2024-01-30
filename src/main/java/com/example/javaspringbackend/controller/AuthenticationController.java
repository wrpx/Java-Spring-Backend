// AuthenticationController.java
package com.example.javaspringbackend.controller;

import com.example.javaspringbackend.model.User;
import com.example.javaspringbackend.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (userService.checkUserCredentials(credentials.getUsername(), credentials.getPassword())) {
            return ResponseEntity.ok("User logged in successfully");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            User newUser = new User();
            newUser.setUsername(request.getUsername());
            newUser.setPassword(request.getPassword());
            userService.saveUser(newUser);

            return ResponseEntity.ok("registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unable to register user: " + e.getMessage());
        }
    }

    @Setter
    @Getter
    static class LoginCredentials {
        private String username;
        private String password;
    }

    @Setter
    @Getter
    static class RegistrationRequest {
        private String username;
        private String password;
    }
}
