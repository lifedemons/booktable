package com.customerviewer.presentation.presenter;

import android.support.annotation.NonNull;

import com.customerviewer.domain.Customer;
import com.customerviewer.domain.usecases.GetCustomersList;
import com.customerviewer.domain.usecases.SearchByTitle;
import com.customerviewer.domain.usecases.SimpleSubscriber;
import com.customerviewer.presentation.mapper.customer.CustomerToCustomerModel;
import com.customerviewer.presentation.model.CustomerModel;
import com.customerviewer.presentation.view.CustomerListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

/**
 * Presenter that controls communication between views and models of the presentation
 * layer.
 */
public class CustomerListPresenter {

    private CustomerListView mViewList;

    //Use cases
    private final GetCustomersList mGetCustomerListUseCase;
    private final SearchByTitle mSearchByTitleUseCase;

    //Transformers
    private CustomerToCustomerModel mCustomerModelTransformer;

    private AlphabetCustomerModelTitleComparator mModelTitleComparator = new AlphabetCustomerModelTitleComparator(true);
    private String mSearchedTitle;

    @Inject
    public CustomerListPresenter(GetCustomersList getCustomerListUseCase,
                              SearchByTitle searchByTitleUseCase) {

        mGetCustomerListUseCase = getCustomerListUseCase;
        mSearchByTitleUseCase = searchByTitleUseCase;

        createTransformers();
    }

    private void createTransformers() {
        mCustomerModelTransformer = new CustomerToCustomerModel();
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

    public void sort(boolean ascending) {
        mModelTitleComparator = new AlphabetCustomerModelTitleComparator(ascending);

        if(mSearchedTitle == null || mSearchedTitle.isEmpty()) {
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
            mSearchByTitleUseCase.setSearchedTitle(title);
            mSearchByTitleUseCase.execute(new CustomerListSubscriber());
        }

        mViewList.highlightTextInList(title);
    }

    public void onCustomerClicked(CustomerModel customerModel) {
        mViewList.viewCustomer(customerModel);
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

    private void hideViewLoading() {
        mViewList.hideLoading();
    }

    private void showViewRetry() {
        mViewList.showRetry();
    }

    private void hideViewRetry() {
        mViewList.hideRetry();
    }

    private void showErrorMessage() {
        //TODO implement
        String errorMessage = "Error happened";
        mViewList.showError(errorMessage);
    }

    private void showCustomerListInView(List<Customer> customerList) {
        final List<CustomerModel> customerModelsList =
                new ArrayList<>(mCustomerModelTransformer.transform(customerList));

        Collections.sort(customerModelsList, mModelTitleComparator);

        mViewList.renderCustomerList(customerModelsList);
    }

    private void getCustomerList() {
        mGetCustomerListUseCase.execute(new CustomerListSubscriber());
    }

    private final class CustomerListSubscriber extends SimpleSubscriber<List<Customer>> {

        @Override
        public void onCompleted() {
            CustomerListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            CustomerListPresenter.this.hideViewLoading();
            CustomerListPresenter.this.showErrorMessage();
            CustomerListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Customer> customers) {
            CustomerListPresenter.this.showCustomerListInView(customers);
        }
    }

    private class AlphabetCustomerModelTitleComparator implements Comparator<CustomerModel> {
        private boolean mIsAscending;

        AlphabetCustomerModelTitleComparator(boolean isAscending) {
            mIsAscending = isAscending;
        }

        @Override
        public int compare(@NonNull CustomerModel lhs, @NonNull CustomerModel rhs) {
            return lhs.getFirstName().compareToIgnoreCase(rhs.getFirstName())
                    * (mIsAscending ? 1 : -1);
        }
    }
}

