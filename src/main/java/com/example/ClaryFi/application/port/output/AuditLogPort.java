package com.example.ClaryFi.application.port.output;


public interface AuditLogPort {
    void saveLog(String message);
}
