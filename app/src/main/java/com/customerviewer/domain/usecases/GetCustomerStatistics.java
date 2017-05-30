package com.customerviewer.domain.usecases;

import com.customerviewer.data.repository.CustomerStatisticsEntityDataSource;
import com.customerviewer.domain.CustomerStatistics;

import com.customerviewer.presentation.di.modules.RxModule;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m") public class GetCustomerStatistics extends UseCase<CustomerStatistics> {

  @Inject CustomerStatisticsEntityDataSource mCustomerStatisticsEntityDataSource;
  @Inject GetCustomerDetails mGetCustomerDetailsUseCase;

  @Inject public GetCustomerStatistics(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler) {
    super(executionScheduler, observingScheduler);
  }

  @Override protected Observable<CustomerStatistics> call() {
    return mCustomerStatisticsEntityDataSource.readStatistics().
        switchMap(customerStatisticsEntity -> {
          mGetCustomerDetailsUseCase.setCustomerId(customerStatisticsEntity.getLastOpenedCustomerId());
          return mGetCustomerDetailsUseCase.call().map(customer -> {
            CustomerStatistics merged = new CustomerStatistics();

            merged.setLastOpenedCustomer(customer);
            merged.setOpenedCustomersCount(customerStatisticsEntity.getOpenedCustomersCount());

            return merged;
          });
        });
  }
}
