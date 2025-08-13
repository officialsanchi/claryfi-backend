package com.example.ClaryFi.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "api_keys")
@Data
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Environment environment;

    @Column(unique = true, nullable = false, length = 255)
    private String publicKey;

    @Column(unique = true, nullable = false, length = 255)
    private String secretKey;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('active', 'revoked') default 'active'")
    private KeyStatus status = KeyStatus.ACTIVE;
    private Instant createdAt = Instant.now();
}