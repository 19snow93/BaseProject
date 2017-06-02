package com.cpgc.baseproject.utils;

import android.app.Application;

import com.cpgc.baseproject.app.APPApplication;

/**
 * 上下文工具类，最基础的类之一
 * Created by jaminchanks on 2017/2/27.
 */

public class ContextUtil {

    public static Application getApplication() {
        return APPApplication.getApp();
    }

}
