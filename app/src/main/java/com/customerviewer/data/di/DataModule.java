package com.customerviewer.data.di;

import com.customerviewer.data.datasource.CustomerEntityDataSource;
import com.customerviewer.domain.datasource.CustomerRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, complete = false) public class DataModule {

  @Provides @Singleton
  public CustomerRepository providesCustomerRepository(CustomerEntityDataSource repository) {
    return repository;
  }
}
