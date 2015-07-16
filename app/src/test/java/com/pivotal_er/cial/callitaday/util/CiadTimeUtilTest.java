package com.pivotal_er.cial.callitaday.util;

import com.pivotal_er.ciad.callitaday.utils.CiadTimeUtil;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

public class CiadTimeUtilTest {

    @Test
    public void calcWorkTimeTest() {
        final int HOURS_A = 6;
        final int MINUTES_A = 42;

        DateTime start = new DateTime();
        DateTime end = start.plusHours(HOURS_A).plusMinutes(MINUTES_A);

        long amount = CiadTimeUtil.calcWorkMillisWithBreak(end.getMillis() - start.getMillis());

        Assert.assertEquals("6h 12m", CiadTimeUtil.millisToHourAndMinute(amount));

        final int HOURS_B = 9;
        final int MINUTES_B = 42;

        end = start.plusHours(HOURS_B).plusMinutes(MINUTES_B);

        amount = CiadTimeUtil.calcWorkMillisWithBreak(end.getMillis() - start.getMillis());

        Assert.assertEquals("8h 42m", CiadTimeUtil.millisToHourAndMinute(amount));
    }

    @Test
    public void getWeekStartDateTest() {
        DateTime dateTime = new DateTime(2015, 7, 10, 12, 30);

        DateTime weekStartDate = CiadTimeUtil.getWeekStartDate(dateTime);
        DateTime weekEndDate = CiadTimeUtil.getWeekEndDate(dateTime);


        String startDateString = weekStartDate.toString("yyyyMMdd");
        String endDateString = weekEndDate.toString("yyyyMMdd");

        Assert.assertEquals("20150706", startDateString);
        Assert.assertEquals("20150710", endDateString);
    }

    @Test
    public void checkMyWorkTime() {
        DateTime dateTime = new DateTime(2015, 7, 10, 9, 58);
        DateTime now = DateTime.now();

        long amount = CiadTimeUtil.calcWorkMillisWithBreak(now.getMillis() - dateTime.getMillis());

        System.out.println(CiadTimeUtil.millisToHourAndMinute(amount));

    }
}
