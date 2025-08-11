package com.example.ClaryFi.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String token;
    private Instant expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ConfirmationToken(String token, Instant expiresAt, User user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
