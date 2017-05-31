package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.entity.Customer;
import javax.inject.Inject;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Single;

/**
 * Domain Use Case: Represents getting of particular customer operation.
 */
@Accessors(prefix = "m") public class GetCustomer {

  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public GetCustomer(CustomerEntityDataSource customerEntityDataSource) {
    mCustomerEntityDataSource = customerEntityDataSource;
  }

  public Single<Customer> call(int customerId) {
    return this.mCustomerEntityDataSource.customer(customerId);
  }
}
