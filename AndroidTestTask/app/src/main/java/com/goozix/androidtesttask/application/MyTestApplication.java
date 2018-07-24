package com.goozix.androidtesttask.application;

import android.app.Application;

import com.goozix.androidtesttask.di.AppComponent;
import com.goozix.androidtesttask.di.DaggerAppComponent;
import com.goozix.androidtesttask.di.modules.ContextModule;

public class MyTestApplication extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        buildAppComponent();
    }

    private void buildAppComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
