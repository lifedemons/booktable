package com.bookatable.data.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m") @DatabaseTable(tableName = "customers") public class CustomerEntity {

  @SerializedName(Fields.ID) @Getter @Setter @DatabaseField(id = true, columnName = Fields.ID)
  private int mId;
  @SerializedName(Fields.FIRST_NAME) @Getter @Setter @DatabaseField(columnName = Fields.FIRST_NAME)
  private String mFirstName;
  @SerializedName(Fields.LAST_NAME) @Getter @Setter @DatabaseField(columnName = Fields.LAST_NAME)
  private String mLastName;

  public interface Fields {
    String ID = "id";
    String FIRST_NAME = "customerFirstName";
    String LAST_NAME = "customerLastName";
  }
}