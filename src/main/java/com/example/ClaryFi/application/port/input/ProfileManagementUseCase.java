package com.example.ClaryFi.application.port.input;

import com.example.ClaryFi.domain.model.User;
import org.springframework.stereotype.Component;


public interface ProfileManagementUseCase {
    User viewProfile(Long userId);
    void updateProfile(Long userId, User updatedUser);
    User editProfile(String userId, User updatedProfile);
}
