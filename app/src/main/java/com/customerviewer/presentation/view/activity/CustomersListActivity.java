package com.customerviewer.presentation.view.activity;

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
import com.customerviewer.R;
import com.customerviewer.presentation.model.CustomerModel;
import com.customerviewer.presentation.navigation.Navigator;
import com.customerviewer.presentation.presenter.CustomerListPresenter;
import com.customerviewer.presentation.view.CustomerListView;
import com.customerviewer.presentation.view.adapter.CustomerAdapter;
import com.customerviewer.presentation.view.utils.ImageRoundedCornersTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;

public class CustomersListActivity extends DiAppCompatActivity implements CustomerListView {

    @Inject CustomerListPresenter mCustomerListPresenter;
    @Inject Navigator mNavigator;
    @Inject Picasso mPicasso;

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
    private Transformation mImageTransformation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        initialize();
        loadCustomerList();
        setUpUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            mCustomerListPresenter.sort(!isChecked);
        });
    }

    private void setUpSearchMenu(Menu menu) {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCustomerListPresenter.searchByTitle(newText);
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            mCustomerListPresenter.searchByTitle(null);
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    public void onResume() {
        super.onResume();
        mCustomerListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCustomerListPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCustomerListPresenter.destroy();
    }

    private void initialize() {
        mCustomerListPresenter.setView(this);
    }

    private void setUpUI() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        mCustomerListView.setLayoutManager(new LinearLayoutManager(this));

        mCustomersListAdapter = new CustomerAdapter(this, new ArrayList<>(), mPicasso);
        mCustomersListAdapter.setOnItemClickListener(onItemClickListener);
        mCustomerListView.setAdapter(mCustomersListAdapter);

        mRetryButton.setOnClickListener(v -> onButtonRetryClick());

        setupTransformation();
    }

    private void setupTransformation() {
        int radius = (int) getResources().getDimension(R.dimen.list_view_row_icon_rounding_radius);
        mImageTransformation = new ImageRoundedCornersTransformation(radius, 0);
    }

    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        mProgressView.setVisibility(View.GONE);
        setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        mRetryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mRetryView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    protected void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Loads all customers.
     */
    private void loadCustomerList() {
        mCustomerListPresenter.initialize();
    }

    void onButtonRetryClick() {
        loadCustomerList();
    }

    private CustomerAdapter.OnItemClickListener onItemClickListener =
            new CustomerAdapter.OnItemClickListener() {
                @Override
                public void onItemClicked(CustomerModel customerModel) {
                    if (CustomersListActivity.this.mCustomerListPresenter != null && customerModel != null) {
                        CustomersListActivity.this.mCustomerListPresenter.onCustomerClicked(customerModel);
                    }
                }
            };

    @Override
    public void renderCustomerList(Collection<CustomerModel> customerModelCollection) {
        if (customerModelCollection != null) {
            this.mCustomersListAdapter.setCustomersCollection(customerModelCollection);
        }
    }

    @Override
    public void viewCustomer(CustomerModel customerModel) {
        mNavigator.navigateToCustomerDetails(this, customerModel.getId());
    }

    @Override
    public void highlightTextInList(String textToHighlight) {
        mCustomersListAdapter.highlightText(textToHighlight);
    }
}
