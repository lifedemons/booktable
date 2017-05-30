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
import rx.Completable;
import rx.Single;

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

  public Single<List<CustomerEntity>> queryForAll() {
    return Single.fromCallable(() -> mCustomersDao.queryForAll());
  }

  public Completable saveAll(final Collection<CustomerEntity> entities) {
    return Completable.fromEmitter(completableEmitter -> {
      try {
        saveAllSynchronous(entities);
        completableEmitter.onCompleted();
      } catch (SQLException e) {
        completableEmitter.onError(e);
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

  public Single<CustomerEntity> queryForId(int customerId) {
    return Single.fromCallable(() -> mCustomersDao.queryForId(customerId));
  }

  public Single<List<CustomerEntity>> queryForTitle(String title) {
    return Single.fromCallable(() -> {
      mSearchByTitleQuerySelectArg.setValue(PERCENT + title + PERCENT);
      return mCustomersDao.query(mSearchByTitleQuery);
    });
  }
}
