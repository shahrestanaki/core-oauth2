package com.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = -7806029002430564887L;
    private String message;
    private HttpStatus httpStatus;

    public AppException(String message ) {
        this.message = message;
    }

    public AppException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}