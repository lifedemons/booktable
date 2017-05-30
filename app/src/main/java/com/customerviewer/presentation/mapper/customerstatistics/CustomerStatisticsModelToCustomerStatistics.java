package com.customerviewer.presentation.mapper.customerstatistics;

import com.customerviewer.domain.CustomerStatistics;
import com.customerviewer.presentation.mapper.customer.CustomerModelToCustomer;
import com.customerviewer.presentation.model.CustomerModel;
import com.customerviewer.presentation.model.CustomerStatisticsModel;
import com.customerviewer.domain.mapper.BaseLayerDataTransformer;

public class CustomerStatisticsModelToCustomerStatistics extends BaseLayerDataTransformer<CustomerStatisticsModel, CustomerStatistics> {

    private CustomerModelToCustomer mCustomerModelToCustomerTransformer;

    public CustomerStatisticsModelToCustomerStatistics() {
        mCustomerModelToCustomerTransformer = new CustomerModelToCustomer();
    }

    @Override
    public CustomerStatistics transform(CustomerStatisticsModel from) {
        CustomerStatistics transformed = new CustomerStatistics();

        CustomerModel lastOpenedCustomerModel = from.getLastOpenedCustomerModel();

        if (lastOpenedCustomerModel != null) {
            transformed.setLastOpenedCustomer(mCustomerModelToCustomerTransformer.transform(lastOpenedCustomerModel));
            transformed.setOpenedCustomersCount(from.getOpenedCustomersCount());
        }

        return transformed;
    }
}
