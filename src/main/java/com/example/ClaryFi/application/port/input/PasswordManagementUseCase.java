package com.example.ClaryFi.application.port.input;

import org.springframework.stereotype.Component;

@Component
public interface PasswordManagementUseCase {
    void forgotPassword(String email);
    void resetPassword(String token, String newPassword);
    void changePassword(String userId, String oldPassword, String newPassword);
}
