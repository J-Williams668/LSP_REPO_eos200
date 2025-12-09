package com.example.expensetracker.controller;

import com.example.expensetracker.domain.UserEntity;
import com.example.expensetracker.dto.AuthenticationResponse;
import com.example.expensetracker.dto.LoginRequest;
import com.example.expensetracker.dto.RegisterRequest;
import com.example.expensetracker.security.JwtService;
import com.example.expensetracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(@Valid @RequestBody RegisterRequest request){
        UserEntity user = userService.register(request);
        String token = jwtService.generateToken(user.getEmail());
        return new AuthenticationResponse(token);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest request){
        var authToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        authenticationManager.authenticate(authToken);
        String token = jwtService.generateToken(request.email());
        return new AuthenticationResponse(token);
    }
}