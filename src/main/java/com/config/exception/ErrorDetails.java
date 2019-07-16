package com.config.exception;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class ErrorDetails {
    private String timestamp;
    private String message;
    private String details;
    private int type;

    public enum TYPE {
        Sys(0), App(1);
        private final int type;

        TYPE(int type) {
            this.type = type;
        }

        public int getValue() {
            return type;
        }
    }

    public ErrorDetails(String timestamp, String message, String details, int type) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.type = type;
    }

    public ErrorDetails(String message, String details, int type) {
        Date date = new Date();
        this.timestamp = date.toString();
        this.message = message;
        this.details = details;
        this.type = type;
    }

    public byte[] restResponseBytes(ErrorDetails eErrorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
        return serialized.getBytes();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getType() {
        return type;
    }


}