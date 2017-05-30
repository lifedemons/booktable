package com.bookatable.domain.mapper.customer;

import com.bookatable.data.entity.CustomerEntity;
import com.bookatable.domain.Customer;
import com.bookatable.domain.mapper.BaseLayerDataTransformer;

public class CustomerEntityToCustomer extends BaseLayerDataTransformer<CustomerEntity, Customer> {
  @Override public Customer transform(CustomerEntity from) {
    Customer transformed = new Customer();

    transformed.setId(from.getId());
    transformed.setFirstName(from.getFirstName());
    transformed.setLastName(from.getLastName());

    return transformed;
  }
}
