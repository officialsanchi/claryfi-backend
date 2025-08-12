package com.example.ClaryFi.infrastructure.adapter.output;

import com.example.ClaryFi.application.port.output.KeycloakAdminPort;
import org.springframework.stereotype.Repository;

@Repository
public class KeycloakAdminAdapter implements KeycloakAdminPort {
    @Override
    public void assignRoleToUser(String userId, String roleName) {

    }

    @Override
    public void removeRoleFromUser(String userId, String roleName) {

    }
}
