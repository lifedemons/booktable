package com.bookatable.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.bookatable.presentation.view.activity.CustomerDetailsActivity;
import javax.inject.Inject;

/**
 * Class used to navigate through the application.
 */
public class Navigator {

  @Inject public Navigator() {}

  /**
   * Goes to the customer details screen.
   *
   * @param context A Context needed to open the activity.
   */
  public void navigateToCustomerDetails(Context context, int customerId) {
    if (context != null) {
      Intent intentToLaunch = CustomerDetailsActivity.getCallingIntent(context, customerId);
      context.startActivity(intentToLaunch);
    }
  }
}
