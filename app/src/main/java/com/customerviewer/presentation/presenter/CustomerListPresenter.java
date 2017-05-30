package com.customerviewer.presentation.presenter;

import android.support.annotation.NonNull;

import com.customerviewer.domain.Customer;
import com.customerviewer.domain.CustomerStatistics;
import com.customerviewer.domain.usecases.GetCustomerStatistics;
import com.customerviewer.domain.usecases.GetCustomersList;
import com.customerviewer.domain.usecases.SaveCustomerStatistics;
import com.customerviewer.domain.usecases.SearchByTitle;
import com.customerviewer.domain.usecases.SimpleSubscriber;
import com.customerviewer.presentation.mapper.customer.CustomerToCustomerModel;
import com.customerviewer.presentation.mapper.customerstatistics.CustomerStatisticsModelToCustomerStatistics;
import com.customerviewer.presentation.mapper.customerstatistics.CustomerStatisticsToCustomerStatisticsModel;
import com.customerviewer.presentation.model.CustomerModel;
import com.customerviewer.presentation.model.CustomerStatisticsModel;
import com.customerviewer.presentation.view.CustomerListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class CustomerListPresenter extends SimplePresenter {

    private CustomerStatisticsModel mCustomerStatisticsModel;

    private CustomerListView mViewList;

    //Use cases
    private final GetCustomersList mGetCustomerListUseCase;
    private final SearchByTitle mSearchByTitleUseCase;
    private final GetCustomerStatistics mGetCustomerStatisticsUseCase;
    private final SaveCustomerStatistics mSaveCustomerStatisticsUseCase;

    //Transformers
    private CustomerToCustomerModel mCustomerModelTransformer;
    private CustomerStatisticsToCustomerStatisticsModel mCustomerStatisticsToCustomerStatisticsModelTransformer;
    private CustomerStatisticsModelToCustomerStatistics mCustomerStatisticsModelToCustomerStatisticsTransformer;

    private AlphabetCustomerModelTitleComparator mModelTitleComparator = new AlphabetCustomerModelTitleComparator(true);
    private String mSearchedTitle;

    @Inject
    public CustomerListPresenter(GetCustomersList getCustomerListUseCase,
                              SearchByTitle searchByTitleUseCase,
                              GetCustomerStatistics getCustomerStatistics,
                              SaveCustomerStatistics saveCustomerStatistics) {

        mGetCustomerListUseCase = getCustomerListUseCase;
        mSearchByTitleUseCase = searchByTitleUseCase;
        mGetCustomerStatisticsUseCase = getCustomerStatistics;
        mSaveCustomerStatisticsUseCase = saveCustomerStatistics;

        createTransformers();
    }

    private void createTransformers() {
        mCustomerModelTransformer = new CustomerToCustomerModel();
        mCustomerStatisticsModelToCustomerStatisticsTransformer = new CustomerStatisticsModelToCustomerStatistics();
        mCustomerStatisticsToCustomerStatisticsModelTransformer = new CustomerStatisticsToCustomerStatisticsModel();
    }

    public void setView(@NonNull CustomerListView view) {
        mViewList = view;
    }

    @Override public void destroy() {
        mGetCustomerListUseCase.unsubscribe();
    }

    /**
     * Initializes the presenter by start retrieving the customer list.
     */
    public void initialize() {
        loadCustomerList();
        loadCustomerStatistics();
    }

    private void loadCustomerStatistics() {
        mGetCustomerStatisticsUseCase.execute(new CustomerStatisticsSubscriber());
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
        updateCustomerStatisticsModel(customerModel);
    }

    private void updateCustomerStatisticsModel(CustomerModel customerModel) {
        mCustomerStatisticsModel.incOpenedCustomersCount();
        mCustomerStatisticsModel.setLastOpenedCustomerModel(customerModel);

        CustomerStatistics customerStatistics = mCustomerStatisticsModelToCustomerStatisticsTransformer.transform(mCustomerStatisticsModel);
        mSaveCustomerStatisticsUseCase.setCustomerStatistics(customerStatistics);
        mSaveCustomerStatisticsUseCase.execute(new SimpleSubscriber<>());

        mViewList.renderCustomerStatisticsModel(mCustomerStatisticsModel);
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

    private void showCustomerStatisticsInView(CustomerStatistics customerStatistics) {
        mCustomerStatisticsModel = mCustomerStatisticsToCustomerStatisticsModelTransformer.transform(customerStatistics);
        mViewList.renderCustomerStatisticsModel(mCustomerStatisticsModel);
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

    private final class CustomerStatisticsSubscriber extends SimpleSubscriber<CustomerStatistics> {

        @Override
        public void onCompleted() {
            CustomerListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            CustomerListPresenter.this.showCustomerStatisticsInView(new CustomerStatistics());
        }

        @Override
        public void onNext(CustomerStatistics customerStatistics) {
            CustomerListPresenter.this.showCustomerStatisticsInView(customerStatistics);
        }
    }

    private class AlphabetCustomerModelTitleComparator implements Comparator<CustomerModel> {
        private boolean mIsAscending;

        AlphabetCustomerModelTitleComparator(boolean isAscending) {
            mIsAscending = isAscending;
        }

        @Override
        public int compare(@NonNull CustomerModel lhs, @NonNull CustomerModel rhs) {
            return lhs.getTitle().compareToIgnoreCase(rhs.getTitle())
                    * (mIsAscending ? 1 : -1);
        }
    }
}

