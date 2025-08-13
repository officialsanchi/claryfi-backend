package com.example.ClaryFi.domain.model;

import jakarta.ws.rs.SeBootstrap;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter


@Data
public class ApiKey {
    private String id;
    private String key;
    private Instant createdAt;
    private String userId;
    private boolean revokedAt;

    public boolean getRevokedAt() {
        return false;
    }

    public void setRevokedAt(boolean revokedAt) {
        this.revokedAt = revokedAt;
    }

    public ApiKey() {
    }

    public ApiKey(String id, String key, Instant createdAt, String userId, boolean revokedAt) {
        this.id = id;
        this.key = key;
        this.createdAt = createdAt;
        this.userId = userId;
        this.revokedAt = revokedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
