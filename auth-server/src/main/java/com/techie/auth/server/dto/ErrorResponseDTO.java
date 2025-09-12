package com.techie.auth.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
public record ErrorResponseDTO(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime timestamp,
        int status, String error, String errorCode, String path) implements BaseErrorResponse {

    // Optional: static factory method for convenience
    public static ErrorResponseDTO of(int status, String error, String errorCode, String path) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .error(error)
                .errorCode(errorCode)
                .path(path)
                .build();
    }
}