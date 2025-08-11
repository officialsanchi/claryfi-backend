package com.example.ClaryFi.utils.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(Exception ex) {
        ErrorResponse error = new ErrorResponseException(HttpStatus.BAD_REQUEST, ex);
        return ResponseEntity.badRequest().body(error);
    }
    // Add for AUTH_INVALID_CREDENTIALS, etc.
}
