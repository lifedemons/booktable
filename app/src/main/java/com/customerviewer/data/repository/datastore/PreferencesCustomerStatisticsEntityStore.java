package com.customerviewer.data.repository.datastore;

import android.content.Context;

import com.customerviewer.data.entity.CustomerStatisticsEntity;
import com.customerviewer.data.preferences.orm.PreferencesDao;

import javax.inject.Inject;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Subscriber;

@Accessors(prefix = "m")
public class PreferencesCustomerStatisticsEntityStore {

    @Setter
    private PreferencesDao<CustomerStatisticsEntity> mStatisticsDao;

    @Inject
    public PreferencesCustomerStatisticsEntityStore(Context context) {
        setStatisticsDao(new PreferencesDao<>(CustomerStatisticsEntity.class, context));
    }

    public Observable<CustomerStatisticsEntity> readStatistics() {
        return Observable.create(new Observable.OnSubscribe<CustomerStatisticsEntity>() {
            @Override
            public void call(Subscriber<? super CustomerStatisticsEntity> subscriber) {
                try {
                    CustomerStatisticsEntity statistics = mStatisticsDao.read();
                    subscriber.onNext(statistics);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<CustomerStatisticsEntity> updateStatistics(CustomerStatisticsEntity customerStatisticsEntity) {
        return Observable.create(new Observable.OnSubscribe<CustomerStatisticsEntity>() {
            @Override
            public void call(Subscriber<? super CustomerStatisticsEntity> subscriber) {
                try {
                    CustomerStatisticsEntity oldStatistics = mStatisticsDao.read();
                    mStatisticsDao.save(customerStatisticsEntity);
                    subscriber.onNext(oldStatistics);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
