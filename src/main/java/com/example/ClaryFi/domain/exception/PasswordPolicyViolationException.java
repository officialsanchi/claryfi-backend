package com.example.ClaryFi.domain.exception;

public class PasswordPolicyViolationException extends RuntimeException {
    public PasswordPolicyViolationException(String message) {
        super( message );
    }
}
