package com.example.ClaryFi.domain.model;

import com.example.ClaryFi.utils.roles.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "kyc_documents")
@Data
public class KycDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    @Column(nullable = false, length = 50)
    private String documentType;
    @Column(nullable = false)
    private String documentUrl;
    @Enumerated(EnumType.STRING)
    private Status verificationStatus = Status.PENDING_VERIFICATION;
    private LocalDateTime uploadedAt;
    private LocalDateTime verifiedAt;
}