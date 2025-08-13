package com.example.ClaryFi.infrastructure.adapter.output.customizedexception;

import com.example.ClaryFi.domain.exception.PasswordPolicyViolationException;
import com.example.ClaryFi.domain.exception.UserNotFoundException;
import com.example.ClaryFi.infrastructure.adapter.output.customizedexception.data.response.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(),
                ex.getMessage(),
                List.of(request.getDescription(false)));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordPolicyViolationException.class)
    public final ResponseEntity<Object> handlePasswordPolicy(PasswordPolicyViolationException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(),
                ex.getMessage(),
                List.of(request.getDescription(false)));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse( LocalDateTime.now(),
                ex.getMessage(),
                List.of(request.getDescription(false)));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err -> {
                    String msg = err.getDefaultMessage();
                    return msg != null ? msg : err.toString();
                })
                .collect( Collectors.toList());

        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), "Validation Failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
