package com.example.ClaryFi.application.port.input;

import com.example.ClaryFi.domain.model.User;

public interface AuthenticationUseCase {
    User login(String username, String password);
    void logout(String userId);
    void enableMfa(String userId);
    void disableMfa(String userId);
}
