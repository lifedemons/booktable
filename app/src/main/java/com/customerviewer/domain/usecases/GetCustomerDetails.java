package com.customerviewer.domain.usecases;

import com.customerviewer.data.datasource.CustomerEntityDataSource;
import com.customerviewer.domain.Customer;
import com.customerviewer.domain.mapper.customer.CustomerEntityToCustomer;
import com.customerviewer.data.di.RxModule;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m") public class GetCustomerDetails extends UseCase<Customer> {

  @Setter private int mCustomerId;
  private final CustomerEntityToCustomer mCustomerTransformer;

  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public GetCustomerDetails(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
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
