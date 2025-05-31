package com.example.projektsale.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication and authorization endpoints")
public class AuthController {

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns information about currently logged user")
    public ResponseEntity<String> getCurrentUser(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return ResponseEntity.ok("Logged in as: " + auth.getName() + " with roles: " + auth.getAuthorities());
        }
        return ResponseEntity.ok("Not authenticated");
    }

}