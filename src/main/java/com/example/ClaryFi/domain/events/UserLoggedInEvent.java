package com.example.ClaryFi.domain.events;

import java.time.Instant;

public class UserLoggedInEvent {
    private final Long userId;
    private final Instant timestamp;

    public UserLoggedInEvent(Long userId) {
        this.userId = userId;
        this.timestamp = Instant.now();
    }

    public Long getUserId() {
        return userId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
