package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.di.RxModule;
import com.bookatable.domain.Customer;
import com.bookatable.domain.mapper.customer.CustomerEntityToCustomer;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m") public class SearchByName extends UseCase<List<Customer>> {

  private final CustomerEntityToCustomer customerTransformer;
  @Setter private String mSearchedTitle;
  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public SearchByName(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CustomerEntityDataSource customerEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mCustomerEntityDataSource = customerEntityDataSource;
    customerTransformer = new CustomerEntityToCustomer();
  }

  @Override protected Observable<List<Customer>> call() {
    return this.mCustomerEntityDataSource.searchCustomersByName(mSearchedTitle)
        .map(customerTransformer::transform);
  }
}

