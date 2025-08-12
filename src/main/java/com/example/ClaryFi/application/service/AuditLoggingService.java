package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.input.AuditLoggingUseCase;
import com.example.ClaryFi.application.port.output.AuditLogPort;
import org.springframework.stereotype.Service;

@Service
public class AuditLoggingService implements AuditLoggingUseCase {
    private final AuditLogPort auditLogPort;

    public AuditLoggingService(AuditLogPort auditLogPort) {
        this.auditLogPort = auditLogPort;
    }


    @Override
    public void logEvent(String event) {
        auditLogPort.saveLog( event );

    }
}
