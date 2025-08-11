package com.example.ClaryFi.domain.repositories;

import com.example.ClaryFi.domain.model.Organization;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface OrganizationRepository {
    Organization save(Organization org);
    Organization findById(UUID id);
}
