package com.example.ClaryFi.utils.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(Exception ex) {
        ErrorResponse error = new ErrorResponse("VALIDATION_ERROR", ex.getMessage(), Map.of());
        return ResponseEntity.badRequest().body(error);
    }
    // Add for AUTH_INVALID_CREDENTIALS, etc.
}
