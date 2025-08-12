package com.example.ClaryFi.infrastructure.adapter.output.customizedexception.data.response;

import java.time.LocalDateTime;
import java.util.List;

public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private List<String> details;

    public ExceptionResponse(LocalDateTime timestamp, String message, List<String> details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // getters and setters
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<String> getDetails() { return details; }
    public void setDetails(List<String> details) { this.details = details; }
}
