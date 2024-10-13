package com.example.javaspringbackend.controller;

import com.example.javaspringbackend.util.JwtUtil;
import com.example.javaspringbackend.model.User;
import com.example.javaspringbackend.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthenticationController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials) {
        if (userService.checkUserCredentials(credentials.getEmail(), credentials.getPassword())) {
            String token = jwtUtil.generateJwtToken(credentials.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response); 
        } else {
            return ResponseEntity.status(401).body("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
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
