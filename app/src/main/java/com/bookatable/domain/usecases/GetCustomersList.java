package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.di.RxModule;
import com.bookatable.data.entity.Customer;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Scheduler;
import rx.Single;

/**
 * Domain Use Case: Represents getting of customers list operation.
 */
public class GetCustomersList extends UseCase<List<Customer>> {

  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public GetCustomersList(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CustomerEntityDataSource customerEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mCustomerEntityDataSource = customerEntityDataSource;
  }

  @Override protected Single<List<Customer>> call() {
    return mCustomerEntityDataSource.customers();
  }
}

