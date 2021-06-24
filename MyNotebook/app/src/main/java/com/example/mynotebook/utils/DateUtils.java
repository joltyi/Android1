package com.example.mynotebook.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String dateToString(LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER);
    }
}
