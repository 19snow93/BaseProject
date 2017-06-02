package com.cpgc.baseproject.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpgc.baseproject.base.mvp.BasePresenter;
import com.cpgc.baseproject.base.mvp.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by leo on 2017/5/15.
 */

public abstract class MVPFragment<T extends BasePresenter> extends BaseFragment implements BaseView {
    @Inject
    protected T mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;

    protected  boolean mIsViewInited = false;
    protected boolean mIsVisible = true; //setUserVisible 默认为true
    private boolean mIsLazyLoad = false; //是否懒加载
    private boolean mIsDataInited = false; //数据加载完成

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        initInject();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        mUnBinder = ButterKnife.bind(this, view);
        mIsViewInited = true;
        //不需要懒加载的话直接初始化数据，否则啥也不干
        if (!mIsLazyLoad) {
            initEventAndData();
            mIsDataInited = true;
        }else{
             lazyInitEventAndData();
        }
    }


    /**
     *  子类可通过重写该方法来判断是否需要懒加载
     */
    protected void setFragmentLazyLoad(boolean isLazyLoad) {
        this.mIsLazyLoad = isLazyLoad;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
          mIsVisible = true;
          lazyInitEventAndData();
          onVisible();
        } else {
          mIsVisible = false;
          onInVisible();
        }

    }

    protected void onVisible() {
    }

    protected void onInVisible() {
    }

    /**
     * 懒加载数据
     */
    private void lazyInitEventAndData() {
        if (mIsLazyLoad && mIsViewInited && mIsVisible && !mIsDataInited) {
            //初始化
            initEventAndData();
            mIsDataInited = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsViewInited = false;
        mIsDataInited = false;
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }


    //------------------------ 子类只需重写以下方法-------------------------//

    protected abstract void initInject();

    /**
     * 返回fragment的布局id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 控件的初始化和事件的初始化都在这里实现
     */
    protected abstract void initEventAndData();

}
