package com.bookatable.data.di;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.data.datasource.TableEntityDataSource;
import com.bookatable.domain.datasource.CustomerDataSource;
import com.bookatable.domain.datasource.TableDataSource;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, complete = false) public class DataModule {

  @Provides @Singleton
  public CustomerDataSource providesCustomerDataSource(CustomerEntityDataSource dataSource) {
    return dataSource;
  }

  @Provides @Singleton
  public TableDataSource providesTablesDataSource(TableEntityDataSource dataSource) {
    return dataSource;
  }
}
