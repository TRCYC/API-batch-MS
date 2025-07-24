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
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+tempDate);
          if (tempDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
        tempDate = tempDate + "T00:00:00Z"; // Set time to midnight UTC
    }
    ZonedDateTime zdt = ZonedDateTime.parse(tempDate, DateTimeFormatter.ISO_DATE_TIME);
    return String.valueOf(zdt.toInstant().toEpochMilli());

    }

}
