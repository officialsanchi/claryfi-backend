package com.example.ClaryFi.application.port.output;

import org.springframework.stereotype.Component;

@Component
public interface EmailPort {
    void sendEmail(String to, String subject, String body);
}
