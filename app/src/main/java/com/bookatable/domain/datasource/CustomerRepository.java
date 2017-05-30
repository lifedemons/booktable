package com.bookatable.domain.datasource;

import com.bookatable.data.entity.CustomerEntity;
import com.bookatable.domain.Customer;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link Customer} related data.
 */
public interface CustomerRepository {
    /**
     * Get an {@link rx.Observable} which will emit a List of {@link CustomerEntity}.
     */
    Observable<List<CustomerEntity>> customers();

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link CustomerEntity},
     * whom titles match searched name.
     * @param name The customers' Title used to retrieve customer data.
     */
    Observable<List<CustomerEntity>> searchCustomersByName(String name);

    /**
     * Get an {@link rx.Observable} which will emit a {@link CustomerEntity}.
     *
     * @param customerId The customer id used to retrieve customer data.
     */
    Observable<CustomerEntity> customer(final int customerId);
}
