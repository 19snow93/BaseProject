package com.cpgc.baseproject.di.module;

import com.cpgc.baseproject.app.APPApplication;
import com.cpgc.baseproject.model.pres.ImplPreferencesHelper;
import com.cpgc.baseproject.model.pres.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by leo on 2017/5/16.
 */

@Module
public class AppModule {

    private final APPApplication appApplication;

    public AppModule(APPApplication appApplication) {
        this.appApplication = appApplication;
    }

    @Provides
    @Singleton
    APPApplication provideApplicationContext() {
        return appApplication;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(ImplPreferencesHelper implPreferencesHelper) {
        return implPreferencesHelper;
    }

}
