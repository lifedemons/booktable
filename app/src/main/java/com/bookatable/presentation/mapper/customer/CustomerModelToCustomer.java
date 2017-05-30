package com.bookatable.presentation.mapper.customer;

import com.bookatable.domain.Customer;
import com.bookatable.domain.mapper.BaseLayerDataTransformer;
import com.bookatable.presentation.model.CustomerModel;

public class CustomerModelToCustomer extends BaseLayerDataTransformer<CustomerModel, Customer> {
  @Override public Customer transform(CustomerModel from) {
    Customer transformed = new Customer();

    transformed.setId(from.getId());
    transformed.setFirstName(from.getFirstName());
    transformed.setLastName(from.getLastName());

    return transformed;
  }
}
