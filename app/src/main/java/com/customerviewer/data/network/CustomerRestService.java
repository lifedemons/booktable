package com.customerviewer.data.network;

import com.customerviewer.data.entity.CustomerEntity;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface CustomerRestService {

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link CustomerEntity}.
     */
    @GET("quandoo-assessment/customer-list.json")
    Observable<List<CustomerEntity>> getCustomers();

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link CustomerEntity}.
     */
    @GET("quandoo-assessment/table-map.json")
    Observable<List<CustomerEntity>> getTables();
}
