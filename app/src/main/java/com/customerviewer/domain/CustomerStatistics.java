package com.customerviewer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class CustomerStatistics {

    @Setter
    @Getter
    private Customer mLastOpenedCustomer;

    @Setter
    @Getter
    private int mOpenedCustomersCount;
}