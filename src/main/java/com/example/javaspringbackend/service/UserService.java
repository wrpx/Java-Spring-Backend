//UserService.java
package com.example.javaspringbackend.service;

import com.example.javaspringbackend.exception.CustomException;
import com.example.javaspringbackend.exception.ErrorType;
import com.example.javaspringbackend.model.User;
import com.example.javaspringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new CustomException(ErrorType.USERNAME_EXISTS.getMessage(), ErrorType.USERNAME_EXISTS.getStatus());
        }
        if (isInvalidString(user.getUsername()) || isInvalidString(user.getPassword())) {
            throw new CustomException("Username and password must contain only English letters and numbers", ErrorType.INVALID_STRING.getStatus());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean checkUserCredentials(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean isInvalidString(String input) {
        return !input.matches("[A-Za-z0-9]+");
    }
}
