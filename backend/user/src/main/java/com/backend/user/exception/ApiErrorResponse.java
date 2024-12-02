package com.backend.user.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

// represents an error response structure to be returned in API responses

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private int status;
    private String error;
    private String message;
    private String timestamp = Instant.now().toString();
}
