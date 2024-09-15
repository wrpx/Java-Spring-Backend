package com.example.javaspringbackend.service;

import com.example.javaspringbackend.exception.CustomException;
import com.example.javaspringbackend.exception.ErrorType;
import com.example.javaspringbackend.model.User;
import com.example.javaspringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        if (existsByEmail(user.getEmail())) {
            throw new CustomException(ErrorType.EMAIL_EXISTS.getMessage(), ErrorType.EMAIL_EXISTS.getStatus());
        }
        if (isInvalidString(user.getName()) || isInvalidString(user.getEmail()) || isInvalidString(user.getPassword())) {
            throw new CustomException("Invalid input", ErrorType.INVALID_STRING.getStatus());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean checkUserCredentials(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found with id: " + id, ErrorType.USER_NOT_FOUND.getStatus()));
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        if (userDetails.getName() != null) user.setName(userDetails.getName());
        if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
        if (userDetails.getRole() != null) user.setRole(userDetails.getRole());
        if (userDetails.getPersonalInfo() != null) user.setPersonalInfo(userDetails.getPersonalInfo());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User not found with id: " + id, ErrorType.USER_NOT_FOUND.getStatus());
        }
        userRepository.deleteById(id);
    }

    private boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isInvalidString(String input) {
        return !input.matches("[A-Za-z0-9@.]+");
    }
}