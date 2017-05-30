package com.bookatable.presentation.presenter;

import android.support.annotation.NonNull;
import com.bookatable.data.entity.Customer;
import com.bookatable.domain.usecases.GetCustomer;
import com.bookatable.domain.usecases.SimpleSubscriber;
import com.bookatable.presentation.view.CustomerDetailsView;
import javax.inject.Inject;


/**
 * Presenter as in Model-View-Presenter pattern.
 */
public class CustomerDetailsPresenter {

  private final GetCustomer mGetCustomerUseCase;

  /**
   * id used to retrieve customer details
   */
  private int mCustomerId;
  private CustomerDetailsView mViewDetailsView;

  @Inject public CustomerDetailsPresenter(GetCustomer getCustomerUseCase) {
    mGetCustomerUseCase = getCustomerUseCase;
  }

  public void setView(@NonNull CustomerDetailsView view) {
    mViewDetailsView = view;
  }

  public void destroy() {
    mGetCustomerUseCase.unsubscribe();
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
    mGetCustomerUseCase.setCustomerId(mCustomerId);
    mGetCustomerUseCase.execute(new CustomerDetailsSubscriber());
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

  private final class CustomerDetailsSubscriber extends SimpleSubscriber<Customer> {

    @Override public void onCompleted() {
      CustomerDetailsPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      CustomerDetailsPresenter.this.hideViewLoading();
      CustomerDetailsPresenter.this.showErrorMessage();
      CustomerDetailsPresenter.this.showViewRetry();
    }

    @Override public void onNext(Customer customer) {
      CustomerDetailsPresenter.this.showCustomerDetailsInView(customer);
    }
  }
}
