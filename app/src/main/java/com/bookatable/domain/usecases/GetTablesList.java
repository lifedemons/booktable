package com.bookatable.domain.usecases;

import com.bookatable.data.datasource.TableEntityDataSource;
import com.bookatable.data.entity.Table;
import java.util.List;
import javax.inject.Inject;
import rx.Single;

public class GetTablesList {
  private TableEntityDataSource mEntityDataSource;

  @Inject public GetTablesList(TableEntityDataSource tableEntityDataSource) {
    mEntityDataSource = tableEntityDataSource;
  }

  public Single<List<Table>> call() {
    return mEntityDataSource.getTables();
  }
}
