package com.example.ClaryFi.domain.repositories;

import com.example.ClaryFi.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findUserById(UUID id);
}
