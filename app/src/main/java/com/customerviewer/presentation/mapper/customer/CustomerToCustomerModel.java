package com.customerviewer.presentation.mapper.customer;

import com.customerviewer.domain.Customer;
import com.customerviewer.presentation.model.CustomerModel;
import com.customerviewer.domain.mapper.BaseLayerDataTransformer;

public class CustomerToCustomerModel extends BaseLayerDataTransformer<Customer, CustomerModel> {
    @Override
    public CustomerModel transform(Customer from) {
        CustomerModel transformed = new CustomerModel();

        transformed.setId(from.getId());
        transformed.setTitle(from.getTitle());
        transformed.setUrl(from.getUrl());
        transformed.setThumbnailUrl(from.getThumbnailUrl());

        return transformed;
    }
}