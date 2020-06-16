package com.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    @Autowired
    private Environment env;

    private static final long serialVersionUID = -7806029002430564887L;
    private String message;
    private HttpStatus httpStatus;

    public AppException(String message) {
        this.message = translate(message);
    }

    public AppException(String message, HttpStatus httpStatus) {
        this.message = translate(message);
        this.httpStatus = httpStatus;
    }

    public String translate(String source) {
        try {
            return env.getProperty(source);
        } catch (Exception e) {
            return source;
        }

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