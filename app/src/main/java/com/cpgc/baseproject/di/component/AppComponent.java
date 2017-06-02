package com.cpgc.baseproject.di.component;

import com.cpgc.baseproject.app.APPApplication;
import com.cpgc.baseproject.di.module.AppModule;
import com.cpgc.baseproject.di.module.HttpModule;
import com.cpgc.baseproject.http.Retrofit2Helper;
import com.cpgc.baseproject.http.RetrofitHelper;
import com.cpgc.baseproject.http.api.TestApi;
import com.cpgc.baseproject.model.pres.ImplPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by leo on 2017/5/16.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    APPApplication getContext(); // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类

    TestApi getTestApi();

    ImplPreferencesHelper preferencesHelper(); //提供sp帮助类
}
