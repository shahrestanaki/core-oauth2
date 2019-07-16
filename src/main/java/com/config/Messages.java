package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Messages {

    @Autowired
    private MessageSource messageSource;

    public String messageFa(String message) {
        message = messageSource.getMessage(message, null, new Locale("fa", "IR"));
        return message;
    }

    public String message(String message) {
        message = messageSource.getMessage(message, null, Locale.ENGLISH);
        return message;
    }

}