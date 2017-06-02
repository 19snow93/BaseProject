package com.cpgc.baseproject.di.component;

import android.app.Activity;

import com.cpgc.baseproject.base.di.scope.ActivityScope;
import com.cpgc.baseproject.di.module.ActivityModule;
import com.cpgc.baseproject.ui.Test.Test1Activity;
import com.cpgc.baseproject.ui.Test.TestActivity;

import dagger.Component;

/**
 * Created by leo on 2017/5/16.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(TestActivity testActivity);
    void inject(Test1Activity testActivity);
}
