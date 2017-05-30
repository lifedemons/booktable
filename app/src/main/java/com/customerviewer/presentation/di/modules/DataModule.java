package com.customerviewer.presentation.di.modules;

import com.customerviewer.data.repository.CustomerEntityDataSource;
import com.customerviewer.domain.repository.CustomerRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, complete = false) public class DataModule {

  @Provides @Singleton
  public CustomerRepository providesCustomerRepository(CustomerEntityDataSource repository) {
    return repository;
  }
}
