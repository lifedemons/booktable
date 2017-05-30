package com.customerviewer.domain.usecases;

import com.customerviewer.data.datasource.CustomerEntityDataSource;
import com.customerviewer.domain.Customer;
import com.customerviewer.domain.mapper.customer.CustomerEntityToCustomer;

import com.customerviewer.data.di.RxModule;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m") public class SearchByTitle extends UseCase<List<Customer>> {

  @Setter private String mSearchedTitle;
  private CustomerEntityDataSource mCustomerEntityDataSource;
  private final CustomerEntityToCustomer customerTransformer;

  @Inject public SearchByTitle(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
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

