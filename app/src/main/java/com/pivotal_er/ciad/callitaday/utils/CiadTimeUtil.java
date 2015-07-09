package com.pivotal_er.ciad.callitaday.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

public class CiadTimeUtil {

    private static Map<String, DateTimeFormatter> formatterMap = null;

    static {
        formatterMap = new HashMap<>();
    }

    public static String formatDate(DateTime dateTime, String format) {
        if(formatterMap.containsKey(format)) {
            return formatterMap.get(format).print(dateTime);
        }
        else {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
            formatterMap.put(format, formatter);

            return formatter.print(dateTime);
        }
    }
}
