package com.example.ClaryFi.application.port.output;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface KeycloakAdminPort {
    void assignRoleToUser(String keycloakUserId, String roleName);
    void removeRoleFromUser(String keycloakUserId, String roleName);
    String createUser(String username, String email, String temporaryPassword, boolean enabled);
    void executeActionsEmail(String keycloakUserId, List<String> actions); // for reset password/email
    void logoutUserSessions(String keycloakUserId);
    void addRequiredAction(String keycloakUserId, String action);
    void removeRequiredAction(String keycloakUserId, String action);
    void resetPassword(String keycloakUserId, String newPassword);
    void updatePasswordByEmail(String email, String newPassword);
    boolean validatePassword(String userId, String oldPassword);
    void updatePassword(String userId, String newPassword);
}
