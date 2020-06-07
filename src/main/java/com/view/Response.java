package com.view;

import org.springframework.http.HttpStatus;

public class Response {

    private HttpStatus result;

    public HttpStatus getResult() {
        return result;
    }

    public void setResult(HttpStatus result) {
        this.result = result;
    }
}
