package com.view;

import org.springframework.http.HttpStatus;

public class UserGeneralResponse extends Response{

    UserGeneralResponse(){

    }
    public UserGeneralResponse(HttpStatus result){
        super.setResult(result);
    }
}
