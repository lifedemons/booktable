package com.bookatable.data.datasource;

import com.bookatable.data.datasource.datastore.table.DatabaseTableEntityStore;
import com.bookatable.data.datasource.datastore.table.ServerTableEntityStore;
import com.bookatable.data.entity.Table;
import com.bookatable.domain.datasource.TableDataSource;
import java.util.List;
import javax.inject.Inject;
import rx.Completable;
import rx.Single;

import static rx.Single.just;

public class TableEntityDataSource implements TableDataSource {

  private final DatabaseTableEntityStore mDatabaseTableEntityStore;
  private final ServerTableEntityStore mServerTableEntityStore;

  @Inject public TableEntityDataSource(DatabaseTableEntityStore databaseTableEntityStore,
      ServerTableEntityStore serverTableEntityStore) {
    mDatabaseTableEntityStore = databaseTableEntityStore;
    mServerTableEntityStore = serverTableEntityStore;
  }

  @Override public Single<List<Table>> getTables() {
    return queryDatabaseForAll();
  }

  @Override public Completable update(List<Table> table) {
    return mDatabaseTableEntityStore.saveAll(table);
  }

  private Single<List<Table>> queryDatabaseForAll() {
    return mDatabaseTableEntityStore.queryForAll().flatMap(tableEntities -> {
      if (tableEntities.size() != 0) {
        return just(tableEntities);
      }
      return loadFromServer();
    });
  }

  private Single<List<Table>> loadFromServer() {
    return mServerTableEntityStore.tablesList()
        .flatMap(loadedCustomerEntities -> mDatabaseTableEntityStore.
            saveAll(loadedCustomerEntities).andThen(just(loadedCustomerEntities)));
  }
}
