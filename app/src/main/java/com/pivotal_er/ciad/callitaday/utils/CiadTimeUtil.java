package com.pivotal_er.ciad.callitaday.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormatter;

import java.util.Map;

public class CiadTimeUtil {

    private static final int REGULAR_WORK_HOURS = 8;
    private static final int LEAST_WORK_HOURS = 4;
    private static final int BREAK_TIME_MINUTES = 30;
    private static final int REGULAR_WEEK_WORK_HOURS = 40;
    private static Map<String, DateTimeFormatter> formatterMap = null;

    private static final String YEAR = "년";
    private static final String MONTH = "월";
    private static final String NTH_WEEK = "주차";
    private static final String HOURS_ABBR = "h";
    private static final String MINUTES_ABBR = "m";


    public static String millisToHourAndMinute(long millis) {
        long hours = (millis / 1000) / 3600;
        long minutes = ((millis / 1000) % 3600) / 60;

        return hours + HOURS_ABBR + " " + minutes + MINUTES_ABBR;
    }

    public static long calcWorkMillisWithBreak(long workMillis) {
        final long leastWorkTimeMills = LEAST_WORK_HOURS * 3600 * 1000 ;
        final long regularWorkTimeMillis = REGULAR_WORK_HOURS * 3600 * 1000;
        final long unitBreakTimeMillis = BREAK_TIME_MINUTES * 60 * 1000;

        if(workMillis > leastWorkTimeMills && workMillis < regularWorkTimeMillis) {
            workMillis -= unitBreakTimeMillis;
        }
        else if(workMillis >= regularWorkTimeMillis) {
            workMillis -= 2*unitBreakTimeMillis;
        }

        return workMillis;
    }

    public static DateTime getWeekStartDate(DateTime dateTime) {
        return dateTime.withDayOfWeek(DateTimeConstants.MONDAY);
    }

    public static DateTime getWeekEndDate(DateTime dateTime) {
        return dateTime.withDayOfWeek(DateTimeConstants.FRIDAY);
    }

    public static long getMillis(String date, int hourOfDay, int minuteOfHour) {
        final int year = Integer.parseInt(date.substring(0, 4));
        final int monthOfYear = Integer.parseInt(date.substring(4, 6));
        final int dayOfMonth = Integer.parseInt(date.substring(6, 8));
        return new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, 0).getMillis();
    }

    public static String getWeekInfo(DateTime dateTime) {
        return dateTime.getYear() + YEAR + " " + dateTime.getMonthOfYear() + MONTH + " " + dateTime.getDayOfMonth() % 7 + NTH_WEEK;
    }
}
