package com.cpgc.baseproject.ui.Test;

import com.cpgc.baseproject.base.bean.WXItemBean;
import com.cpgc.baseproject.base.mvp.BasePresenter;
import com.cpgc.baseproject.base.mvp.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by leo on 2017/5/19.
 */

public interface TestContract {

    interface View extends BaseView{
        void testSuccess(List<WXItemBean> task);

    }

    interface Presenter extends BasePresenter<View>{
        void testWX(String url,Map<String, Object> maps);
    }

}
