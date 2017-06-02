package com.cpgc.baseproject.base.http;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created by leo on 2017/6/1.
 */

public abstract class BaseSubscriber1<T> extends Subscriber<T> {

    private String msg;

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();

        if(throwable instanceof ReloginException){
            // 踢出登录
        }else if (throwable instanceof UnknownHostException) {
            msg = "没有网络...";
        } else if (throwable instanceof SocketTimeoutException) {
            // 超时
            msg = "超时...";
        }else{
            msg = "请求失败，请稍后重试...";
        }
        onErrorT(msg);
    }

    @Override
    public void onNext(T t) {
        onNextT(t);
    }

    public abstract void onNextT(T t);

    public abstract void onErrorT(String msg);
}
