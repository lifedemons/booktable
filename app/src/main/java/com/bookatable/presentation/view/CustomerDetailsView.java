package com.bookatable.presentation.view;

import com.bookatable.presentation.model.CustomerModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a customer profile.
 */
public interface CustomerDetailsView extends LoadDataView {
  /**
   * Render a customer in the UI.
   *
   * @param customer The {@link CustomerModel} that will be shown.
   */
  void renderCustomer(CustomerModel customer);
}
