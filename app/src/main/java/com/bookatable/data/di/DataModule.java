package com.bookatable.data.di;

import com.bookatable.data.datasource.CustomerEntityDataSource;
import com.bookatable.domain.datasource.CustomerDataSource;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, complete = false) public class DataModule {

  @Provides @Singleton
  public CustomerDataSource providesCustomerRepository(CustomerEntityDataSource repository) {
    return repository;
  }
}
