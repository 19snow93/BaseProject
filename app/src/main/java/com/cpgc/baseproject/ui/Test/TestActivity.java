package com.cpgc.baseproject.ui.Test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.cpgc.baseproject.R;
import com.cpgc.baseproject.adapter.WeChatAdapter;
import com.cpgc.baseproject.app.Contants;
import com.cpgc.baseproject.base.activity.MVPActivity;
import com.cpgc.baseproject.base.bean.WXItemBean;
import com.cpgc.baseproject.base.http.BaseSubscriber;
import com.cpgc.baseproject.base.http.Task;
import com.cpgc.baseproject.base.recyclerview.BaseAdapter;
import com.cpgc.baseproject.http.api.AppURL;
import com.cpgc.baseproject.model.bean.MovieModel;
import com.cpgc.baseproject.widget.SwipeRecyclerView;
import com.cpgc.baseproject.widget.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by leo on 2017/5/19.
 */

public class TestActivity extends MVPActivity<TestPresenter> implements TestContract.View{

    @BindView(R.id.swiperefreshlayout)
    SwipeRecyclerView swipeRecyclerView;

    private RecyclerView recyclerView;
    private WeChatAdapter weChatAdapter;
    private int mPage = 1;
    private List<WXItemBean> wxItemBeanList = new ArrayList<>();

    private Map<String, Object> parameters = new HashMap<String, Object>();

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,TestActivity.class);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {

        recyclerView = swipeRecyclerView.getRecyclerView();

        parameters = new HashMap<>();
        parameters.put("key", Contants.KEY_API);
        parameters.put("num", "10");
        parameters.put("page", mPage);
        mPresenter.testWX(AppURL.WxNews,parameters);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        weChatAdapter = new WeChatAdapter(R.layout.item_wechat,this,wxItemBeanList);
        recyclerView.setAdapter(weChatAdapter);
        View headerView = LayoutInflater.from(this).inflate(R.layout.view_header,null);
        weChatAdapter.setHeaderView(headerView);
        View footerView = LayoutInflater.from(this).inflate(R.layout.view_footer,null);
        weChatAdapter.setFooterView(footerView);

        //下拉设置颜色
        swipeRecyclerView.setSwipeRefreshColor(R.color.colorPrimary);
        //下拉刷新，上拉加载
        swipeRecyclerView.setOnSwipeRecyclerViewListener(new SwipeRecyclerView.OnSwipeRecyclerViewListener() {
            @Override
            public void onRefresh() {
                wxItemBeanList.clear();
                mPage = 1;
                parameters = new HashMap<>();
                parameters.put("key", Contants.KEY_API);
                parameters.put("num", "10");
                parameters.put("page", mPage);
                mPresenter.testWX(AppURL.WxNews,parameters);
            }

            @Override
            public void onLoadNext() {
                parameters = new HashMap<>();
                parameters.put("key", Contants.KEY_API);
                parameters.put("num", "10");
                parameters.put("page", mPage++);
                mPresenter.testWX(AppURL.WxNews,parameters);
            }
        });
        //点击事件
        weChatAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast(position + "");
            }
        });

    }

    @Override
    public void testSuccess(List<WXItemBean> task) {
        wxItemBeanList.addAll(task);
        weChatAdapter.notifyDataSetChanged();
        swipeRecyclerView.onLoadFinish();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }
}
