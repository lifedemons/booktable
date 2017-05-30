package com.bookatable.data.datasource.datastore.customer;

import android.util.Log;
import com.bookatable.data.db.DatabaseManager;
import com.bookatable.data.entity.Customer;
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

  private Dao<Customer, Integer> mCustomersDao;

  //Search prepared fields, for faster search
  private PreparedQuery<Customer> mSearchByTitleQuery;
  private SelectArg mSearchByFirstNameQuerySelectArg;
  private SelectArg mSearchByLastNameQuerySelectArg;

  @Inject public DatabaseCustomerEntityStore(DatabaseManager databaseManager) {
    mCustomersDao = databaseManager.getCustomersDao();
    prepareSearchByTitleQuery();
  }

  private void prepareSearchByTitleQuery() {
    try {
      QueryBuilder<Customer, Integer> queryBuilder = mCustomersDao.queryBuilder();
      mSearchByFirstNameQuerySelectArg = new SelectArg();
      mSearchByLastNameQuerySelectArg = new SelectArg();
      queryBuilder.where()
          .like(Customer.Fields.FIRST_NAME, mSearchByFirstNameQuerySelectArg)
          .or()
          .like(Customer.Fields.LAST_NAME, mSearchByLastNameQuerySelectArg);
      mSearchByTitleQuery = queryBuilder.prepare();
    } catch (SQLException e) {
      Log.wtf(LOG_TAG, "Preparing of SearchByTitleQuery failed", e);
    }
  }

  public Single<List<Customer>> queryForAll() {
    return Single.fromCallable(() -> mCustomersDao.queryForAll());
  }

  public Completable saveAll(final Collection<Customer> entities) {
    return Completable.fromEmitter(completableEmitter -> {
      try {
        saveAllSynchronous(entities);
        completableEmitter.onCompleted();
      } catch (SQLException e) {
        completableEmitter.onError(e);
      }
    });
  }

  private void saveAllSynchronous(final Collection<Customer> entities) throws SQLException {
    TransactionManager.callInTransaction(mCustomersDao.getConnectionSource(), () -> {
      for (Customer customer : entities) {
        mCustomersDao.createOrUpdate(customer);
      }
      return null;
    });
  }

  public Single<Customer> queryForId(int customerId) {
    return Single.fromCallable(() -> mCustomersDao.queryForId(customerId));
  }

  public Single<List<Customer>> queryForTitle(String title) {
    return Single.fromCallable(() -> {
      String value = PERCENT + title + PERCENT;
      mSearchByFirstNameQuerySelectArg.setValue(value);
      mSearchByLastNameQuerySelectArg.setValue(value);
      return mCustomersDao.query(mSearchByTitleQuery);
    });
  }
}
