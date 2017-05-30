package com.bookatable.presentation.mapper.customer;

import com.bookatable.domain.model.Customer;
import com.bookatable.domain.mapper.BaseLayerDataTransformer;
import com.bookatable.presentation.model.CustomerModel;

public class CustomerToCustomerModel extends BaseLayerDataTransformer<Customer, CustomerModel> {
  @Override public CustomerModel transform(Customer from) {
    CustomerModel transformed = new CustomerModel();

    transformed.setId(from.getId());
    transformed.setFirstName(from.getFirstName());
    transformed.setLastName(from.getLastName());

    return transformed;
  }
}