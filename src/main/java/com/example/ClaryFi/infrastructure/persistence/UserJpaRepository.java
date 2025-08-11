package com.example.ClaryFi.infrastructure.persistence;

import com.example.ClaryFi.domain.model.User;
import com.example.ClaryFi.domain.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserJpaRepository implements UserRepository {
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
