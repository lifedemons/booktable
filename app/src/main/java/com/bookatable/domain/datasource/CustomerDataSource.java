package com.bookatable.domain.datasource;

import com.bookatable.data.entity.Customer;
import java.util.List;
import rx.Single;

/**
 * Interface that represents a DataSource for getting {@link Customer} related data.
 * The implementation of this interface should hide/encapsulate details of the source of Data.
 * Upper Layers of the App should not know about DB or Rest sources of data.
 */
public interface CustomerDataSource {
  /**
   * Get an {@link rx.Observable} which will emit a List of {@link Customer}.
   */
  Single<List<Customer>> customers();

  /**
   * Get an {@link rx.Observable} which will emit a List of {@link Customer},
   * whom first or last names match searched name.
   *
   * @param name The customers' Title used to retrieve customer data.
   */
  Single<List<Customer>> searchCustomersByName(String name);

  /**
   * Get an {@link rx.Observable} which will emit a {@link Customer}.
   *
   * @param customerId The customer id used to retrieve customer data.
   */
  Single<Customer> customer(final int customerId);
}
