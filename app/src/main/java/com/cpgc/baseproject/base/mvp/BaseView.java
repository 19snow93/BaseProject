package com.cpgc.baseproject.base.mvp;

/**
 * Created by codeest on 2016/8/2.
 * View基类
 */
public interface BaseView {

    void showToast(String msg);

    void showLoadingDialog(String msg);

    void dismissLoadingDialog();
}
