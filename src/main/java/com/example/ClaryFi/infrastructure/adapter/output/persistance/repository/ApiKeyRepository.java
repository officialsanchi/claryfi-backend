package com.example.ClaryFi.infrastructure.adapter.output.persistance.repository;

import com.example.ClaryFi.infrastructure.adapter.output.persistance.entity.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, String> {
}
