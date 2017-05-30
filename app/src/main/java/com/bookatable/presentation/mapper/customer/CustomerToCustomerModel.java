package com.bookatable.presentation.mapper.customer;

import com.bookatable.domain.Customer;
import com.bookatable.presentation.model.CustomerModel;
import com.bookatable.domain.mapper.BaseLayerDataTransformer;

public class CustomerToCustomerModel extends BaseLayerDataTransformer<Customer, CustomerModel> {
    @Override
    public CustomerModel transform(Customer from) {
        CustomerModel transformed = new CustomerModel();

        transformed.setId(from.getId());
        transformed.setFirstName(from.getFirstName());
        transformed.setLastName(from.getLastName());

        return transformed;
    }
}