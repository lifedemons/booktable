package com.bookatable.presentation.presenter;

import android.support.annotation.NonNull;
import com.bookatable.data.di.RxModule;
import com.bookatable.data.entity.Customer;
import com.bookatable.domain.usecases.GetCustomer;
import com.bookatable.presentation.view.CustomerDetailsView;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Scheduler;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter as in Model-View-Presenter pattern.
 */
public class CustomerDetailsPresenter {

  private final GetCustomer mGetCustomerUseCase;
  private final Scheduler mExecutionScheduler;
  private final Scheduler mObservingScheduler;
  private final CompositeSubscription mCompositeSubscription;

  /**
   * id used to retrieve customer details
   */
  private int mCustomerId;
  private CustomerDetailsView mViewDetailsView;

  @Inject public CustomerDetailsPresenter(GetCustomer getCustomerUseCase,
      @Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CompositeSubscription compositeSubscription) {
    mGetCustomerUseCase = getCustomerUseCase;
    mExecutionScheduler = executionScheduler;
    mObservingScheduler = observingScheduler;
    mCompositeSubscription = compositeSubscription;
  }

  public void setView(@NonNull CustomerDetailsView view) {
    mViewDetailsView = view;
  }

  public void destroy() {
    mCompositeSubscription.clear();
  }

  /**
   * Initializes the presenter by start retrieving customer details.
   */
  public void initialize(int customerId) {
    mCustomerId = customerId;
    loadCustomerDetails();
  }

  /**
   * Loads customer details.
   */
  private void loadCustomerDetails() {
    hideViewRetry();
    showViewLoading();
    getCustomerDetails();
  }

  private void showViewLoading() {
    mViewDetailsView.showLoading();
  }

  private void hideViewRetry() {
    mViewDetailsView.hideRetry();
  }

  private void getCustomerDetails() {
    mCompositeSubscription.add(
        mGetCustomerUseCase.call(mCustomerId)
        .subscribeOn(mExecutionScheduler)
        .observeOn(mObservingScheduler)
        .subscribe(this::showCustomerDetailsInView, this::onError));
  }

  private void onError(Throwable throwable) {
    hideViewLoading();
    showErrorMessage();
    showViewRetry();
  }

  private void hideViewLoading() {
    mViewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    mViewDetailsView.showRetry();
  }

  private void showErrorMessage() {
    //TODO implement
    String errorMessage = "Error happened";
    mViewDetailsView.showError(errorMessage);
  }

  private void showCustomerDetailsInView(Customer customer) {
    mViewDetailsView.renderCustomer(customer);
  }
}
