package com.example.projektsale.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return ResponseEntity.ok("Logged in as: " + auth.getName() + " with roles: " + auth.getAuthorities());
        }
        return ResponseEntity.ok("Not authenticated");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint working - no need login");
    }
}