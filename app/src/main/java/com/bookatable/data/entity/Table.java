package com.bookatable.data.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * This is Data Layer model, which holds info about Json and Database related fields about tables
 */
@Data @Accessors(prefix = "m") @DatabaseTable(tableName = "tables")
public class Table {
  @DatabaseField(id = true, columnName = Fields.ID)
  private Integer mId;

  @SerializedName(Fields.IS_BOOKED) @DatabaseField(columnName = Fields.IS_BOOKED)
  private Boolean mIsBooked;

  @DatabaseField(columnName = Fields.CUSTOMER, foreign = true)
  private Customer mCustomer;

  interface Fields {
    String ID = "id";
    String IS_BOOKED = "isBooked";
    String CUSTOMER = "customer";
  }
}
