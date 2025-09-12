package com.techie.auth.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ValidationErrorResponse implements BaseErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String errorCode;
    private String path;
    private List<FieldErrorDetail> errors;


    @Override
    public String path() {
        return "";
    }

    @Override
    public String errorCode() {
        return "";
    }

    @Override
    public String error() {
        return "";
    }

    @Override
    public int status() {
        return 0;
    }

    @Override
    public LocalDateTime timestamp() {
        return null;
    }
}