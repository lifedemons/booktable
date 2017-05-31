package com.bookatable.presentation.view;

import com.bookatable.data.entity.Customer;
import com.bookatable.data.entity.Table;
import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a customer profile.
 */
public interface BookTableView extends LoadDataView {
  /**
   * Render a customer in the UI.
   *
   * @param customer The {@link Customer} that will be shown.
   */
  void showCustomer(Customer customer);

  /**
   * Show tables
   * @param tables
   */
  void showTables(List<Table> tables);

  void redraw();
}
