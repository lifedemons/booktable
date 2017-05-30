package com.customerviewer.data.di;

import com.customerviewer.BuildConfig;
import com.customerviewer.data.network.CustomerRestService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(library = true, complete = false) public class ApiModule {

  private static String API_BASE_URL = "https://s3-eu-west-1.amazonaws.com/";

  @Provides @Singleton CustomerRestService provideOrderRatingService(OkHttpClient httpClient) {
    return create(httpClient);
  }

  public static CustomerRestService create(OkHttpClient client) {
    return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .baseUrl(API_BASE_URL)
        .client(client)
        .build()
        .create(CustomerRestService.class);
  }

  @Provides @Singleton OkHttpClient provideHttpClient() {
    return createOkHttpClient();
  }

  private static OkHttpClient createOkHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor interceptor =
          new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(interceptor);
    }
    return builder.build();
  }

}
