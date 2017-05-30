package com.customerviewer.di;

import com.customerviewer.common.steps.PageObject;
import com.customerviewer.pages.CustomerListPage;
import com.customerviewer.presentation.di.modules.AppModule;
import dagger.Module;

@Module(injects = { PageObject.class, CustomerListPage.class }, addsTo = AppModule.class)
public class TestsModule {
}
