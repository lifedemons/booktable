package com.customerviewer.presentation.mapper.customer;

import com.customerviewer.domain.Customer;
import com.customerviewer.domain.mapper.BaseLayerDataTransformer;
import com.customerviewer.presentation.model.CustomerModel;

public class CustomerModelToCustomer extends BaseLayerDataTransformer<CustomerModel, Customer> {
    @Override
    public Customer transform(CustomerModel from) {
        Customer transformed = new Customer();

        transformed.setId(from.getId());
        transformed.setTitle(from.getTitle());
        transformed.setThumbnailUrl(from.getThumbnailUrl());
        transformed.setUrl(from.getUrl());

        return transformed;
    }
}
