package com.bookatable.data.db;

import android.content.Context;
import android.util.Log;
import com.bookatable.data.entity.Customer;
import com.bookatable.data.entity.Table;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import javax.inject.Inject;

public class DatabaseManager {

  public static final String LOG_TAG_DB = "Customers:Database";
  public static final String DEFAULT_DATABASE_NAME = "book_a_table";

  private final ConnectionSource mConnectionSource;

  //DAOs
  private Dao<Customer, Integer> mCustomersDao;
  private Dao<Table, Integer> mTableDao;

  @Inject public DatabaseManager(Context context) {
    this(context, DEFAULT_DATABASE_NAME);
  }

  public DatabaseManager(Context context, String databaseName) {
    DatabaseHelper databaseHelper = new DatabaseHelper(context, databaseName);
    mConnectionSource =
        databaseHelper.getConnectionSource(); // force database creation/upgrade eagerly
  }

  public Dao<Customer, Integer> getCustomersDao() {
    if (mCustomersDao == null) {
      createCustomerEntityDao();
    }

    return mCustomersDao;
  }

  private void createCustomerEntityDao() {
    try {
      mCustomersDao = DaoManager.createDao(mConnectionSource, Customer.class);
      mCustomersDao.setObjectCache(true);
    } catch (SQLException e) {
      Log.wtf(LOG_TAG_DB, "Creation of Dao<" + Customer.class + "> failed", e);
    }
  }

  public Dao<Table, Integer> getTablesDao() {
    if (mTableDao == null) {
      createTableEntityDao();
    }

    return mTableDao;
  }

  private void createTableEntityDao() {
    try {
      mTableDao = DaoManager.createDao(mConnectionSource, Table.class);
      mCustomersDao.setObjectCache(true);
    } catch (SQLException e) {
      Log.wtf(LOG_TAG_DB, "Creation of Dao<" + Table.class + "> failed", e);
    }
  }
}
