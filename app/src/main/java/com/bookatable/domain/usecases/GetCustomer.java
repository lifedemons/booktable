package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.domain.Customer;
import com.bookatable.domain.mapper.customer.CustomerEntityToCustomer;
import com.bookatable.data.di.RxModule;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m") public class GetCustomer extends UseCase<Customer> {

  @Setter private int mCustomerId;
  private final CustomerEntityToCustomer mCustomerTransformer;

  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public GetCustomer(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CustomerEntityDataSource customerEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mCustomerTransformer = new CustomerEntityToCustomer();
    mCustomerEntityDataSource = customerEntityDataSource;
  }

  @Override protected Observable<Customer> call() {
    return this.mCustomerEntityDataSource.customer(mCustomerId).map(mCustomerTransformer::transform);
  }
}
