package com.bookatable.data.datasource.datastore;

import android.util.Log;
import com.bookatable.data.db.DatabaseManager;
import com.bookatable.data.entity.CustomerEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

public class DatabaseCustomerEntityStore {

  private static final String LOG_TAG = DatabaseCustomerEntityStore.class.getSimpleName();
  private static final String PERCENT = "%";

  private Dao<CustomerEntity, Integer> mCustomersDao;

  //Search prepared fields, for faster search
  private PreparedQuery<CustomerEntity> mSearchByTitleQuery;
  private SelectArg mSearchByTitleQuerySelectArg;

  @Inject public DatabaseCustomerEntityStore(DatabaseManager databaseManager) {
    mCustomersDao = databaseManager.getCustomersDao();
    prepareSearchByTitleQuery();
  }

  private void prepareSearchByTitleQuery() {
    try {
      QueryBuilder<CustomerEntity, Integer> queryBuilder = mCustomersDao.queryBuilder();
      mSearchByTitleQuerySelectArg = new SelectArg();
      queryBuilder.where().like(CustomerEntity.Fields.FIRST_NAME, mSearchByTitleQuerySelectArg);
      mSearchByTitleQuery = queryBuilder.prepare();
    } catch (SQLException e) {
      Log.wtf(LOG_TAG, "Preparing of SearchByTitleQuery failed", e);
    }
  }

  public Observable<List<CustomerEntity>> queryForAll() {
    return Observable.create(new Observable.OnSubscribe<List<CustomerEntity>>() {
      @Override public void call(Subscriber<? super List<CustomerEntity>> subscriber) {
        try {
          List<CustomerEntity> customers = mCustomersDao.queryForAll();
          subscriber.onNext(customers);
          subscriber.onCompleted();
        } catch (SQLException e) {
          subscriber.onError(e);
        }
      }
    });
  }

  public Observable<Void> saveAll(final Collection<CustomerEntity> entities) {
    return Observable.create(new Observable.OnSubscribe<Void>() {
      @Override public void call(Subscriber<? super Void> subscriber) {
        try {
          saveAllSynchronous(entities);
          subscriber.onNext(null);
          subscriber.onCompleted();
        } catch (SQLException e) {
          subscriber.onError(e);
        }
      }
    });
  }

  public void saveAllSynchronous(final Collection<CustomerEntity> entities) throws SQLException {
    TransactionManager.callInTransaction(mCustomersDao.getConnectionSource(), () -> {
      for (CustomerEntity customerEntity : entities) {
        mCustomersDao.createOrUpdate(customerEntity);
      }
      return null;
    });
  }

  public Observable<CustomerEntity> queryForId(int customerId) {
    return Observable.create(new Observable.OnSubscribe<CustomerEntity>() {
      @Override public void call(Subscriber<? super CustomerEntity> subscriber) {
        try {
          CustomerEntity customer = mCustomersDao.queryForId(customerId);
          subscriber.onNext(customer);
          subscriber.onCompleted();
        } catch (SQLException e) {
          subscriber.onError(e);
        }
      }
    });
  }

  public Observable<List<CustomerEntity>> queryForTitle(String title) {
    return Observable.create(new Observable.OnSubscribe<List<CustomerEntity>>() {
      @Override public void call(Subscriber<? super List<CustomerEntity>> subscriber) {
        try {
          mSearchByTitleQuerySelectArg.setValue(PERCENT + title + PERCENT);
          List<CustomerEntity> customers = mCustomersDao.query(mSearchByTitleQuery);
          subscriber.onNext(customers);
          subscriber.onCompleted();
        } catch (SQLException e) {
          subscriber.onError(e);
        }
      }
    });
  }
}
