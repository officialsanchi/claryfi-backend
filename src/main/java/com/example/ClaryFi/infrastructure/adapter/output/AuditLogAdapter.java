package com.example.ClaryFi.infrastructure.adapter.output;

import com.example.ClaryFi.application.port.output.AuditLogPort;
import org.springframework.stereotype.Repository;

@Repository
public class AuditLogAdapter implements AuditLogPort {
    @Override
    public void saveLog(String message) {

    }
}
