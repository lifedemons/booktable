package com.customerviewer.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.customerviewer.R;
import com.customerviewer.presentation.model.CustomerModel;
import com.customerviewer.presentation.view.utils.ImageRoundedCornersTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import java.util.Collection;
import java.util.List;

/**
 * Adapter that manages a collection of {@link CustomerModel}.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
  private final Picasso mPicasso;
  private List<CustomerModel> mCustomerModelsList;
    private OnItemClickListener mOnItemClickListener;
    private Transformation mImageTransformation;


    //Highlighting
    private String mTextToHighlight;
    private static final String STRING_PREPARED_HIGHLIGHT_MARKUP = "<font color='red'>%s</font>";

  public CustomerAdapter(Context context, Collection<CustomerModel> customerModelsCollection,
      Picasso picasso) {
    validateCustomersCollection(customerModelsCollection);
    mContext = context;
    mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mCustomerModelsList = (List<CustomerModel>) customerModelsCollection;
    mPicasso = picasso;

    setupTransformation();
  }

    public void highlightText(String textToHighlight) {
        mTextToHighlight = textToHighlight;
    }

    @Override
    public int getItemCount() {
        return (mCustomerModelsList != null) ? mCustomerModelsList.size() : 0;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_customer, parent, false);
        CustomerViewHolder customerViewHolder = new CustomerViewHolder(view);

        return customerViewHolder;
    }

  @Override public void onBindViewHolder(CustomerViewHolder holder, final int position) {
    final CustomerModel customerModel = mCustomerModelsList.get(position);
    setText(holder.mTextViewTitle, customerModel.getTitle());
    mPicasso.load(customerModel.getThumbnailUrl())
        .placeholder(R.drawable.ic_crop_original_black)
        .error(R.drawable.ic_error_outline_black)
        .transform(mImageTransformation)
        .into(holder.mCustomerImageView);

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

    @Override
    public long getItemId(int position) {
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

    private void setupTransformation() {
        int radius = (int) mContext.getResources().getDimension(R.dimen.list_view_row_icon_rounding_radius);
        mImageTransformation = new ImageRoundedCornersTransformation(radius, 0);
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
        private TextView mTextViewTitle;
        private ImageView mCustomerImageView;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View itemView) {
            mTextViewTitle = (TextView) itemView.findViewById(R.id.title);
            mCustomerImageView = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }
}
