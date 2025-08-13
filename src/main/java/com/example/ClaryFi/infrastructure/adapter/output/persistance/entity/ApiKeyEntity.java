package com.example.ClaryFi.infrastructure.adapter.output.persistance.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys")

public class ApiKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    @Column(nullable = false, unique = true, length = 255)
    private String key;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private String userId;

    public boolean isRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(boolean revokedAt) {
        this.revokedAt = revokedAt;
    }

    @Column(nullable = false)
    private boolean revokedAt;


    public ApiKeyEntity() {}

    public ApiKeyEntity(String id, String key, Instant createdAt, String userId, boolean revokedAt) {
        this.id = id;
        this.key = key;
        this.createdAt = createdAt;
        this.userId = userId;
        this.revokedAt = revokedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
