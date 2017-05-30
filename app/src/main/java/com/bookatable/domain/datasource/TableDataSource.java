package com.bookatable.domain.datasource;

import com.bookatable.data.entity.Table;
import java.util.List;
import rx.Completable;
import rx.Single;

/**
 * Interface that represents a DataSource for getting {@link Table} related data.
 * The implementation of this interface should hide/encapsulate details of the source of Data.
 * Upper Layers of the App should not know about DB or Rest sources of data.
 */
public interface TableDataSource {
  /**
   * Get an {@link rx.Observable} which will emit a List of {@link Table}.
   */
  Single<List<Table>> getTables();

  /**
   * Updates list of {@link Table} in DataSource and notifies about completion via Completable
   * @param table table to be updated
   */
  Completable update(List<Table> table);
}
