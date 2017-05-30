package com.bookatable.di;

import com.bookatable.common.steps.PageObject;
import com.bookatable.pages.CustomerListPage;
import com.bookatable.presentation.di.AppModule;
import dagger.Module;

@Module(injects = { PageObject.class, CustomerListPage.class }, addsTo = AppModule.class)
public class TestsModule {
}
