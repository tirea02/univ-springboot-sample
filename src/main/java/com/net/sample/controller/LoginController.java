package com.net.sample.controller;


import java.util.HashMap;
import java.util.Map;

import com.net.sample.dto.LoginCredentials;
import com.net.sample.repository.LoginRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.net.sample.model.Login;

@RestController
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private HttpSession httpSession; // Inject HttpSession

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody LoginCredentials credentials) {
        try {
            Login user = loginRepository.getUserByUsername(credentials.getUsername());
            if (user != null && user.getPwd().equals(credentials.getPassword())) {
                // Store user information in the session
                httpSession.setAttribute("loggedInUser", user);
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }

    @GetMapping("/check-login")
    public ResponseEntity<Map<String, Object>> checkLoginStatus() {
        // Check if a user is logged in by looking at the session
        Login loggedInUser = (Login) httpSession.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "loggedIn");
            response.put("message", "User is logged in");
            response.put("user", loggedInUser);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "loggedOut");
            response.put("message", "User is not logged in");
            response.put("user", null);
            return ResponseEntity.ok(response);
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<?> handleLogout() {
        // Invalidate the session to log the user out
        httpSession.invalidate();
        return ResponseEntity.ok("Logout successful");
    }

}

