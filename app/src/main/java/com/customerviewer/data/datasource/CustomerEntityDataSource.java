package com.customerviewer.data.datasource;

import com.customerviewer.data.entity.CustomerEntity;
import com.customerviewer.data.datasource.datastore.DatabaseCustomerEntityStore;
import com.customerviewer.data.datasource.datastore.ServerCustomerEntityStore;
import com.customerviewer.domain.datasource.CustomerRepository;

import java.util.List;

import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class CustomerEntityDataSource implements CustomerRepository {

    private DatabaseCustomerEntityStore mDatabaseCustomerEntityStore;
    private ServerCustomerEntityStore mServerCustomerEntityStore;

    @Inject
    public CustomerEntityDataSource(DatabaseCustomerEntityStore databaseCustomerEntityStore, ServerCustomerEntityStore serverCustomerEntityStore) {
        mDatabaseCustomerEntityStore = databaseCustomerEntityStore;
        mServerCustomerEntityStore = serverCustomerEntityStore;
    }

    @Override
    public Observable<List<CustomerEntity>> customers() {
        return Observable.create(new Observable.OnSubscribe<List<CustomerEntity>>() {
            @Override
            public void call(Subscriber<? super List<CustomerEntity>> subscriber) {
                queryDatabaseForAll(subscriber);
            }
        });
    }

    @Override
    public Observable<List<CustomerEntity>> searchCustomersByTitle(String title) {
        return mDatabaseCustomerEntityStore.queryForTitle(title);
    }

    @Override
    public Observable<CustomerEntity> customer(int customerId) {
        return mDatabaseCustomerEntityStore.queryForId(customerId);
    }

    private void queryDatabaseForAll(final Subscriber<? super List<CustomerEntity>> subscriber) {
        mDatabaseCustomerEntityStore.queryForAll().subscribe(new Observer<List<CustomerEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
                fetchServerForAll(subscriber);
            }

            @Override
            public void onNext(List<CustomerEntity> customerEntities) {
                if (customerEntities.size() != 0) {
                    subscriber.onNext(customerEntities);
                    subscriber.onCompleted();
                } else {
                    fetchServerForAll(subscriber);
                }
            }
        });
    }

    private void fetchServerForAll(Subscriber<? super List<CustomerEntity>> subscriber) {
        mServerCustomerEntityStore.customerEntityList().subscribe(new Observer<List<CustomerEntity>>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(List<CustomerEntity> customerEntities) {
                subscriber.onNext(customerEntities);
                mDatabaseCustomerEntityStore.
                        saveAll(customerEntities).
                        subscribe();
            }
        });
    }
}
