package com.fakestoreapi.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



// Utility class for handling date formatting and parsing.

public final class DateUtil {

    private DateUtil() {
        // Prevent instantiation
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null || pattern == null) {
            throw new IllegalArgumentException("Date and pattern must not be null");
        }
        return new SimpleDateFormat(pattern).format(date);
    }


    public static Date parseDate(String dateStr, String pattern) throws ParseException {
        if (dateStr == null || pattern == null) {
            throw new IllegalArgumentException("Date string and pattern must not be null");
        }
        return new SimpleDateFormat(pattern).parse(dateStr);
    }


    public static String getCurrentDate(String pattern) {
        return formatDate(new Date(), pattern);
    }
}
