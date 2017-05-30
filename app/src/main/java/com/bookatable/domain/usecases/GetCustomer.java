package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.di.RxModule;
import com.bookatable.data.entity.Customer;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Scheduler;
import rx.Single;

/**
 * Domain Use Case: Represents getting of particular customer operation.
 */
@Accessors(prefix = "m") public class GetCustomer extends UseCase<Customer> {

  @Setter private int mCustomerId;
  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public GetCustomer(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CustomerEntityDataSource customerEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mCustomerEntityDataSource = customerEntityDataSource;
  }

  @Override protected Single<Customer> call() {
    return this.mCustomerEntityDataSource.customer(mCustomerId);
  }
}
