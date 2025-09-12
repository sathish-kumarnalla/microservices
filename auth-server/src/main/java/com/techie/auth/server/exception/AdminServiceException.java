package com.techie.auth.server.exception;

public class AdminServiceException extends ApiException{
    public AdminServiceException(String message, int code, String errorCode) {
        super(message, code, errorCode);
    }
}
