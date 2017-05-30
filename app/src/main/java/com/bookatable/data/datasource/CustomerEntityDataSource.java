package com.bookatable.data.datasource;

import com.bookatable.data.datasource.datastore.DatabaseCustomerEntityStore;
import com.bookatable.data.datasource.datastore.ServerCustomerEntityStore;
import com.bookatable.data.entity.CustomerEntity;
import com.bookatable.domain.datasource.CustomerDataSource;
import java.util.List;
import javax.inject.Inject;
import rx.Single;

import static rx.Single.just;

public class CustomerEntityDataSource implements CustomerDataSource {

  private DatabaseCustomerEntityStore mDatabaseCustomerEntityStore;
  private ServerCustomerEntityStore mServerCustomerEntityStore;

  @Inject public CustomerEntityDataSource(DatabaseCustomerEntityStore databaseCustomerEntityStore,
      ServerCustomerEntityStore serverCustomerEntityStore) {
    mDatabaseCustomerEntityStore = databaseCustomerEntityStore;
    mServerCustomerEntityStore = serverCustomerEntityStore;
  }

  @Override public Single<List<CustomerEntity>> customers() {
    return queryDatabaseForAll();
  }

  private Single<List<CustomerEntity>> queryDatabaseForAll() {
    return mDatabaseCustomerEntityStore.queryForAll().flatMap(customerEntities -> {
      if (customerEntities.size() != 0) {
        return just(customerEntities);
      }
      return loadFromServer();
    });
  }

  private Single<List<CustomerEntity>> loadFromServer() {
    return mServerCustomerEntityStore.customerList()
        .flatMap(loadedCustomerEntities -> mDatabaseCustomerEntityStore.
            saveAll(loadedCustomerEntities).andThen(just(loadedCustomerEntities)));
  }

  @Override public Single<List<CustomerEntity>> searchCustomersByName(String name) {
    return mDatabaseCustomerEntityStore.queryForTitle(name);
  }

  @Override public Single<CustomerEntity> customer(int customerId) {
    return mDatabaseCustomerEntityStore.queryForId(customerId);
  }
}
