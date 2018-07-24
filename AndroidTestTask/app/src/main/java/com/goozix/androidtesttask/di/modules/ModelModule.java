package com.goozix.androidtesttask.di.modules;

import android.content.Context;

import com.goozix.androidtesttask.mvp.model.api.ApiInterface;
import com.goozix.androidtesttask.mvp.model.api.ApiModule;
import com.goozix.androidtesttask.mvp.model.api.util.APIUtil;
import com.goozix.androidtesttask.mvp.model.Model;
import com.goozix.androidtesttask.mvp.model.ModelImpl;
import com.goozix.androidtesttask.mvp.model.preferences.SharedPrefsModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ModelModule {

    @Provides
    ApiInterface provideApiInterface() {
        return ApiModule.getApiInterface(APIUtil.getBaseUrl());
    }

    @Provides
    @Singleton
    SharedPrefsModule provideSharedPrefsModule(Context context) {
        return new SharedPrefsModule(context);
    }

    @Provides
    Model provideDataRepository() {
        return new ModelImpl();
    }
}

