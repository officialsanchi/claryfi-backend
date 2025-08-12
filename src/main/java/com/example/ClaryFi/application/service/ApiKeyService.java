package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.input.ApiKeyManagementUseCase;
import com.example.ClaryFi.application.port.output.ApiKeyRepositoryPort;
import com.example.ClaryFi.domain.model.ApiKey;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApiKeyService implements ApiKeyManagementUseCase {
    private final ApiKeyRepositoryPort apiKeyRepositoryPort;

    public ApiKeyService(ApiKeyRepositoryPort apiKeyRepositoryPort) {
        this.apiKeyRepositoryPort = apiKeyRepositoryPort;
    }
    @Override
    public String generateApiKey(String userId) {
        ApiKey apiKey =  new ApiKey();
        apiKey.setId("" );
        apiKey.setKey(generateSecureKey());
        apiKey.setRevokedAt(false);
        apiKey.setCreatedAt( apiKey.getCreatedAt() );
        apiKeyRepositoryPort.save(apiKey);
        return "";
    }

    @Override
    public void revokeApiKey(String apiKeyId) {
        apiKeyRepositoryPort.revoke(apiKeyId);

    }

    @Override
    public List<ApiKey> listApiKeys(String userId) {
        return apiKeyRepositoryPort.findByUserId(userId);
    }
    private String generateSecureKey() {

        return java.util.UUID.randomUUID().toString();
    }
}
