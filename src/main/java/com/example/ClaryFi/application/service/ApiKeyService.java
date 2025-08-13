package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.input.ApiKeyManagementUseCase;
import com.example.ClaryFi.application.port.input.AuditLoggingUseCase;
import com.example.ClaryFi.application.port.output.ApiKeyRepositoryPort;
import com.example.ClaryFi.domain.model.ApiKey;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ApiKeyService implements ApiKeyManagementUseCase {
    private final ApiKeyRepositoryPort apiKeyRepositoryPort;
    private final AuditLoggingUseCase auditLoggingUseCase;

    public ApiKeyService(ApiKeyRepositoryPort apiKeyRepositoryPort, AuditLoggingUseCase auditLoggingUseCase) {
        this.apiKeyRepositoryPort = apiKeyRepositoryPort;
        this.auditLoggingUseCase = auditLoggingUseCase;
    }

    @Override
    public String generateApiKey(String userId) {
        ApiKey apiKey =  new ApiKey();
        // set id as UUID
        apiKey.setId(UUID.randomUUID().toString());
        apiKey.setUserId(userId);
        apiKey.setKey(generateSecureKey());
        apiKey.setRevokedAt( false );
        apiKey.setCreatedAt( Instant.now());
        apiKeyRepositoryPort.save(apiKey);

        auditLoggingUseCase.logEvent("API key created for userId=" + userId + " apiKeyId=" + apiKey.getId());

        return apiKey.getKey();
    }

    @Override
    public void revokeApiKey(String apiKeyId) {
        apiKeyRepositoryPort.revoke(apiKeyId);
        auditLoggingUseCase.logEvent("API key revoked apiKeyId=" + apiKeyId);
    }

    @Override
    public List<ApiKey> listApiKeys(String userId) {
        return apiKeyRepositoryPort.findByUserId(userId);
    }

    private String generateSecureKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
