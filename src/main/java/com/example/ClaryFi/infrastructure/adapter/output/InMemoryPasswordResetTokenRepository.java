package com.example.ClaryFi.infrastructure.adapter.output;

import com.example.ClaryFi.application.port.output.PasswordResetTokenRepositoryPort;
import com.example.ClaryFi.domain.model.PasswordResetToken;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPasswordResetTokenRepository  implements PasswordResetTokenRepositoryPort {
    private final Map<String, PasswordResetToken> tokenStorage = new ConcurrentHashMap<>();

    @Override
    public void save(PasswordResetToken token) {
        tokenStorage.put(token.getToken(), token);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return Optional.ofNullable(tokenStorage.get(token));
    }

    @Override
    public void delete(PasswordResetToken token) {
        tokenStorage.remove(token.getToken());
    }
}
