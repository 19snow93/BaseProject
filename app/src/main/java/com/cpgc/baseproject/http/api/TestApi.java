package com.cpgc.baseproject.http.api;

import com.cpgc.baseproject.base.bean.WXHttpResponse;
import com.cpgc.baseproject.base.bean.WXItemBean;

import java.util.List;
import java.util.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by leo on 2017/5/16.
 */

public interface TestApi {

    @GET("wxnew")
    rx.Observable<WXHttpResponse<List<WXItemBean>>> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);


}
