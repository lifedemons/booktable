package com.customerviewer.data.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.customerviewer.data.entity.CustomerEntity;

import java.sql.SQLException;
import javax.inject.Inject;

public class DatabaseManager {

    public static final String LOG_TAG_DB = "Customers:Database";
    public static final String DEFAULT_DATABASE_NAME = "customer_viewer";

    private final ConnectionSource mConnectionSource;

    //DAOs
    private Dao<CustomerEntity, Integer> mCustomersDao;

    @Inject
    public DatabaseManager(Context context) {
        this(context, DEFAULT_DATABASE_NAME);
    }

    public DatabaseManager(Context context, String databaseName) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context, databaseName);
        mConnectionSource = databaseHelper.getConnectionSource(); // force database creation/upgrade eagerly
    }

    public Dao<CustomerEntity, Integer> getCustomersDao() {
        if (mCustomersDao == null) {
            createCustomerEntityDao();
        }

        return mCustomersDao;
    }

    private void createCustomerEntityDao() {
        try {
            mCustomersDao = DaoManager.createDao(mConnectionSource, CustomerEntity.class);
            mCustomersDao.setObjectCache(true);
        } catch (SQLException e) {
            Log.wtf(LOG_TAG_DB, "Creation of Dao<" + CustomerEntity.class + "> failed", e);
        }
    }
}
