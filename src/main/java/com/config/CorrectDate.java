package com.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CorrectDate {
    public static String dateTimeZone(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("GMT"));
        return f.format(date) ;
    }
}
