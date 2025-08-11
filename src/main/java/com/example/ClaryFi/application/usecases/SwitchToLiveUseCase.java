package com.example.ClaryFi.application.usecases;

import com.example.ClaryFi.domain.model.Organization;
import com.example.ClaryFi.domain.repositories.OrganizationRepository;
import com.example.ClaryFi.infrastructure.authentication.service.KycService;
import com.example.ClaryFi.utils.roles.Environment;
import com.example.ClaryFi.utils.roles.KycStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SwitchToLiveUseCase {
    private final OrganizationRepository orgRepository;
    private final KycService kycService; // Assume for verification

    public SwitchToLiveUseCase(OrganizationRepository orgRepository, KycService kycService) {
        this.orgRepository = orgRepository;
        this.kycService = kycService;
    }

    public void execute(String orgId) {
        Organization org = orgRepository.findById(UUID.fromString(orgId));
        if (org.getKycStatus() != KycStatus.APPROVED) {
            throw new RuntimeException("KYC_REQUIRED");
        }
        org.setEnvironment(Environment.LIVE);
        orgRepository.save(org);
        // Generate live API keys
        // Notify via email
    }
}