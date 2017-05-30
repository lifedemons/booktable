package com.bookatable.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.bookatable.data.db.migrations.CreateCustomersTable;
import com.bookatable.data.db.migrations.CreateTablesTable;
import com.bookatable.data.db.migrations.Migration;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
  private static final Migration[] MIGRATIONS = new Migration[] {
      new CreateCustomersTable(),
      new CreateTablesTable()
  };

  public DatabaseHelper(Context context, String databaseName) {
    super(context, databaseName, null, MIGRATIONS.length);
  }

  @Override
  public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
    for (Migration migration : MIGRATIONS) {
      migration.up(sqLiteDatabase, connectionSource);
    }
  }

  @Override
  public void onUpgrade(@NonNull SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource,
      int oldVersion, int newVersion) {
    for (int i = oldVersion; i < newVersion; i++) {
      MIGRATIONS[i].up(sqLiteDatabase, connectionSource);
    }
  }
}
