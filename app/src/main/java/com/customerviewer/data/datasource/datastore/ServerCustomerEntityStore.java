package com.customerviewer.data.datasource.datastore;

import com.customerviewer.data.entity.CustomerEntity;

import com.customerviewer.data.network.CustomerRestService;
import java.util.List;

import javax.inject.Inject;
import rx.Observable;

public class ServerCustomerEntityStore {

    private final CustomerRestService mService;

    @Inject
    public ServerCustomerEntityStore(CustomerRestService service) {
        mService = service;
    }

    public Observable<List<CustomerEntity>> customerEntityList() {
        return mService.customerEntityList();
    }
}
