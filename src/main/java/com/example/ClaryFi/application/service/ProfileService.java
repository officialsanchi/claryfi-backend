package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.input.ProfileManagementUseCase;
import com.example.ClaryFi.application.port.output.UserRepositoryPort;
import com.example.ClaryFi.domain.exception.UserNotFoundException;
import com.example.ClaryFi.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class ProfileService  implements ProfileManagementUseCase {
    private final UserRepositoryPort userRepositoryPort;

    public ProfileService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }
    @Override
    public User viewProfile(Long userId) {
        return userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public void updateProfile(Long userId, User updatedUser) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Update non-sensitive fields
        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
        // add more fields as needed

        userRepositoryPort.save(user);
    }

    @Override
    public User editProfile(String userId, User updatedProfile) {
        return null;
    }

}



