package com.customerviewer.presentation.di;

import android.content.Context;
import android.content.res.Resources;
import com.customerviewer.data.di.ApiModule;
import com.customerviewer.data.di.DataModule;
import com.customerviewer.data.di.RxModule;
import com.customerviewer.domain.di.UseCasesModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, includes = {
    ApiModule.class, DataModule.class, UseCasesModule.class, RxModule.class
}) public class AppModule {
  private Context mContext;

  public AppModule(Context context) {
    mContext = context;
  }

  @Provides Context providesApplicationContext() {
    return mContext;
  }

  @Provides @Singleton Resources getApplicationResources() {
    return mContext.getResources();
  }
}
