package com.customerviewer.presentation.mapper.customerstatistics;

import com.customerviewer.domain.Customer;
import com.customerviewer.domain.CustomerStatistics;
import com.customerviewer.presentation.mapper.customer.CustomerToCustomerModel;
import com.customerviewer.presentation.model.CustomerStatisticsModel;
import com.customerviewer.domain.mapper.BaseLayerDataTransformer;

public class CustomerStatisticsToCustomerStatisticsModel extends BaseLayerDataTransformer<CustomerStatistics, CustomerStatisticsModel> {

    private CustomerToCustomerModel mCustomerToCustomerModelTransformer;

    public CustomerStatisticsToCustomerStatisticsModel() {
        mCustomerToCustomerModelTransformer = new CustomerToCustomerModel();
    }

    @Override
    public CustomerStatisticsModel transform(CustomerStatistics from) {
        CustomerStatisticsModel transformed = new CustomerStatisticsModel();

        transformed.setOpenedCustomersCount(from.getOpenedCustomersCount());

        Customer lastOpenedCustomer = from.getLastOpenedCustomer();
        if (lastOpenedCustomer != null) {
            transformed.setLastOpenedCustomerModel(mCustomerToCustomerModelTransformer.transform(lastOpenedCustomer));
        }

        return transformed;
    }
}
