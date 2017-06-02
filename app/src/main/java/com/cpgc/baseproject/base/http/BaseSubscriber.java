package com.cpgc.baseproject.base.http;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created by leo on 2017/5/26.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Task<T> resultTask;
    private String msg;

    public BaseSubscriber(){
        Type type = getSuperClassGenricType(getClass(),0);
        this.resultTask = new Task(type);
    }

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
        if(t != null) {
            Gson gson = new Gson();
            String result = gson.toJson(t);
            resultTask.setJson(result);
            onNextT(resultTask.getResult());
        }
    }

    public abstract void onNextT(T t);

    public abstract void onErrorT(String msg);

    // 获取超类类型
    private Type getSuperclassTypeParameter(Class<?> clazz, int index)
    {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    public static Type getSuperClassGenricType(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Type)) {
            return Object.class;
        }

        return params[index];
    }

}
