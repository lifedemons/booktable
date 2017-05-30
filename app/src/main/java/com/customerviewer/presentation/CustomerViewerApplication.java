package com.customerviewer.presentation;

import android.app.Application;
import com.customerviewer.presentation.di.modules.AppModule;
import dagger.ObjectGraph;

public class CustomerViewerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        sObjectGraph = ObjectGraph.create(new AppModule(this));
    }

    private static ObjectGraph sObjectGraph;

    public static ObjectGraph getScopedGraph(Object... modules) {
        return sObjectGraph.plus(modules);
    }
}
