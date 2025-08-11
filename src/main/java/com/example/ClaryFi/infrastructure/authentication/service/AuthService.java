package com.example.ClaryFi.infrastructure.authentication.service;

import com.example.ClaryFi.api.dtos.SignupRequest;
import com.example.ClaryFi.api.dtos.TokenResponse;
import org.springframework.stereotype.Component;

@Component
public interface AuthService {
    String createUser(SignupRequest request); // Returns user_id or token
    void verifyEmail(String token);
    String getAccessToken(String username, String password); // For login
    TokenResponse login(String email, String password, String realm);
}