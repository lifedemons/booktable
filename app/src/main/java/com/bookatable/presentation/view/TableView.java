package com.bookatable.presentation.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bookatable.R;
import com.bookatable.data.entity.Customer;
import com.bookatable.data.entity.Table;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class TableView extends FrameLayout {

  @Getter private Table mTable;

  @BindView(R.id.view_table_state_text) TextView mStateText;

  private int mAvailableColor;
  private int mBookedColor;

  public TableView(@NonNull Context context) {
    super(context);
    init();
  }

  public TableView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public TableView(@NonNull Context context, @Nullable AttributeSet attrs,
      @AttrRes int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    inflate(getContext(), R.layout.view_table, this);
    initColors();
    ButterKnife.bind(this);
  }

  private void initColors() {
    mAvailableColor = getResources().getColor(R.color.view_table_available);
    mBookedColor = getResources().getColor(R.color.view_table_booked);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void showTable(Table table) {
    mTable = table;

    requestLayout();
    invalidate();

    if (table.getIsBooked()) {
      showBooked(table.getCustomer());
    } else {
      showAvailable();
    }
  }

  private void showBooked(Customer customer) {
    setBackgroundColor(mBookedColor);

    if(customer != null) {
      mStateText.setText(customer.getLastName());
    } else {
      mStateText.setText(getResources().getString(R.string.view_table_booked));
    }

    setClickable(false);
  }

  private void showAvailable() {
    setBackgroundColor(mAvailableColor);
    mStateText.setText(getResources().getString(R.string.view_table_available));

    setClickable(true);
  }
}
