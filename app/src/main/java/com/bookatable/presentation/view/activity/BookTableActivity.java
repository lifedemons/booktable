package com.bookatable.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.bookatable.R;
import com.bookatable.data.entity.Customer;
import com.bookatable.data.entity.Table;
import com.bookatable.presentation.presenter.BookTablePresenter;
import com.bookatable.presentation.view.BookTableView;
import com.bookatable.presentation.view.adapter.TableAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Activity that shows details of a certain customer.
 */
public class BookTableActivity extends DiAppCompatActivity implements BookTableView {

  private static final String INTENT_EXTRA_PARAM_CUSTOMER_ID = "INTENT_PARAM_CUSTOMER_ID";
  private static final String INSTANCE_STATE_PARAM_CUSTOMER_ID = "STATE_PARAM_CUSTOMER_ID";

  @Inject BookTablePresenter mBookTablePresenter;

  //Toolbar Views
  @BindView(R.id.toolbar) Toolbar mToolbar;

  //Content Views
  @BindView(R.id.first_name_text_view) TextView mTextViewFirstName;
  @BindView(R.id.last_name_text_view) TextView mTextViewLastName;
  @BindView(R.id.tables_list) RecyclerView mTableListView;

  //Data Loading Views
  @BindView(R.id.progress_layout) RelativeLayout mProgressView;
  @BindView(R.id.retry_layout) RelativeLayout mRetryView;
  @BindView(R.id.retry_button) Button mRetryButton;

  private TableAdapter mTablesListAdapter;

  private TableAdapter.OnItemClickListener onItemClickListener = table -> {
    if (mBookTablePresenter != null && table != null) {
      mBookTablePresenter.onTableClicked(table);
    }
  };

  private int mCustomerId;

  public static Intent getCallingIntent(Context context, int customerId) {
    Intent callingIntent = new Intent(context, BookTableActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_CUSTOMER_ID, customerId);

    return callingIntent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_book_table);

    initialize(savedInstanceState);
    setUpUI();
  }

  /**
   * Initializes this activity.
   */
  private void initialize(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.mCustomerId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CUSTOMER_ID, -1);
    } else {
      this.mCustomerId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CUSTOMER_ID);
    }
    mBookTablePresenter.setView(this);
    mBookTablePresenter.initialize(mCustomerId);
  }

  private void setUpUI() {
    setUpToolbar();

    mTableListView.setLayoutManager(new GridLayoutManager(this, 3));

    mTablesListAdapter = new TableAdapter(this, new ArrayList<>());
    mTablesListAdapter.setOnItemClickListener(onItemClickListener);
    mTableListView.setAdapter(mTablesListAdapter);

    mRetryButton.setOnClickListener(v -> onButtonRetryClick());
  }

  private void setUpToolbar() {
    setSupportActionBar(mToolbar);
    getSupportActionBar().setTitle(getString(R.string.activity_title_book_table));
  }

  void onButtonRetryClick() {
    loadCustomerDetails();
  }

  /**
   * Loads all customers.
   */
  private void loadCustomerDetails() {
    if (mBookTablePresenter != null) {
      mBookTablePresenter.initialize(mCustomerId);
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (outState != null) {
      outState.putInt(INSTANCE_STATE_PARAM_CUSTOMER_ID, mCustomerId);
    }
    super.onSaveInstanceState(outState);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mBookTablePresenter.destroy();
  }

  @Override public void showLoading() {
    mProgressView.setVisibility(View.VISIBLE);
    setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    mProgressView.setVisibility(View.GONE);
    setProgressBarIndeterminateVisibility(false);
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

  private void showToastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override public void showCustomer(Customer customerModel) {
    if (customerModel != null) {
      mTextViewFirstName.setText(customerModel.getFirstName());
      mTextViewLastName.setText(customerModel.getLastName());
    }
  }

  @Override public void showTables(List<Table> tables) {
    if (tables != null) {
      mTablesListAdapter.setTables(tables);
    }
  }

  @Override public void redraw() {
    mTablesListAdapter.notifyDataSetChanged();
  }
}
