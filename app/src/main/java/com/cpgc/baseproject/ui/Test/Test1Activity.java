package com.cpgc.baseproject.ui.Test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.cpgc.baseproject.R;
import com.cpgc.baseproject.adapter.WeChatAdapter;
import com.cpgc.baseproject.app.Contants;
import com.cpgc.baseproject.base.activity.MVPActivity;
import com.cpgc.baseproject.base.bean.WXItemBean;
import com.cpgc.baseproject.base.recyclerview.BaseAdapter;
import com.cpgc.baseproject.http.api.AppURL;
import com.cpgc.baseproject.widget.SwipeRecyclerView;
import com.cpgc.baseproject.widget.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by leo on 2017/6/1.
 */

public class Test1Activity extends MVPActivity<Test1Presenter> implements Test1Contract.View{

    @BindView(R.id.swiperefreshlayout)
    SwipeRecyclerView swipeRecyclerView;

    private RecyclerView recyclerView;
    private WeChatAdapter weChatAdapter;
    private int mPage = 1;
    private List<WXItemBean> wxItemBeanList = new ArrayList<>();

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,Test1Activity.class);
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

        mPresenter.getWXHot(Contants.KEY_API,10,mPage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        weChatAdapter = new WeChatAdapter(R.layout.item_wechat,this,wxItemBeanList);
        recyclerView.setAdapter(weChatAdapter);
        //设置头部
        View headerView = LayoutInflater.from(this).inflate(R.layout.view_header,null);
        weChatAdapter.setHeaderView(headerView);
        //设置尾部
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
                mPresenter.getWXHot(Contants.KEY_API,10,mPage);
            }

            @Override
            public void onLoadNext() {
                mPresenter.getWXHot(Contants.KEY_API,10,mPage++);
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