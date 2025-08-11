package com.example.ClaryFi.domain.repositories;

import com.example.ClaryFi.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
}
