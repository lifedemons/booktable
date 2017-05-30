package com.bookatable.data.datasource.datastore.table;

import com.bookatable.data.db.DatabaseManager;
import com.bookatable.data.entity.Table;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import rx.Completable;
import rx.Single;

public class DatabaseTableEntityStore {

  private Dao<Table, Integer> mTablesDao;

  @Inject public DatabaseTableEntityStore(DatabaseManager databaseManager) {
    mTablesDao = databaseManager.getTablesDao();
  }

  public Single<List<Table>> queryForAll() {
    return Single.fromCallable(() -> mTablesDao.queryForAll());
  }

  public Completable saveAll(final Collection<Table> entities) {
    return Completable.fromEmitter(completableEmitter -> {
      try {
        saveAllSynchronous(entities);
        completableEmitter.onCompleted();
      } catch (SQLException e) {
        completableEmitter.onError(e);
      }
    });
  }

  private void saveAllSynchronous(final Collection<Table> entities) throws SQLException {
    TransactionManager.callInTransaction(mTablesDao.getConnectionSource(), () -> {
      for (Table table : entities) {
        mTablesDao.createOrUpdate(table);
      }
      return null;
    });
  }
}
