package com.example.ClaryFi.application.port.output;

import com.example.ClaryFi.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;


public interface UserRepositoryPort {
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    User save(User user);
}
