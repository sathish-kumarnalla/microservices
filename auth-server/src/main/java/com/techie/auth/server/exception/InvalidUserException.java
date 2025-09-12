package com.techie.auth.server.exception;


public class InvalidUserException extends ApiException {

    public InvalidUserException(String message, int statusCode, String errorCode) {
        super(message, statusCode, errorCode);
    }
}