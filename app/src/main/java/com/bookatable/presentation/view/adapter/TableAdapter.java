package com.bookatable.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.bookatable.data.entity.Table;
import com.bookatable.presentation.view.TableView;
import java.util.Collection;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

  private final Context mContext;
  private List<Table> mCustomersList;
  private OnItemClickListener mOnItemClickListener;

  public TableAdapter(Context context, List<Table> customersList) {
    mContext = context;
    mCustomersList = customersList;
  }

  public void setTables(List<Table> tables) {
    validateCustomersCollection(tables);
    mCustomersList = tables;
    notifyDataSetChanged();
  }

  private void validateCustomersCollection(Collection<Table> tables) {
    if (tables == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  @Override public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TableViewHolder(new TableView(mContext));
  }

  @Override public void onBindViewHolder(TableViewHolder holder, int position) {
    final Table table = mCustomersList.get(position);
    holder.mTableView.showTable(table);

    holder.itemView.setOnClickListener(v -> {
      if (mOnItemClickListener != null) {
        mOnItemClickListener.onItemClicked(table);
      }
    });
  }

  @Override public int getItemCount() {
    return (mCustomersList != null) ? mCustomersList.size() : 0;
  }

  public interface OnItemClickListener {
    void onItemClicked(Table table);
  }

  static class TableViewHolder extends RecyclerView.ViewHolder {
    private TableView mTableView;

    public TableViewHolder(TableView itemView) {
      super(itemView);
      mTableView = itemView;
    }
  }
}
