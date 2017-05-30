package com.bookatable.domain.datasource;

import com.bookatable.data.entity.CustomerEntity;
import com.bookatable.domain.Customer;
import java.util.List;
import rx.Observable;
import rx.Single;

/**
 * Interface that represents a Repository for getting {@link Customer} related data.
 */
public interface CustomerDataSource {
  /**
   * Get an {@link rx.Observable} which will emit a List of {@link CustomerEntity}.
   */
  Single<List<CustomerEntity>> customers();

  /**
   * Get an {@link rx.Observable} which will emit a List of {@link CustomerEntity},
   * whom titles match searched name.
   *
   * @param name The customers' Title used to retrieve customer data.
   */
  Single<List<CustomerEntity>> searchCustomersByName(String name);

  /**
   * Get an {@link rx.Observable} which will emit a {@link CustomerEntity}.
   *
   * @param customerId The customer id used to retrieve customer data.
   */
  Single<CustomerEntity> customer(final int customerId);
}
