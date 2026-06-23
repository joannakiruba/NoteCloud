package com.notecloud.backend.dto;

public record LoginRequest(
        String email,
        String password
) {}