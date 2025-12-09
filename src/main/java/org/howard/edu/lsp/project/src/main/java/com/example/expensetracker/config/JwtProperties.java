package com.example.expensetracker.config;

public record JwtProperties(
        String secret,
        long expirationMinutes
){}
