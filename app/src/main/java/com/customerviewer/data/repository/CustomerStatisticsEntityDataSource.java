package com.customerviewer.data.repository;

import com.customerviewer.data.entity.CustomerStatisticsEntity;
import com.customerviewer.data.repository.datastore.PreferencesCustomerStatisticsEntityStore;
import com.customerviewer.domain.repository.CustomerStatisticsRepository;

import javax.inject.Inject;
import rx.Observable;

public class CustomerStatisticsEntityDataSource implements CustomerStatisticsRepository {

    @Inject PreferencesCustomerStatisticsEntityStore mStatisticsEntityStore;

    @Override
    public Observable<CustomerStatisticsEntity> readStatistics() {
        return mStatisticsEntityStore.readStatistics();
    }

    @Override
    public Observable<CustomerStatisticsEntity> updateStatistics(CustomerStatisticsEntity customerStatisticsEntity) {
        return mStatisticsEntityStore.updateStatistics(customerStatisticsEntity);
    }
}
