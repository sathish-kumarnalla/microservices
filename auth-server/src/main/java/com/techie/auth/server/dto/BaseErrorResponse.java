package com.techie.auth.server.dto;

import java.time.LocalDateTime;



import java.time.LocalDateTime;

public interface BaseErrorResponse {
    String path();
    String errorCode();
    String error();
    int status();
    LocalDateTime timestamp();
}
