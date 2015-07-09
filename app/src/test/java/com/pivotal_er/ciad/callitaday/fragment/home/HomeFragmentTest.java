package com.pivotal_er.ciad.callitaday.fragment.home;

import com.pivotal_er.ciad.callitaday.fragments.HomeFragment;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

public class HomeFragmentTest {

    HomeFragment fragment = HomeFragment.newInstance();

    @Test
    public void testCalcWorkAmount() {
        final int HOURS = 3;
        final int MINUTES = 21;

        DateTime start = new DateTime();

        DateTime end = new DateTime(start);
        end = end.plusHours(HOURS).plusMinutes(MINUTES);

        String timeAmount = fragment.calcTimeAmount(start.getMillis(), end.getMillis());

        Assert.assertEquals(HOURS + "h " + MINUTES + "m", timeAmount);
    }
}
