package com.bookatable.presentation.view;

import com.bookatable.data.entity.Customer;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a customer profile.
 */
public interface CustomerDetailsView extends LoadDataView {
  /**
   * Render a customer in the UI.
   *
   * @param customer The {@link Customer} that will be shown.
   */
  void renderCustomer(Customer customer);
}
