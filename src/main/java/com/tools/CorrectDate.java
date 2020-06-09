package com.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CorrectDate {
    public static String dateTimeZone(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("GMT"));
        return f.format(date);
    }

    public static Date addToDate(Date date, int amount, int unit) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(unit, amount);
        return c.getTime();
    }
}
