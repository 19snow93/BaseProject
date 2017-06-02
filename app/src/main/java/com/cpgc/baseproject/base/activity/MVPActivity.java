package com.cpgc.baseproject.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.cpgc.baseproject.app.APPApplication;
import com.cpgc.baseproject.base.mvp.BasePresenter;
import com.cpgc.baseproject.base.mvp.BaseView;
import com.cpgc.baseproject.di.component.ActivityComponent;
import com.cpgc.baseproject.di.component.DaggerActivityComponent;
import com.cpgc.baseproject.di.module.ActivityModule;
import com.cpgc.baseproject.http.RetrofitHelper;
import com.cpgc.baseproject.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by leo on 2017/5/15.
 */

public abstract class MVPActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;
    @Inject
    protected RetrofitHelper retrofitHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        initEventAndData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        ActivityUtils.removeActivity(this);
    }

    protected ActivityComponent getActivityComponent(){
        return  DaggerActivityComponent.builder()
                .appComponent(APPApplication.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    protected abstract int getLayout();
    protected abstract void initInject();
    protected abstract void initEventAndData();


}
