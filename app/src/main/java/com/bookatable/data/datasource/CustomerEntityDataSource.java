package com.bookatable.data.datasource;

import com.bookatable.data.datasource.datastore.customer.DatabaseCustomerEntityStore;
import com.bookatable.data.datasource.datastore.customer.ServerCustomerEntityStore;
import com.bookatable.data.entity.Customer;
import com.bookatable.domain.datasource.CustomerDataSource;
import java.util.List;
import javax.inject.Inject;
import rx.Single;

import static rx.Single.just;

public class CustomerEntityDataSource implements CustomerDataSource {

  private final DatabaseCustomerEntityStore mDatabaseCustomerEntityStore;
  private final ServerCustomerEntityStore mServerCustomerEntityStore;

  @Inject public CustomerEntityDataSource(DatabaseCustomerEntityStore databaseCustomerEntityStore,
      ServerCustomerEntityStore serverCustomerEntityStore) {
    mDatabaseCustomerEntityStore = databaseCustomerEntityStore;
    mServerCustomerEntityStore = serverCustomerEntityStore;
  }

  @Override public Single<List<Customer>> customers() {
    return queryDatabaseForAll();
  }

  private Single<List<Customer>> queryDatabaseForAll() {
    return mDatabaseCustomerEntityStore.queryForAll().flatMap(customerEntities -> {
      if (customerEntities.size() != 0) {
        return just(customerEntities);
      }
      return loadFromServer();
    });
  }

  private Single<List<Customer>> loadFromServer() {
    return mServerCustomerEntityStore.customerList()
        .flatMap(loadedCustomerEntities -> mDatabaseCustomerEntityStore.
            saveAll(loadedCustomerEntities).andThen(just(loadedCustomerEntities)));
  }

  @Override public Single<List<Customer>> searchCustomersByName(String name) {
    return mDatabaseCustomerEntityStore.queryForTitle(name);
  }

  @Override public Single<Customer> customer(int customerId) {
    return mDatabaseCustomerEntityStore.queryForId(customerId);
  }
}
