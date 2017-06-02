package com.cpgc.baseproject.ui.Test;

import com.cpgc.baseproject.base.bean.WXItemBean;
import com.cpgc.baseproject.base.mvp.BasePresenter;
import com.cpgc.baseproject.base.mvp.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by leo on 2017/6/1.
 */

public interface Test1Contract {
    interface View extends BaseView {
        void testSuccess(List<WXItemBean> task);

    }

    interface Presenter extends BasePresenter<Test1Contract.View> {
        void getWXHot(String key, int num, int page);
    }
}
