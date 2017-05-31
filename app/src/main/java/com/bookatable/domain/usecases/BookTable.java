package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.TableEntityDataSource;
import com.bookatable.data.entity.Customer;
import com.bookatable.data.entity.Table;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;
import rx.Completable;

public class BookTable {
  private TableEntityDataSource mEntityDataSource;

  @Inject public BookTable(TableEntityDataSource tableEntityDataSource) {
    mEntityDataSource = tableEntityDataSource;
  }

  public Completable call(Table table, Customer customer) {
    return mEntityDataSource.update(Collections.singletonList(bookTable(table, customer)));
  }

  private Table bookTable(Table table, Customer customer) {
    table.setIsBooked(true);
    table.setCustomerId(customer.getId());

    return table;
  }
}
