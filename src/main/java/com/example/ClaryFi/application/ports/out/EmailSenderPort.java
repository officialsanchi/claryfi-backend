package com.example.ClaryFi.application.ports.out;

import com.example.ClaryFi.domain.model.ConfirmationToken;
import com.example.ClaryFi.domain.model.User;

public interface EmailSenderPort {
    void sendEmail(User user, ConfirmationToken confirmationToken);
}
