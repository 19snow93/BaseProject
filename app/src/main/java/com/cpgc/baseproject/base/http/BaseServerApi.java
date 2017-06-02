package com.cpgc.baseproject.base.http;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by leo on 2017/5/24.
 * Detail 默认基础的api 提供 get post download upload
 */

public interface BaseServerApi {

    @POST("{path}")
    Observable<ResponseBody> post(
            @Path(value = "path", encoded = true) String path,
            @QueryMap Map<String, Object> map);

    @GET("{path}")
    Observable<ResponseBody> get(
            @Path(value = "path", encoded = true) String path,
            @QueryMap Map<String, Object> map);

    @POST("{path}")
    Observable<ResponseBody> postJSON(
            @Path(value = "path", encoded = true) String path,
            @Body RequestBody route);

    @GET("{path}")
    Observable<ResponseBody> getJSON(
            @Path(value = "path", encoded = true) String path,
            @Body RequestBody route);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFile(
            @Url String url,
            @PartMap() Map<String, RequestBody> maps);

    //支持大文件
    @GET
    Observable<ResponseBody> downloadFile(
            @Url String fileUrl);

}
