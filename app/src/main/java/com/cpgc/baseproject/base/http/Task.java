package com.cpgc.baseproject.base.http;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by leo on 2017/5/27.
 */

public class Task<T> {
    private String json;
    private T result;

    private Type clzType;

    public Task(Type type) {
        this.clzType = type;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
        Log.e("json",json);
        result = new Gson().fromJson(json,clzType);
    }

    public T getResult() {
        return result;
    }
}
