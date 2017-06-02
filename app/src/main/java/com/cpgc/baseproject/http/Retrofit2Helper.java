package com.cpgc.baseproject.http;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.cpgc.baseproject.utils.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leo on 2017/6/1.
 */

public class Retrofit2Helper {
    private static final int CONNECT_TIME_OUT = 20;
    private static final int READ_TIME_OUT = 20;
    private static final int WRITE_TIME_OUT = 20;
    private static final int MAX_CACHE_SIZE = 1024 * 1024 * 50;
    private static final String HTTP_CACHE_DIR = Environment.getExternalStorageDirectory() + "/HTTP_LIBRARY_CACHE";

    private static OkHttpClient mOkHttpClient = null;

    @Inject
    public Retrofit2Helper(Context context) {
        initOkHttp(context);
    }

    /**
     * 获取一个经过普通配置的Retrofit实例
     * @param baseUrl
     * @return
     */
    public static Retrofit getRetrofit(Context context, String baseUrl) {
        initOkHttp(context);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) //自定义的gsonConverter 在里面处理了登录失效处理
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public static void initOkHttp(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (Logger.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        File cacheFile = new File(HTTP_CACHE_DIR);
        Cache cache = new Cache(cacheFile, MAX_CACHE_SIZE);
        Interceptor cacheInterceptor = new CacheInterceptor();
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);

        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
    }

}
