package com.pivotal_er.ciad.callitaday.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pivotal_er.ciad.callitaday.model.TabRecord;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ciad.db";
    private static final int DATABASE_VERSION = 5;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.i(this.getClass().getName(), "DB Helper onCreate");
        try {
            TableUtils.createTable(connectionSource, TabRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.i(this.getClass().getName(), "DB Helper onUpgrade");

        if(oldVersion != newVersion) {
            try {
                TableUtils.dropTable(connectionSource, TabRecord.class, true);
                onCreate(database, connectionSource);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
