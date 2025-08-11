package com.example.ClaryFi.application.usecases;

import com.example.ClaryFi.domain.repositories.OrganizationRepository;
import com.example.ClaryFi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationRepository organizationRepository;
    private final EmailSenderPort emailSenderPort;
}
