package com.techie.auth.server.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message, int code, String errorCode) {
        super(message, code, errorCode);
    }
}