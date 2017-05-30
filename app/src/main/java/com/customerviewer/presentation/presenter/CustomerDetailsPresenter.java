package com.customerviewer.presentation.presenter;

import android.support.annotation.NonNull;

import com.customerviewer.domain.Customer;
import com.customerviewer.domain.usecases.GetCustomerDetails;
import com.customerviewer.domain.usecases.SimpleSubscriber;
import com.customerviewer.presentation.mapper.customer.CustomerToCustomerModel;
import com.customerviewer.presentation.model.CustomerModel;
import com.customerviewer.presentation.view.CustomerDetailsView;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class CustomerDetailsPresenter extends SimplePresenter {

    private final GetCustomerDetails mGetCustomerDetailsUseCase;
    private final CustomerToCustomerModel mCustomerModelTransformer;
    /**
     * id used to retrieve customer details
     */
    private int mCustomerId;
    private CustomerDetailsView mViewDetailsView;

    @Inject
    public CustomerDetailsPresenter(GetCustomerDetails getCustomerDetailsUseCase) {
        mGetCustomerDetailsUseCase = getCustomerDetailsUseCase;
        mCustomerModelTransformer = new CustomerToCustomerModel();
    }

    public void setView(@NonNull CustomerDetailsView view) {
        mViewDetailsView = view;
    }

    @Override
    public void destroy() {
        mGetCustomerDetailsUseCase.unsubscribe();
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

    private void hideViewLoading() {
        mViewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        mViewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        mViewDetailsView.hideRetry();
    }

    private void showErrorMessage() {
        //TODO implement
        String errorMessage = "Error happened";
        mViewDetailsView.showError(errorMessage);
    }

    private void showCustomerDetailsInView(Customer customer) {
        final CustomerModel customerModel = mCustomerModelTransformer.transform(customer);
        mViewDetailsView.renderCustomer(customerModel);
    }

    private void getCustomerDetails() {
        mGetCustomerDetailsUseCase.setCustomerId(mCustomerId);
        mGetCustomerDetailsUseCase.execute(new CustomerDetailsSubscriber());
    }

    private final class CustomerDetailsSubscriber extends SimpleSubscriber<Customer> {

        @Override
        public void onCompleted() {
            CustomerDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            CustomerDetailsPresenter.this.hideViewLoading();
            CustomerDetailsPresenter.this.showErrorMessage();
            CustomerDetailsPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Customer customer) {
            CustomerDetailsPresenter.this.showCustomerDetailsInView(customer);
        }
    }
}
