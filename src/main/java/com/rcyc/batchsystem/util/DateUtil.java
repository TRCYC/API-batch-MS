package com.rcyc.batchsystem.util;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtil {

    public static String rubyToJavaForDay(String tempDate) {

        LocalDate date = LocalDate.parse(tempDate, DateTimeFormatter.ISO_DATE);
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }

    public static String rubyToJavaForEpochMilli(String tempDate) {
        ZonedDateTime zdt = ZonedDateTime.parse(tempDate, DateTimeFormatter.ISO_DATE_TIME);
        return String.valueOf(zdt.toInstant().toEpochMilli());

    }

}
