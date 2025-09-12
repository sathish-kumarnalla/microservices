package com.techie.gateway.exception;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponseDTO {
    private Instant timestamp;
    private String message;
    private String traceId;

    // Getters and setters
}