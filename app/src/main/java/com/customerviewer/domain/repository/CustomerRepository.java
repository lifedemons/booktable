package com.customerviewer.domain.repository;

import com.customerviewer.data.entity.CustomerEntity;
import com.customerviewer.domain.Customer;

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
     * whom titles match searched title.
     * @param title The customers' Title used to retrieve customer data.
     */
    Observable<List<CustomerEntity>> searchCustomersByTitle(String title);

    /**
     * Get an {@link rx.Observable} which will emit a {@link CustomerEntity}.
     *
     * @param customerId The customer id used to retrieve customer data.
     */
    Observable<CustomerEntity> customer(final int customerId);
}
