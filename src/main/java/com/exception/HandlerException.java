package com.exception;

import com.enump.ErrorEnum;
import com.tools.CorrectDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {
    @Autowired
    MessageSource messageSource;


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error(((ServletWebRequest) request).getRequest().getRequestURI());
        logger.error(ex.toString());
        Map<String, String> params = new HashMap<>();
        params.put("core_code", ErrorEnum.General.toString());
        params.put("timestamp", new Date().toString());
        params.put("message", "general.error");
        return new ResponseEntity(params, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppException.class)
    public final ResponseEntity<Object> handleAppException(AppException ex, WebRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("core_code", ErrorEnum.ValidBusiness.toString());
        params.put("timestamp", new Date().toString());
        params.put("message", ex.getMessage());
        return new ResponseEntity(params, ex.getHttpStatus());
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("core_code", ErrorEnum.ValidArg.toString());
        params.put("timestamp", CorrectDate.dateTimeZone(new Date()));
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            params.put("message", error.getDefaultMessage());
            params.put("field", error.getField());
        });
        return new ResponseEntity(params, HttpStatus.BAD_REQUEST);
    }
}
