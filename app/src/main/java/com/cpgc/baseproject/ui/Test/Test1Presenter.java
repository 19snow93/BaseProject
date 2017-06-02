package com.cpgc.baseproject.ui.Test;

import com.cpgc.baseproject.base.bean.WXHttpResponse;
import com.cpgc.baseproject.base.bean.WXItemBean;
import com.cpgc.baseproject.base.http.BaseSubscriber1;
import com.cpgc.baseproject.base.http.RxUtil;
import com.cpgc.baseproject.base.mvp.RxPresenter;
import com.cpgc.baseproject.http.api.TestApi;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;


/**
 * Created by leo on 2017/6/1.
 */

public class Test1Presenter extends RxPresenter<Test1Contract.View> implements Test1Contract.Presenter{

    private TestApi testApi;

    @Inject
    public Test1Presenter(TestApi testApi){
        this.testApi = testApi;
    }

    @Override
    public void getWXHot(String key, int num, int page) {
        Subscription subscribe = testApi.getWXHot(key, num, page)
                .compose(RxUtil.<WXHttpResponse<List<WXItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WXItemBean>>handleResult1())
                .subscribe(new BaseSubscriber1<List<WXItemBean>>() {
                    @Override
                    public void onNextT(List<WXItemBean> wxItemBeen) {
                        mView.testSuccess(wxItemBeen);
                    }

                    @Override
                    public void onErrorT(String msg) {

                    }
                });
        addSubscrebe(subscribe);
    }
}
