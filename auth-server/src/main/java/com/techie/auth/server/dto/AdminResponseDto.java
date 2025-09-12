package com.techie.auth.server.dto;


import java.time.LocalDateTime;
import java.util.List;

public record AdminResponseDto(
        String status,
        String message,
        String user,
        List<String> roles,
        LocalDateTime timestamp
) {}

