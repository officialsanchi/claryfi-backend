package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.input.AuditLoggingUseCase;
import com.example.ClaryFi.application.port.input.ProfileManagementUseCase;
import com.example.ClaryFi.application.port.output.UserRepositoryPort;
import com.example.ClaryFi.domain.exception.UserNotFoundException;
import com.example.ClaryFi.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class ProfileService  implements ProfileManagementUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final AuditLoggingUseCase auditLoggingUseCase;

    public ProfileService(UserRepositoryPort userRepositoryPort,
                          AuditLoggingUseCase auditLoggingUseCase) {
        this.userRepositoryPort = userRepositoryPort;
        this.auditLoggingUseCase = auditLoggingUseCase;
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

        // Audit log
        auditLoggingUseCase.logEvent("Profile updated for userId=" + userId);

        // Update allowed fields
        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
//        user.setFirstName(updatedUser.getFirstName());
//        user.setLastName(updatedUser.getLastName());

        userRepositoryPort.save(user);
    }

    @Override
    public User editProfile(Long userId, User updatedProfile) {
        User existingUser = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Audit log
        auditLoggingUseCase.logEvent("Profile edited for userId=" + userId);

        // Update allowed fields only
        if (updatedProfile.getEmail() != null) existingUser.setEmail(updatedProfile.getEmail());
        if (updatedProfile.getUsername() != null) existingUser.setUsername(updatedProfile.getUsername());
//        if (updatedProfile.getFirstName() != null) existingUser.setFirstName(updatedProfile.getFirstName());
//        if (updatedProfile.getLastName() != null) existingUser.setLastName(updatedProfile.getLastName());

        return userRepositoryPort.save(existingUser);
    }

}



