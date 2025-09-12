package com.techie.auth.server.exception;

public class UserInfoException extends ApiException {

    public UserInfoException(String message, int statusCode, String errorCode) {
        super(message, statusCode, errorCode);
    }

    public UserInfoException(String message, String errorCode) {
        super(message, errorCode);

    }
}
