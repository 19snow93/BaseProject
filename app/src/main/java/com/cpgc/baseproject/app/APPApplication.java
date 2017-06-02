package com.cpgc.baseproject.app;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

import com.cpgc.baseproject.di.component.AppComponent;
import com.cpgc.baseproject.di.component.DaggerAppComponent;
import com.cpgc.baseproject.di.module.AppModule;
import com.cpgc.baseproject.di.module.HttpModule;
import com.cpgc.baseproject.utils.ActivityUtils;
import com.cpgc.baseproject.utils.CrashHandler;
import com.cpgc.baseproject.utils.Logger;
import com.squareup.leakcanary.LeakCanary;

import java.util.HashSet;

/**
 * Created by leo on 2017/5/16.
 */

public class APPApplication extends Application {


    private static APPApplication sInstance;

    public static AppComponent appComponent;

    public static synchronized APPApplication getApp() {
        if (null == sInstance) {
            throw new NullPointerException("Application实例为空,请于Applocation的onCreate()方法中将其实例化");
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        if (Logger.DEBUG) {
            LeakCanary.install(this);
        }
        //捕捉异常
        CrashHandler.getsInstance().init(sInstance);
        ActivityUtils.initActivities(new HashSet<Activity>()); //初始化actity列表
    }

    public static void exitApp() {
        ActivityUtils.removeAllActivity();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    public static AppComponent getAppComponent(){
        if(appComponent == null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(sInstance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }




}
