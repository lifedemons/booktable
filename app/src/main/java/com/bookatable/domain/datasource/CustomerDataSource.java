package com.bookatable.domain.datasource;

import com.bookatable.data.entity.CustomerEntity;
import com.bookatable.domain.model.Customer;
import java.util.List;
import rx.Single;

/**
 * Interface that represents a DataSource for getting {@link Customer} related data.
 * The implementation of this interface should hide/encapsulate details of the source of Data.
 * Upper Layers of the App should not know about DB or Rest sources of data.
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
