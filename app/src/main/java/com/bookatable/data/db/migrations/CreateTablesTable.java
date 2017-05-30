package com.bookatable.data.db.migrations;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.bookatable.data.db.DatabaseManager;
import com.bookatable.data.entity.Table;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class CreateTablesTable extends Migration {
  @Override public void up(SQLiteDatabase db, ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, Table.class);
    } catch (SQLException e) {
      Log.wtf(DatabaseManager.LOG_TAG_DB, "Creation of TablesTable failed", e);
    }
  }
}
