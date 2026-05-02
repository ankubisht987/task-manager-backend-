package com.task.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.backend.model.User;
import com.task.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ✅ SIGNUP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email & Password required");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    // ✅ LOGIN (PROPER VALIDATION)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email & Password required");
        }

        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());

        if (dbUser.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");
        }

        if (!encoder.matches(user.getPassword(), dbUser.get().getPassword())) {
            return ResponseEntity.status(401).body("Wrong password");
        }

        User u = dbUser.get();
        u.setPassword(null); // 🔥 hide password

        return ResponseEntity.ok(u);
    }
}