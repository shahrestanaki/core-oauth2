package com.tools;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class GetResourceBundle {
    public static ResourceBundle getApplication = ResourceBundle.getBundle("application",new Locale("en_us"));
    public static ResourceBundle getConfig = ResourceBundle.getBundle("config",new Locale("en_us"));
    public static ResourceBundle getMessage = ResourceBundle.getBundle("messages",new Locale("en_us"));
}
