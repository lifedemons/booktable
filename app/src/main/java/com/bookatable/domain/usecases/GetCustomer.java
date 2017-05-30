package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.di.RxModule;
import com.bookatable.domain.model.Customer;
import com.bookatable.domain.mapper.customer.CustomerEntityToCustomer;
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

  private final CustomerEntityToCustomer mCustomerTransformer;
  @Setter private int mCustomerId;
  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public GetCustomer(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CustomerEntityDataSource customerEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mCustomerTransformer = new CustomerEntityToCustomer();
    mCustomerEntityDataSource = customerEntityDataSource;
  }

  @Override protected Single<Customer> call() {
    return this.mCustomerEntityDataSource.customer(mCustomerId)
        .map(mCustomerTransformer::transform);
  }
}
