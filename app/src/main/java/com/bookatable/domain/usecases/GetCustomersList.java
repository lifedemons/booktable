package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.entity.Customer;
import java.util.List;
import javax.inject.Inject;
import rx.Single;

/**
 * Domain Use Case: Represents getting of customers list operation.
 */
public class GetCustomersList {

  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public GetCustomersList(CustomerEntityDataSource customerEntityDataSource) {
    mCustomerEntityDataSource = customerEntityDataSource;
  }

  public Single<List<Customer>> call() {
    return mCustomerEntityDataSource.customers();
  }
}

