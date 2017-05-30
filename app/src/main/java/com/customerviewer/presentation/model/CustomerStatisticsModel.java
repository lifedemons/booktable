package com.customerviewer.presentation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class CustomerStatisticsModel {

    @Setter
    @Getter
    private CustomerModel mLastOpenedCustomerModel;

    @Setter
    @Getter
    private int mOpenedCustomersCount;

    public void incOpenedCustomersCount() {
        mOpenedCustomersCount++;
    }
}
