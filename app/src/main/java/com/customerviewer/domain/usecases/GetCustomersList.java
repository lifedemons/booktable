package com.customerviewer.domain.usecases;

import com.customerviewer.data.repository.CustomerEntityDataSource;
import com.customerviewer.domain.Customer;
import com.customerviewer.domain.mapper.customer.CustomerEntityToCustomer;
import com.customerviewer.presentation.di.modules.RxModule;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

public class GetCustomersList extends UseCase<List<Customer>> {

  private CustomerEntityDataSource mCustomerEntityDataSource;
  private final CustomerEntityToCustomer mCustomerTransformer;

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

