package com.example.ClaryFi.infrastructure.adapter.input.eventlistener;

import com.example.ClaryFi.domain.exception.PasswordPolicyViolationException;

import java.util.regex.Pattern;

public class PasswordPolicyValidator {
    private static final Pattern PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{10,}$");

    public static void validate(String password) {
        if (password == null || !PATTERN.matcher(password).matches()) {
            throw new PasswordPolicyViolationException(
                    "Password must be at least 10 characters, contain upper/lower/digit and special char");
        }
    }
}
