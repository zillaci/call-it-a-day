package com.pivotal_er.ciad.callitaday.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "day_summary")
public class DaySummary {

    @DatabaseField(id = true)
    private String date;

    @DatabaseField
    private long startMillis;

    @DatabaseField
    private long endMillis;

    @DatabaseField
    private long amountMillis;

    public DaySummary() {}

    public DaySummary(String date, long startMillis, long endMillis, long amountMillis) {
        this.date = date;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.amountMillis = amountMillis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
    }

    public long getAmountMillis() {
        return amountMillis;
    }

    public void setAmountMillis(long amountMillis) {
        this.amountMillis = amountMillis;
    }

    public boolean isValidWork() {
        return startMillis != 0 && endMillis != 0;
    }
}
