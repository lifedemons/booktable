package com.bookatable.presentation;

import android.app.Application;
import com.bookatable.presentation.di.AppModule;
import dagger.ObjectGraph;

public class BookATableApplication extends Application {

  private static ObjectGraph sObjectGraph;

  public static ObjectGraph getScopedGraph(Object... modules) {
    return sObjectGraph.plus(modules);
  }

  @Override public void onCreate() {
    super.onCreate();
    sObjectGraph = ObjectGraph.create(new AppModule(this));
  }
}
