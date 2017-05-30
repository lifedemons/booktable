package com.customerviewer.domain.mapper.customer;

import com.customerviewer.data.entity.CustomerEntity;
import com.customerviewer.domain.Customer;
import com.customerviewer.domain.mapper.BaseLayerDataTransformer;

public class CustomerEntityToCustomer extends BaseLayerDataTransformer<CustomerEntity, Customer> {
    @Override
    public Customer transform(CustomerEntity from) {
        Customer transformed = new Customer();

        transformed.setId(from.getId());
        transformed.setTitle(from.getTitle());
        transformed.setUrl(from.getUrl());
        transformed.setThumbnailUrl(from.getThumbnailUrl());

        return transformed;
    }
}
