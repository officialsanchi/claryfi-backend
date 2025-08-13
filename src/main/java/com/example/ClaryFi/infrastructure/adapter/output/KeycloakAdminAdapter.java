package com.example.ClaryFi.infrastructure.adapter.output;

import com.example.ClaryFi.application.port.output.KeycloakAdminPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KeycloakAdminAdapter implements KeycloakAdminPort {
    @Override
    public void assignRoleToUser(String userId, String roleName) {

    }

    @Override
    public void removeRoleFromUser(String userId, String roleName) {

    }

    @Override
    public String createUser(String username, String email, String temporaryPassword, boolean enabled) {
        return "";
    }

    @Override
    public void executeActionsEmail(String keycloakUserId, List<String> actions) {

    }

    @Override
    public void logoutUserSessions(String keycloakUserId) {

    }

    @Override
    public void addRequiredAction(String keycloakUserId, String action) {

    }

    @Override
    public void removeRequiredAction(String keycloakUserId, String action) {

    }

    @Override
    public void resetPassword(String keycloakUserId, String newPassword) {

    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) {

    }

    @Override
    public boolean validatePassword(String userId, String oldPassword) {

        return false;
    }

    @Override
    public void updatePassword(String userId, String newPassword) {

    }
}
