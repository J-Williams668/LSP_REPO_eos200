package com.example.expensetracker.service;

import com.example.expensetracker.domain.UserEntity;
import com.example.expensetracker.dto.RegisterRequest;
import com.example.expensetracker.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity register (RegisterRequest request){
        userRepository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new EntityExistsException("Email already registered.");
                }
                );
        UserEntity user = new UserEntity();
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setUserName(request.userName());

        return userRepository.save(user);
    }
}
