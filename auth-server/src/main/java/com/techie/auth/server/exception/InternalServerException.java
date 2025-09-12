package com.techie.auth.server.exception;

public class InternalServerException extends ApiException {
    public InternalServerException(String message, int statusCode, String errorCode) {
        super(message, statusCode, errorCode);
    }
}
