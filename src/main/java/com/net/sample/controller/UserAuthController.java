package com.net.sample.controller;
import com.net.sample.dto.LoginCredentials;
import com.net.sample.model.UserAccount;
import com.net.sample.repository.UserAccountRepository;
import com.net.sample.service.UserAccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserAuthController {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private HttpSession httpSession; // Inject HttpSession

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody LoginCredentials credentials) {
        try {
            UserAccount user = userAccountRepository.getUserByUserId(credentials.getUsername());
            if (user != null && user.getPassword().equals(credentials.getPassword())) {
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
        UserAccount loggedInUser = (UserAccount) httpSession.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "loggedIn");
            response.put("user", loggedInUser);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "loggedOut");
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute UserAccount userAccount) {
        try {
            System.out.println(userAccount);
            UserAccount newUser = userAccountService.createUserAccount(userAccount);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
