package com.example.ClaryFi.infrastructure.adapter.output.persistance.mapper;

import com.example.ClaryFi.domain.model.ApiKey;
import com.example.ClaryFi.infrastructure.adapter.output.persistance.entity.ApiKeyEntity;

import java.time.Instant;
import java.time.LocalDateTime;

public class ApiKeyMapper {
    public static ApiKey toDomain(ApiKeyEntity entity) {
        if (entity == null) return null;
        ApiKey domain = new ApiKey();
        domain.setId(entity.getId().toString());
        domain.setKey(entity.getKey());
        domain.setUserId(entity.getUserId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setRevokedAt( entity.isRevokedAt() );
        return domain;
    }

    public static ApiKeyEntity toEntity(ApiKey domain) {
        if (domain == null) return null;
        ApiKeyEntity entity = new ApiKeyEntity();
        entity.setId( domain.getId() );
        entity.setKey( domain.getKey() );
        entity.setUserId(domain.getUserId());
        entity.setCreatedAt( domain.getCreatedAt() );
        entity.setRevokedAt( domain.getRevokedAt() );
        return entity;
    }
}
