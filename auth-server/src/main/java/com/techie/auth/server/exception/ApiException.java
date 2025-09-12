package com.techie.auth.server.exception;



import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {

    private final String errorCode;
    private final int statusCode;

    public ApiException(String message, int statusCode, String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public ApiException(String message, String errorCode) {
        super(message);
        this.statusCode = 500; // Default to Internal Server Error
        this.errorCode = errorCode;
    }
}