package com.techie.auth.server.exception;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(String message, int code, String errorCode) {
        super(message, code, errorCode);
    }
}