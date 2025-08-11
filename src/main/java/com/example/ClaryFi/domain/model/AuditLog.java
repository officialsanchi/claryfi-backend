package com.example.ClaryFi.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "org_id")
    private UUID orgId;

    @Column(nullable = false, length = 50)
    private String actionType;

    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "JSONB")
    private String metadata;

    private Instant createdAt = Instant.now();
}