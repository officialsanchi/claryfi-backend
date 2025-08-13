package com.example.ClaryFi.application.port.output;

import com.example.ClaryFi.domain.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepositoryPort {
    void save(PasswordResetToken token);
    Optional<PasswordResetToken> findByToken(String token);
    void delete(PasswordResetToken token);

}
