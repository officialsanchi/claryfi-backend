package com.example.ClaryFi.application.port.output;

public interface EmailPort {
    void sendEmail(String to, String subject, String body);
}
