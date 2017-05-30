package com.customerviewer.data.entity;

import com.customerviewer.data.preferences.orm.Preference;
import com.customerviewer.data.preferences.orm.PreferenceFile;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
@PreferenceFile(name = "customers_opening_statistics")
public class CustomerStatisticsEntity {

    @Setter
    @Getter
    @Preference(name = "last_opened_customer_id")
    private int mLastOpenedCustomerId;

    @Setter
    @Getter
    @Preference(name = "opened_customers_count")
    private int mOpenedCustomersCount;
}
