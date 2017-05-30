package com.customerviewer.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.customerviewer.R;
import com.customerviewer.presentation.model.CustomerModel;
import com.customerviewer.presentation.presenter.CustomerDetailsPresenter;
import com.customerviewer.presentation.view.CustomerDetailsView;
import javax.inject.Inject;

/**
 * Activity that shows details of a certain customer.
 */
public class CustomerDetailsActivity extends DiAppCompatActivity implements CustomerDetailsView {

    private static final String INTENT_EXTRA_PARAM_CUSTOMER_ID = "INTENT_PARAM_CUSTOMER_ID";
    private static final String INSTANCE_STATE_PARAM_CUSTOMER_ID = "STATE_PARAM_CUSTOMER_ID";

    @Inject CustomerDetailsPresenter mCustomerDetailsPresenter;

    //Content Views
    @BindView(R.id.title_text_view) TextView mTitleTextView;

    //Data Loading Views
    @BindView(R.id.progress_layout) RelativeLayout mProgressView;
    @BindView(R.id.retry_layout) RelativeLayout mRetryView;
    @BindView(R.id.retry_button) Button mRetryButton;

    private int mCustomerId;

    public static Intent getCallingIntent(Context context, int customerId) {
        Intent callingIntent = new Intent(context, CustomerDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CUSTOMER_ID, customerId);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_details);

        initialize(savedInstanceState);
        setUpUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_CUSTOMER_ID, mCustomerId);
        }
        super.onSaveInstanceState(outState);
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
        mCustomerDetailsPresenter.setView(this);
        mCustomerDetailsPresenter.initialize(mCustomerId);
    }

    private void setUpUI() {
//        setSupportActionBar(mToolbar);

        mRetryButton.setOnClickListener(v -> onButtonRetryClick());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mCustomerDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCustomerDetailsPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCustomerDetailsPresenter.destroy();
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

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Loads all customers.
     */
    private void loadCustomerDetails() {
        if (mCustomerDetailsPresenter != null) {
            mCustomerDetailsPresenter.initialize(mCustomerId);
        }
    }

    void onButtonRetryClick() {
        loadCustomerDetails();
    }

    @Override
    public void renderCustomer(CustomerModel customerModel) {
        if (customerModel != null) {
            mTitleTextView.setText(customerModel.getTitle());
        }
    }
}
