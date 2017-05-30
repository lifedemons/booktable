package com.bookatable.data.network;

import com.bookatable.data.entity.Customer;
import com.bookatable.data.entity.Table;
import java.util.List;
import retrofit2.http.GET;
import rx.Observable;

/**
 * RestApi for retrieving Customer data from the network.
 */
public interface CustomerRestService {

  /**
   * Retrieves an {@link rx.Observable} which will emit a List of {@link Customer}.
   */
  @GET("quandoo-assessment/customer-list.json") Observable<List<Customer>> getCustomers();

  /**
   * Retrieves an {@link rx.Observable} which will emit a List of {@link Customer}.
   */
  @GET("quandoo-assessment/table-map.json") Observable<List<Table>> getTables();
}
