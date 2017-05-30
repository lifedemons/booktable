package com.bookatable.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bookatable.R;
import com.bookatable.data.entity.Customer;
import java.util.Collection;
import java.util.List;

/**
 * Adapter that manages a collection of {@link Customer}.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

  private static final String STRING_PREPARED_HIGHLIGHT_MARKUP = "<font color='red'>%s</font>";
  private final LayoutInflater mLayoutInflater;
  private final Context mContext;
  private List<Customer> mCustomersList;
  private OnItemClickListener mOnItemClickListener;
  //Highlighting
  private String mTextToHighlight;

  public CustomerAdapter(Context context, Collection<Customer> customerModelsCollection) {
    validateCustomersCollection(customerModelsCollection);
    mContext = context;
    mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mCustomersList = (List<Customer>) customerModelsCollection;
  }

  private void validateCustomersCollection(Collection<Customer> customerModelsCollection) {
    if (customerModelsCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  public void highlightText(String textToHighlight) {
    mTextToHighlight = textToHighlight;
  }

  @Override public int getItemCount() {
    return (mCustomersList != null) ? mCustomersList.size() : 0;
  }

  @Override public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mLayoutInflater.inflate(R.layout.list_item_customer, parent, false);
    CustomerViewHolder customerViewHolder = new CustomerViewHolder(view);

    return customerViewHolder;
  }

  @Override public void onBindViewHolder(CustomerViewHolder holder, final int position) {
    final Customer customerModel = mCustomersList.get(position);
    setText(holder.mTextViewFirstName, customerModel.getFirstName());
    setText(holder.mTextViewLastName, customerModel.getLastName());

    holder.itemView.setOnClickListener(v -> {
      if (CustomerAdapter.this.mOnItemClickListener != null) {
        CustomerAdapter.this.mOnItemClickListener.onItemClicked(customerModel);
      }
    });
  }

  private void setText(TextView textView, String original) {
    if (mTextToHighlight != null && !mTextToHighlight.isEmpty()) {
      String preparedMarkup = String.format(STRING_PREPARED_HIGHLIGHT_MARKUP, mTextToHighlight);
      String highlighted = original.replaceAll(mTextToHighlight, preparedMarkup);
      textView.setText(Html.fromHtml(highlighted));
    } else {
      textView.setText(original);
    }
  }

  @Override public long getItemId(int position) {
    return position;
  }

  public void setCustomersCollection(Collection<Customer> customerModelsCollection) {
    validateCustomersCollection(customerModelsCollection);
    mCustomersList = (List<Customer>) customerModelsCollection;
    notifyDataSetChanged();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener {
    void onItemClicked(Customer customerModel);
  }

  static class CustomerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.first_name) TextView mTextViewFirstName;
    @BindView(R.id.last_name) TextView mTextViewLastName;

    public CustomerViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
