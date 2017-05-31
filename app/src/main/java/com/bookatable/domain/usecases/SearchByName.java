package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.entity.Customer;
import java.util.List;
import javax.inject.Inject;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Single;

/**
 * Domain Use Case: Represents operation of searching in customers list by name.
 */
@Accessors(prefix = "m") public class SearchByName {
  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public SearchByName(CustomerEntityDataSource customerEntityDataSource) {
    mCustomerEntityDataSource = customerEntityDataSource;
  }

  public Single<List<Customer>> call(String name) {
    return this.mCustomerEntityDataSource.searchCustomersByName(name);
  }
}

