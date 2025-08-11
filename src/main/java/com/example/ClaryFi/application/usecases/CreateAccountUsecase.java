package com.example.ClaryFi.application.usecases;

import com.example.ClaryFi.api.dtos.SignupRequest;
import com.example.ClaryFi.api.dtos.SignupResponse;
import com.example.ClaryFi.domain.model.Organization;
import com.example.ClaryFi.domain.model.User;
import com.example.ClaryFi.domain.repositories.OrganizationRepository;
import com.example.ClaryFi.domain.repositories.UserRepository;
import com.example.ClaryFi.infrastructure.authentication.service.AuthService;
import com.example.ClaryFi.utils.roles.Environment;
import com.example.ClaryFi.utils.roles.KycStatus;
import com.example.ClaryFi.utils.roles.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountUsecase {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final OrganizationRepository orgRepository;

    public CreateAccountUsecase(AuthService authService, UserRepository userRepository, OrganizationRepository orgRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.orgRepository = orgRepository;
    }

    public SignupResponse execute(SignupRequest request) {
        // Validate request (e.g., password strength)
        String keycloakUserId = authService.createUser(request);

        // Create org
        Organization org = new Organization();
        org.setName(request.getBusinessName());
        org.setEnvironment(Environment.SANDBOX);
        org.setKycStatus(KycStatus.NOT_STARTED);
        org = orgRepository.save(org);

        // Create user linked to org
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        // passwordHash not stored here; Keycloak handles
        user.setStatus(Status.PENDING_VERIFICATION);
        user.setOrganization(org);
        userRepository.save(user);

        // Generate sandbox API keys (separate use case, but trigger here)
        // ...

        return new SignupResponse("Account created successfully. Please verify your email.", keycloakUserId, "pending_verification");
    }
}