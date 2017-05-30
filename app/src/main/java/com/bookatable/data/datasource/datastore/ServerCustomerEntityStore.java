package com.bookatable.data.datasource.datastore;

import com.bookatable.data.entity.CustomerEntity;
import com.bookatable.data.network.CustomerRestService;
import java.util.List;
import javax.inject.Inject;
import rx.Single;

public class ServerCustomerEntityStore {

  private final CustomerRestService mService;

  @Inject public ServerCustomerEntityStore(CustomerRestService service) {
    mService = service;
  }

  public Single<List<CustomerEntity>> customerList() {
    return mService.getCustomers().toSingle();
  }
}
