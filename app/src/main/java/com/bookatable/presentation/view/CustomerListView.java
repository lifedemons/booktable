/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.bookatable.presentation.view;

import com.bookatable.data.entity.Customer;
import java.util.Collection;
import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link Customer}.
 */
public interface CustomerListView extends LoadDataView {
  /**
   * Render a customer list in the UI.
   *
   * @param customers The list of {@link Customer} that will be shown.
   */
  void renderCustomerList(List<Customer> customers);

  /**
   * View a {@link Customer} profile/details.
   *
   * @param customerModel The customer that will be shown.
   */
  void viewCustomer(Customer customerModel);

  /**
   * Highlights text entries in list items.
   *
   * @param textToHighlight text to highlight.
   */
  void highlightTextInList(String textToHighlight);
}
