package com.customerviewer.domain.repository;

import com.customerviewer.data.entity.CustomerStatisticsEntity;

import rx.Observable;

public interface CustomerStatisticsRepository {
    /**
     * Get an {@link rx.Observable} which will emit {@link CustomerStatisticsEntity}.
     */
    Observable<CustomerStatisticsEntity> readStatistics();

    /**
     * Get an {@link rx.Observable} which will emit {@link CustomerStatisticsEntity}.
     */
    Observable<CustomerStatisticsEntity> updateStatistics(CustomerStatisticsEntity customerStatisticsEntity);
}
