package com.bookatable.presentation.di;

import com.bookatable.presentation.view.activity.DiAppCompatActivity;
import com.bookatable.presentation.view.activity.CustomerDetailsActivity;
import com.bookatable.presentation.view.activity.CustomersListActivity;
import dagger.Module;

@Module(injects = {
    DiAppCompatActivity.class, CustomersListActivity.class, CustomerDetailsActivity.class
}, addsTo = AppModule.class) public class DiActivityModule {
}
