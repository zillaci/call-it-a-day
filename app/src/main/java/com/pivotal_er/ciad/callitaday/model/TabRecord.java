package com.pivotal_er.ciad.callitaday.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tab_record")
public class TabRecord {

    @DatabaseField(id = true)
    private Long timeMillis;

    @DatabaseField
    private String date;

    public TabRecord() {}
    public TabRecord(String date, Long timeMillis) {
        this.date = date;
        this.timeMillis = timeMillis;
    }

    public void setTimeMillis(Long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimeMillis() {
        return this.timeMillis;
    }

    public String getDate() {
        return this.date;
    }
}
