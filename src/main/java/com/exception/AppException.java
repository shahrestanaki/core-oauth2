package com.exception;

import com.tools.GetResourceBundle;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashSet;

public class AppException extends RuntimeException {

    private static final long serialVersionUID = -7806029002430564887L;
    private String message;
    private HttpStatus httpStatus;

    public AppException(String message) {
        this.message = translate(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public AppException(String message, HttpStatus httpStatus) {
        this.message = translate(message);
        this.httpStatus = httpStatus;
    }

    public AppException(String message, LinkedHashSet<String> params, HttpStatus httpStatus) {
        this.message = translate(message);
        int i = 1;
        for (String element : params) {
            this.message = this.message.replace("@" + i, element);
            i++;
        }
        this.httpStatus = httpStatus != null ? httpStatus : HttpStatus.BAD_REQUEST;
    }

    public String translate(String source) {
        try {
            return GetResourceBundle.getMessage.getString(source);
        } catch (NoClassDefFoundError | Exception e) {
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