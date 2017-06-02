package com.cpgc.baseproject.ui.Test;

import android.util.Log;

import com.cpgc.baseproject.base.bean.WXItemBean;
import com.cpgc.baseproject.base.http.BaseSubscriber;
import com.cpgc.baseproject.base.mvp.RxPresenter;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by leo on 2017/5/19.
 */

public class TestPresenter extends RxPresenter<TestContract.View> implements TestContract.Presenter{

    @Inject
    public TestPresenter(){

    }

    @Override
    public void testWX(String url, Map<String, Object> maps) {
        retrofitHelper.Get(url, maps, new BaseSubscriber<List<WXItemBean>>() {

            @Override
            public void onNextT(List<WXItemBean> task) {
                mView.testSuccess(task);

            }

            @Override
            public void onErrorT(String msg) {
                Log.e("_onError",msg);
            }
        });
    }
}
