package com.customerviewer.domain.mapper.customerstatisctics;

import com.customerviewer.data.entity.CustomerStatisticsEntity;
import com.customerviewer.domain.Customer;
import com.customerviewer.domain.CustomerStatistics;
import com.customerviewer.domain.mapper.BaseLayerDataTransformer;

public class CustomerStatisticsToCustomerStatisticsEntity extends BaseLayerDataTransformer<CustomerStatistics, CustomerStatisticsEntity> {
    @Override
    public CustomerStatisticsEntity transform(CustomerStatistics from) {
        CustomerStatisticsEntity transformed = new CustomerStatisticsEntity();

        Customer lastOpenedCustomer = from.getLastOpenedCustomer();
        transformed.setLastOpenedCustomerId(lastOpenedCustomer != null ? lastOpenedCustomer.getId() : 0);
        transformed.setOpenedCustomersCount(from.getOpenedCustomersCount());

        return transformed;
    }
}
