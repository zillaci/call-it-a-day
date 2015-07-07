package com.pivotal_er.ciad.callitaday.enums;

import java.util.HashMap;
import java.util.Map;

public enum FragmentPage {
    HOME(0), ADJUST(1), VACATION(2), PLANS(3), STATISTICS(4), POLICY(5), SETTINGS(6);

    private int mOrder;

    private static Map<Integer, FragmentPage> sMap = new HashMap<Integer, FragmentPage>();

    static {
        for(FragmentPage page: FragmentPage.values()) {
            sMap.put(page.mOrder, page);
        }
    }

    FragmentPage(final int order) {
        this.mOrder = order;
    }

    public int getOrder() {
        return this.mOrder;
    }

    public static FragmentPage fromPosition(final int position) {
        return sMap.get(position);
    }
}
