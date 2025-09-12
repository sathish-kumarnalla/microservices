package com.techie.auth.server.exception;

import com.techie.auth.server.dto.FieldErrorDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends ApiException {

    private final List<FieldErrorDetail> fieldErrors;

    public ValidationException(String message, String errorCode, List<FieldErrorDetail> fieldErrors) {
        super(message, errorCode);
        this.fieldErrors = fieldErrors;
    }
}