package com.customerviewer.pages;

import com.customerviewer.R;
import com.customerviewer.common.steps.PageObject;
import com.customerviewer.presentation.view.activity.CustomersListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CustomerListPage extends PageObject<CustomersListActivity> {

  private interface Locators {
    int CUSTOMERS_LIST_VIEW = R.id.customers_list;
    int CUSTOMERS_SEARCH_BUTTON = R.id.menu_search;
    int SEARCH_SRC_TEXT = android.support.design.R.id.search_src_text;
  }

  public CustomerListPage() {
    super(CustomersListActivity.class);
  }

  public void open() {
    getActivity();
  }

  public void checkListContainsCustomer(String title) {
    onView(withId(Locators.CUSTOMERS_LIST_VIEW)).check(matches(hasDescendant(withText(title))));
  }

  public void doSearchForCustomer(String title) {
    onView(withId(Locators.CUSTOMERS_SEARCH_BUTTON)).perform(click());
    onView(withId(Locators.SEARCH_SRC_TEXT)).perform(typeText(title));
  }
}
