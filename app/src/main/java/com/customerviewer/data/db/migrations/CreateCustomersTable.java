package com.customerviewer.data.db.migrations;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.customerviewer.data.entity.CustomerEntity;
import com.customerviewer.data.db.DatabaseManager;

import java.sql.SQLException;

public class CreateCustomersTable extends Migration {
    @Override
    public void up(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CustomerEntity.class);
        } catch (SQLException e) {
            Log.wtf(DatabaseManager.LOG_TAG_DB, "Creation of CustomersTable failed", e);
        }
    }
}
