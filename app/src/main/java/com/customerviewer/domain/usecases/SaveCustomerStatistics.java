package com.customerviewer.domain.usecases;

import com.customerviewer.data.entity.CustomerStatisticsEntity;
import com.customerviewer.data.repository.CustomerStatisticsEntityDataSource;
import com.customerviewer.domain.CustomerStatistics;
import com.customerviewer.domain.mapper.customerstatisctics.CustomerStatisticsToCustomerStatisticsEntity;

import com.customerviewer.presentation.di.modules.RxModule;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m")
public class SaveCustomerStatistics extends UseCase<Void> {
    private final CustomerStatisticsToCustomerStatisticsEntity mCustomerStatisticsToCustomerStatisticsEntityTransformer;

    @Setter
    private CustomerStatistics mCustomerStatistics;

    @Inject CustomerStatisticsEntityDataSource mCustomerStatisticsEntityDataSource;

    @Inject public SaveCustomerStatistics(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
        @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler) {
        super(executionScheduler, observingScheduler);
        mCustomerStatisticsToCustomerStatisticsEntityTransformer =
            new CustomerStatisticsToCustomerStatisticsEntity();
    }

    @Override
    protected Observable<Void> call() {
        CustomerStatisticsEntity customerStatisticsEntity = mCustomerStatisticsToCustomerStatisticsEntityTransformer.transform(mCustomerStatistics);
        return mCustomerStatisticsEntityDataSource.updateStatistics(customerStatisticsEntity).map(customerStatisticsEntity1 -> null);
    }
}