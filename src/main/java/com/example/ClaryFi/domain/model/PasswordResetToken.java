package com.example.ClaryFi.domain.model;

import java.time.LocalDateTime;

public class PasswordResetToken {

        private String email;
        private String token;
        private LocalDateTime expiryDate;

        public PasswordResetToken(String email, String token, LocalDateTime expiryDate) {
            this.email = email;
            this.token = token;
            this.expiryDate = expiryDate;
        }

        // Getters and setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public LocalDateTime getExpiryDate() { return expiryDate; }
        public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
}
