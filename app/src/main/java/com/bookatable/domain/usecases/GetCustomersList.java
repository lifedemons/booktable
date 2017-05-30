package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.di.RxModule;
import com.bookatable.domain.Customer;
import com.bookatable.domain.mapper.customer.CustomerEntityToCustomer;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

public class GetCustomersList extends UseCase<List<Customer>> {

  private final CustomerEntityToCustomer mCustomerTransformer;
  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public GetCustomersList(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CustomerEntityDataSource customerEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mCustomerTransformer = new CustomerEntityToCustomer();
    mCustomerEntityDataSource = customerEntityDataSource;
  }

  @Override protected Observable<List<Customer>> call() {
    return mCustomerEntityDataSource.customers().map(mCustomerTransformer::transform);
  }
}

