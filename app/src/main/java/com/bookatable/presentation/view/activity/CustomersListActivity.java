package com.bookatable.presentation.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import com.bookatable.R;
import com.bookatable.presentation.model.CustomerModel;
import com.bookatable.presentation.navigation.Navigator;
import com.bookatable.presentation.presenter.CustomersListPresenter;
import com.bookatable.presentation.view.CustomerListView;
import com.bookatable.presentation.view.adapter.CustomerAdapter;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;

public class CustomersListActivity extends DiAppCompatActivity implements CustomerListView {

  @Inject CustomersListPresenter mCustomersListPresenter;
  @Inject Navigator mNavigator;

  //Toolbar Views
  @BindView(R.id.toolbar) Toolbar mToolbar;

  //Content Views
  @BindView(R.id.customers_list) RecyclerView mCustomerListView;

  //Data Loading Views
  @BindView(R.id.progress_layout) RelativeLayout mProgressView;
  @BindView(R.id.retry_layout) RelativeLayout mRetryView;
  @BindView(R.id.retry_button) Button mRetryButton;

  private CheckBox mSortMenuCheckBox;

  private CustomerAdapter mCustomersListAdapter;
  private CustomerAdapter.OnItemClickListener onItemClickListener = customerModel -> {
    if (CustomersListActivity.this.mCustomersListPresenter != null && customerModel != null) {
      CustomersListActivity.this.mCustomersListPresenter.onCustomerClicked(customerModel);
    }
  };

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_list);

    initialize();
    loadCustomerList();
    setUpUI();
  }

  private void initialize() {
    mCustomersListPresenter.setView(this);
  }

  private void setUpUI() {
    setSupportActionBar(mToolbar);
    getSupportActionBar().setTitle("");

    mCustomerListView.setLayoutManager(new LinearLayoutManager(this));

    mCustomersListAdapter = new CustomerAdapter(this, new ArrayList<>());
    mCustomersListAdapter.setOnItemClickListener(onItemClickListener);
    mCustomerListView.setAdapter(mCustomersListAdapter);

    mRetryButton.setOnClickListener(v -> onButtonRetryClick());
  }

  void onButtonRetryClick() {
    loadCustomerList();
  }

  /**
   * Loads all customers.
   */
  private void loadCustomerList() {
    mCustomersListPresenter.initialize();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.customer_list, menu);
    setUpSearchMenu(menu);
    setUpSortMenu(menu);
    return true;
  }

  private void setUpSortMenu(Menu menu) {
    mSortMenuCheckBox = (CheckBox) menu.
        findItem(R.id.menu_sort).
        getActionView().
        findViewById(R.id.sort_toggle_button);

    mSortMenuCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
      mCustomersListPresenter.sort(!isChecked);
    });
  }

  private void setUpSearchMenu(Menu menu) {
    // Associate searchable configuration with the SearchView
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override public boolean onQueryTextChange(String newText) {
        mCustomersListPresenter.searchByTitle(newText);
        return true;
      }
    });
    searchView.setOnCloseListener(() -> {
      mCustomersListPresenter.searchByTitle(null);
      return false;
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.menu_sort) {
      onSortMenuSelected();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void onSortMenuSelected() {
    mSortMenuCheckBox.toggle();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mCustomersListPresenter.destroy();
  }

  @Override public void showLoading() {
    mProgressView.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    mProgressView.setVisibility(View.GONE);
  }

  @Override public void showRetry() {
    mRetryView.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    mRetryView.setVisibility(View.GONE);
  }

  @Override public void showError(String message) {
    showToastMessage(message);
  }

  protected void showToastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override public void renderCustomerList(Collection<CustomerModel> customerModelCollection) {
    if (customerModelCollection != null) {
      this.mCustomersListAdapter.setCustomersCollection(customerModelCollection);
    }
  }

  @Override public void viewCustomer(CustomerModel customerModel) {
    mNavigator.navigateToCustomerDetails(this, customerModel.getId());
  }

  @Override public void highlightTextInList(String textToHighlight) {
    mCustomersListAdapter.highlightText(textToHighlight);
  }
}
