package com.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class GeneralDateTools {
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

    /**
     * @param startDate : date utils
     * @param endDate   : date utils
     * @param unit      : MILLISECONDS - HOURS - SECONDS - DAYS - YEAR
     * @return long value and 0 if none @param unit
     */
    public static Long getDateDiff(Date startDate, Date endDate, String unit) {
        long diffInDays = endDate.getTime() - startDate.getTime();
        switch (unit) {
            case "MILLISECONDS":
                return TimeUnit.MILLISECONDS.convert(diffInDays, TimeUnit.MILLISECONDS);
            case "HOURS":
                return TimeUnit.HOURS.convert(diffInDays, TimeUnit.MILLISECONDS);
            case "SECONDS":
                long diffInMillie = Math.abs(diffInDays);
                return TimeUnit.SECONDS.convert(diffInMillie, TimeUnit.MILLISECONDS);
            case "DAYS":
                String endDateChange = new SimpleDateFormat("dd/M/yyyy").format(endDate) + " 23:59:59";
                Date endDateNew = endDate;
                try {
                    endDateNew = new SimpleDateFormat("dd/M/yyyy hh:mm:ss").parse(endDateChange);
                } catch (ParseException e) {
                    return 0L;
                }
                return ChronoUnit.DAYS.between(startDate.toInstant(), endDateNew.toInstant());
            case "YEAR":
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(endDate);
                return (long) (cal2.get(Calendar.YEAR) - cal.get(Calendar.YEAR));
            default:
                return 0L;
        }
    }
}
