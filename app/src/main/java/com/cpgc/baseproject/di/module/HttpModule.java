package com.cpgc.baseproject.di.module;

import com.cpgc.baseproject.app.APPApplication;
import com.cpgc.baseproject.http.Retrofit2Helper;
import com.cpgc.baseproject.http.RetrofitHelper;
import com.cpgc.baseproject.http.api.AppURL;
import com.cpgc.baseproject.http.api.TestApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by leo on 2017/5/16.
 */
@Module
public class HttpModule  {

    @Provides
    Retrofit provideRetrofit(APPApplication application) {
        String baseUrl = AppURL.BaseURL;
        return Retrofit2Helper.getRetrofit(application,baseUrl);
    }

    @Provides
    TestApi provideTestApi(Retrofit retrofit) {
        return retrofit.create(TestApi.class);
    }


}
