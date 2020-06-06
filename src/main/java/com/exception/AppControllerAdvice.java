package com.exception;

import com.tools.CorrectDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppControllerAdvice {
    /*@ExceptionHandler(AppException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppResponse handleSecurityException(SecurityException se) {
        System.out.println("************* handleSecurityException **************");
        AppResponse response = new AppResponse(se.getMessage());
        return response;
    }*/
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> AppException(AppException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(CorrectDate.dateTimeZone(new Date()), ex.getMessage(), request.getDescription(false), ErrorDetails.TYPE.App.getValue());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(CorrectDate.dateTimeZone(new Date()), ex.getMessage(), request.getDescription(false), ErrorDetails.TYPE.App.getValue());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
