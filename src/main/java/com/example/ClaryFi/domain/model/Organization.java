package com.example.ClaryFi.domain.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "organizations")
@Data
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 150) // Optional, as not all orgs may have an email
    private String email;

    @Column(length = 100) // Optional, for website URL
    private String website;

    @Column(length = 50) // Optional, for phone number
    private String phoneNumber;

    @Column(length = 100) // Optional, for industry type
    private String industry;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('sandbox', 'live') default 'sandbox'")
    private Environment environment = Environment.SANDBOX;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('not_started', 'pending', 'approved', 'rejected') default 'not_started'")
    private KycStatus kycStatus = KycStatus.NOT_STARTED;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
