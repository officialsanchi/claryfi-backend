package com.example.ClaryFi.infrastructure.adapter.output;

import com.example.ClaryFi.application.port.output.UserRepositoryPort;
import com.example.ClaryFi.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    @Override
    public Optional<User> findById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }
}
