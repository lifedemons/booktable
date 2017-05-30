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
import com.bookatable.presentation.model.CustomerModel;
import java.util.Collection;
import java.util.List;

/**
 * Adapter that manages a collection of {@link CustomerModel}.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

  private final LayoutInflater mLayoutInflater;
  private final Context mContext;
  private List<CustomerModel> mCustomerModelsList;
  private OnItemClickListener mOnItemClickListener;

  //Highlighting
  private String mTextToHighlight;
  private static final String STRING_PREPARED_HIGHLIGHT_MARKUP = "<font color='red'>%s</font>";

  public CustomerAdapter(Context context, Collection<CustomerModel> customerModelsCollection) {
    validateCustomersCollection(customerModelsCollection);
    mContext = context;
    mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mCustomerModelsList = (List<CustomerModel>) customerModelsCollection;
  }

  public void highlightText(String textToHighlight) {
    mTextToHighlight = textToHighlight;
  }

  @Override public int getItemCount() {
    return (mCustomerModelsList != null) ? mCustomerModelsList.size() : 0;
  }

  @Override public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mLayoutInflater.inflate(R.layout.list_item_customer, parent, false);
    CustomerViewHolder customerViewHolder = new CustomerViewHolder(view);

    return customerViewHolder;
  }

  @Override public void onBindViewHolder(CustomerViewHolder holder, final int position) {
    final CustomerModel customerModel = mCustomerModelsList.get(position);
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

  public void setCustomersCollection(Collection<CustomerModel> customerModelsCollection) {
    validateCustomersCollection(customerModelsCollection);
    mCustomerModelsList = (List<CustomerModel>) customerModelsCollection;
    notifyDataSetChanged();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  private void validateCustomersCollection(Collection<CustomerModel> customerModelsCollection) {
    if (customerModelsCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  public interface OnItemClickListener {
    void onItemClicked(CustomerModel customerModel);
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
