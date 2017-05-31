package com.bookatable.presentation.presenter;

import android.support.annotation.NonNull;
import com.bookatable.data.di.RxModule;
import com.bookatable.data.entity.Customer;
import com.bookatable.domain.usecases.GetCustomersList;
import com.bookatable.domain.usecases.SearchByName;
import com.bookatable.presentation.view.CustomerListView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Scheduler;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter as in Model-View-Presenter pattern.
 * Presents List of Customers.
 */
public class CustomersListPresenter {

  //Use cases
  private final GetCustomersList mGetCustomerListUseCase;
  private final SearchByName mSearchByNameUseCase;
  private final Scheduler mExecutionScheduler;
  private final Scheduler mObservingScheduler;
  private final CompositeSubscription mCompositeSubscription;

  private CustomerListView mViewList;

  private AlphabetCustomerModelTitleComparator mModelFirstNameComparator =
      new AlphabetCustomerModelTitleComparator(true);
  private String mSearchedTitle;

  @Inject public CustomersListPresenter(GetCustomersList getCustomerListUseCase,
      SearchByName searchByNameUseCase, @Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CompositeSubscription compositeSubscription) {

    mGetCustomerListUseCase = getCustomerListUseCase;
    mSearchByNameUseCase = searchByNameUseCase;
    mExecutionScheduler = executionScheduler;
    mObservingScheduler = observingScheduler;
    mCompositeSubscription = compositeSubscription;
  }

  public void setView(@NonNull CustomerListView view) {
    mViewList = view;
  }

  public void destroy() {
    mCompositeSubscription.clear();
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
    mCompositeSubscription.add(
        mGetCustomerListUseCase.call()
            .subscribeOn(mExecutionScheduler)
            .observeOn(mObservingScheduler)
            .subscribe(this::showCustomerListInView, this::onError));
  }

  private void onError(Throwable throwable) {
    hideViewLoading();
    showErrorMessage();
    showViewRetry();
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

  public void searchByTitle(String name) {
    mSearchedTitle = name;

    if (name == null || name.isEmpty()) {
      loadCustomerList();
    } else {
      showViewLoading();
      launchSearch(name);
    }

    mViewList.highlightTextInList(name);
  }

  private void launchSearch(String name) {
    mCompositeSubscription.add(
        mSearchByNameUseCase.call(name)
            .subscribeOn(mExecutionScheduler)
            .observeOn(mObservingScheduler)
            .subscribe(this::showCustomerListInView, this::onError));
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
    hideViewLoading();
    Collections.sort(customerList, mModelFirstNameComparator);

    mViewList.renderCustomerList(customerList);
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

