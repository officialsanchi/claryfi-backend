package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.output.EmailPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleEmailServiceAdapter implements EmailPort {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleEmailServiceAdapter.class);

    @Override
    public void sendEmail(String to, String subject, String body) {
        // For now just log the email instead of sending
        logger.info("Sending email to: {}\nSubject: {}\nBody: {}", to, subject, body);
    }
}
