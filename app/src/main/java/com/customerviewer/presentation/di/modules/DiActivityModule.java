package com.customerviewer.presentation.di.modules;

import com.customerviewer.presentation.view.activity.DiAppCompatActivity;
import com.customerviewer.presentation.view.activity.CustomerDetailsActivity;
import com.customerviewer.presentation.view.activity.CustomersListActivity;
import dagger.Module;

@Module(injects = {
    DiAppCompatActivity.class, CustomersListActivity.class, CustomerDetailsActivity.class
}, addsTo = AppModule.class) public class DiActivityModule {
}
