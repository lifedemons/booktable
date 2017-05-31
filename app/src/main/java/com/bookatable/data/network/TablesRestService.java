package com.bookatable.data.network;

import com.bookatable.data.entity.Table;
import java.util.List;
import retrofit2.http.GET;
import rx.Observable;

/**
 * RestApi for retrieving Tables data from the network.
 */
public interface TablesRestService {
  /**
   * Retrieves an {@link rx.Observable} which will emit a List of {@link Table}.
   */
  @GET("quandoo-assessment/table-map.json") Observable<List<Boolean>> getTables();
}
