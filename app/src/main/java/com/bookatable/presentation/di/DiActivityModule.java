package com.bookatable.presentation.di;

import com.bookatable.presentation.view.activity.BookTableActivity;
import com.bookatable.presentation.view.activity.CustomersListActivity;
import com.bookatable.presentation.view.activity.DiAppCompatActivity;
import dagger.Module;

@Module(injects = {
    DiAppCompatActivity.class, CustomersListActivity.class, BookTableActivity.class
}, addsTo = AppModule.class) public class DiActivityModule {
}
