package com.bookatable.presentation.presenter;

import android.support.annotation.NonNull;
import com.bookatable.data.di.RxModule;
import com.bookatable.data.entity.Customer;
import com.bookatable.data.entity.Table;
import com.bookatable.domain.usecases.BookTable;
import com.bookatable.domain.usecases.GetCustomer;
import com.bookatable.domain.usecases.GetTablesList;
import com.bookatable.presentation.view.BookTableView;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Scheduler;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter as in Model-View-Presenter pattern.
 */
public class BookTablePresenter {

  private final GetCustomer mGetCustomerUseCase;
  private final GetTablesList mGetTablesList;
  private final BookTable mBookTable;
  private final Scheduler mExecutionScheduler;
  private final Scheduler mObservingScheduler;
  private final CompositeSubscription mCompositeSubscription;

  /**
   * id used to retrieve customer details
   */
  private int mCustomerId;
  private BookTableView mBookTablesView;
  private Customer mCustomer;

  @Inject public BookTablePresenter(GetCustomer getCustomerUseCase, GetTablesList getTablesList,
      BookTable bookTable, @Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      CompositeSubscription compositeSubscription) {
    mGetCustomerUseCase = getCustomerUseCase;
    mGetTablesList = getTablesList;
    mBookTable = bookTable;
    mExecutionScheduler = executionScheduler;
    mObservingScheduler = observingScheduler;
    mCompositeSubscription = compositeSubscription;
  }

  public void setView(@NonNull BookTableView view) {
    mBookTablesView = view;
  }

  public void destroy() {
    mCompositeSubscription.clear();
  }

  /**
   * Initializes the presenter by start retrieving customer details.
   */
  public void initialize(int customerId) {
    mCustomerId = customerId;
    loadData();
  }

  /**
   * Loads customer details.
   */
  private void loadData() {
    hideViewRetry();
    showViewLoading();
    getCustomerDetails();
    getTables();
  }

  private void getTables() {
    mCompositeSubscription.add(mGetTablesList.call()
        .subscribeOn(mExecutionScheduler)
        .observeOn(mObservingScheduler)
        .subscribe(this::showTablesInView, this::onError));
  }

  private void showTablesInView(List<Table> tables) {
    mBookTablesView.showTables(tables);
    hideViewLoading();
  }

  private void showViewLoading() {
    mBookTablesView.showLoading();
  }

  private void hideViewRetry() {
    mBookTablesView.hideRetry();
  }

  private void getCustomerDetails() {
    mCompositeSubscription.add(mGetCustomerUseCase.call(mCustomerId)
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
    mBookTablesView.hideLoading();
  }

  private void showViewRetry() {
    mBookTablesView.showRetry();
  }

  private void showErrorMessage() {
    //TODO implement
    String errorMessage = "Error happened";
    mBookTablesView.showError(errorMessage);
  }

  private void showCustomerDetailsInView(Customer customer) {
    mBookTablesView.showCustomer(customer);
    hideViewLoading();
    mCustomer = customer;
  }

  public void onTableClicked(Table table) {
    mCompositeSubscription.add(
        mBookTable.call(table, mCustomer)
        .subscribeOn(mExecutionScheduler)
        .observeOn(mObservingScheduler)
        .subscribe(() -> redrawList(), this::onError));
  }

  private void redrawList() {
    mBookTablesView.redraw();
  }
}
