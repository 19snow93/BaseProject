package com.cpgc.baseproject.base.http;

import android.util.Log;

import com.cpgc.baseproject.base.bean.WXHttpResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by leo on 2017/5/24.
 */

public class RxUtil {
    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<ResponseBody, T> handleResult() {
        return new Observable.Transformer<ResponseBody, T>() {
            @Override
            public Observable<T> call(Observable<ResponseBody> tObservable) {
                return tObservable.flatMap(
                        new Func1<ResponseBody, Observable<T>>() {
                            @Override
                            public Observable<T> call(ResponseBody responseBody) {
                                try {
                                    String jstr = new String(responseBody.bytes());
                                    Type type = new TypeToken<WXHttpResponse>() {
                                    }.getType();
                                    Log.e("responseBody",jstr);
                                    WXHttpResponse wxHttpResponse = new Gson().fromJson(jstr,type);
                                    if (wxHttpResponse.getCode() == 200) {
                                        Log.e("wxHttpResponse",wxHttpResponse.getNewslist().toString());
                                        return (Observable<T>) Observable.just(wxHttpResponse.getNewslist());
                                    } else {
                                        // 处理被踢出登录情况
                                        return Observable.error(new ReloginException("服务器返回error"));
                                    }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                return Observable.error(new ReloginException("服务器返回error"));
                            }
                        }
                );
            }
        };
    }

    public static <T> Observable.Transformer<WXHttpResponse<T>, T> handleResult1() {
        return new Observable.Transformer<WXHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<WXHttpResponse<T>> tObservable) {
                return tObservable.flatMap(
                        new Func1<WXHttpResponse<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(WXHttpResponse<T> wxHttpResponse) {
                                if (wxHttpResponse.getCode() == 200) {
                                    Log.e("wxHttpResponse",wxHttpResponse.getNewslist().toString());
                                    return (Observable<T>) Observable.just(wxHttpResponse.getNewslist());
                                } else {
                                    // 处理被踢出登录情况
                                    return Observable.error(new ReloginException("服务器返回error"));
                                }
                            }
                        }
                );
            }
        };
    }
}
