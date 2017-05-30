package com.bookatable.presentation.presenter;

import android.support.annotation.NonNull;
import com.bookatable.data.entity.Customer;
import com.bookatable.domain.usecases.GetCustomersList;
import com.bookatable.domain.usecases.SearchByName;
import com.bookatable.domain.usecases.SimpleSubscriber;
import com.bookatable.presentation.view.CustomerListView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

/**
 * Presenter as in Model-View-Presenter pattern.
 * Presents List of Customers.
 */
public class CustomersListPresenter {

  //Use cases
  private final GetCustomersList mGetCustomerListUseCase;
  private final SearchByName mSearchByNameUseCase;
  private CustomerListView mViewList;

  private AlphabetCustomerModelTitleComparator mModelFirstNameComparator =
      new AlphabetCustomerModelTitleComparator(true);
  private String mSearchedTitle;

  @Inject public CustomersListPresenter(GetCustomersList getCustomerListUseCase,
      SearchByName searchByNameUseCase) {

    mGetCustomerListUseCase = getCustomerListUseCase;
    mSearchByNameUseCase = searchByNameUseCase;
  }

  public void setView(@NonNull CustomerListView view) {
    mViewList = view;
  }

  public void destroy() {
    mGetCustomerListUseCase.unsubscribe();
  }

  /**
   * Initializes the presenter by start retrieving the customer list.
   */
  public void initialize() {
    loadCustomerList();
  }

  /**
   * Loads all customers.
   */
  public void loadCustomerList() {
    hideViewRetry();
    showViewLoading();
    getCustomerList();
  }

  private void showViewLoading() {
    mViewList.showLoading();
  }

  private void hideViewRetry() {
    mViewList.hideRetry();
  }

  private void getCustomerList() {
    mGetCustomerListUseCase.execute(new CustomerListSubscriber());
  }

  public void sort(boolean ascending) {
    mModelFirstNameComparator = new AlphabetCustomerModelTitleComparator(ascending);

    if (mSearchedTitle == null || mSearchedTitle.isEmpty()) {
      loadCustomerList();
      mViewList.highlightTextInList(null);
    } else {
      searchByTitle(mSearchedTitle);
    }
  }

  public void searchByTitle(String title) {
    mSearchedTitle = title;

    if (title == null || title.isEmpty()) {
      loadCustomerList();
    } else {
      showViewLoading();
      mSearchByNameUseCase.setSearchedTitle(title);
      mSearchByNameUseCase.execute(new CustomerListSubscriber());
    }

    mViewList.highlightTextInList(title);
  }

  public void onCustomerClicked(Customer customer) {
    mViewList.viewCustomer(customer);
  }

  private void hideViewLoading() {
    mViewList.hideLoading();
  }

  private void showViewRetry() {
    mViewList.showRetry();
  }

  private void showErrorMessage() {
    //TODO implement
    String errorMessage = "Error happened";
    mViewList.showError(errorMessage);
  }

  private void showCustomerListInView(List<Customer> customerList) {
    Collections.sort(customerList, mModelFirstNameComparator);

    mViewList.renderCustomerList(customerList);
  }

  private final class CustomerListSubscriber extends SimpleSubscriber<List<Customer>> {

    @Override public void onCompleted() {
      CustomersListPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      CustomersListPresenter.this.hideViewLoading();
      CustomersListPresenter.this.showErrorMessage();
      CustomersListPresenter.this.showViewRetry();
    }

    @Override public void onNext(List<Customer> customers) {
      CustomersListPresenter.this.showCustomerListInView(customers);
    }
  }

  private class AlphabetCustomerModelTitleComparator implements Comparator<Customer> {
    private boolean mIsAscending;

    AlphabetCustomerModelTitleComparator(boolean isAscending) {
      mIsAscending = isAscending;
    }

    @Override public int compare(@NonNull Customer lhs, @NonNull Customer rhs) {
      return lhs.getFirstName().compareToIgnoreCase(rhs.getFirstName()) * (mIsAscending ? 1 : -1);
    }
  }
}

