package com.example.ClaryFi.domain.model;

import com.example.ClaryFi.utils.roles.Role;
import com.example.ClaryFi.utils.roles.Status;
import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ADMIN;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING_VERIFICATION;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

}
