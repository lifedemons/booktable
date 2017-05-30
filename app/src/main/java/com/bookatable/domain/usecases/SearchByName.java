package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.di.RxModule;
import com.bookatable.data.entity.Customer;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Scheduler;
import rx.Single;

/**
 * Domain Use Case: Represents operation of searching in customers list by name.
 */
@Accessors(prefix = "m") public class SearchByName extends UseCase<List<Customer>> {

  @Setter private String mSearchedTitle;
  private CustomerEntityDataSource mCustomerEntityDataSource;

  @Inject public SearchByName(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CustomerEntityDataSource customerEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mCustomerEntityDataSource = customerEntityDataSource;
  }

  @Override protected Single<List<Customer>> call() {
    return this.mCustomerEntityDataSource.searchCustomersByName(mSearchedTitle);
  }
}

