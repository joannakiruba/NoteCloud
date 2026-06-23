package com.notecloud.backend.controller;

import com.notecloud.backend.dto.UserRegistrationRequest;
import com.notecloud.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.notecloud.backend.dto.LoginRequest;
import com.notecloud.backend.dto.LoginResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody UserRegistrationRequest request
    ) {

        authService.register(
                request.username(),
                request.email(),
                request.password()
        );

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        String token = authService.login(
                request.email(),
                request.password()
        );

        return ResponseEntity.ok(new LoginResponse(token));
    }
}