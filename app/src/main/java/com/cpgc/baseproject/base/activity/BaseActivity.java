package com.cpgc.baseproject.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cpgc.baseproject.R;
import com.cpgc.baseproject.utils.ActivityTransition;
import com.cpgc.baseproject.utils.ActivityUtils;
import com.cpgc.baseproject.utils.Logger;
import com.cpgc.baseproject.utils.ViewUtils;

/**
 * Created by leo on 2017/5/12.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityTransition transition;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.addActivity(this);//添加该Activity到自定义的任务管理栈中

        //获取上个界面跳转到该界面时的转场动画
        Intent intent = getIntent();
        int ordinal = intent.getIntExtra(ActivityTransition.TRANSITION_ANIMATION, ActivityTransition.getDefaultInTransition().ordinal());
        this.transition = (ActivityTransition.values()[ordinal]) ;
        ViewUtils.setTranslucentWindows(this);
        ViewUtils.StatusBarLightMode(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.removeActivity(this);//弹出该Activity在任务管理栈中的位置
    }


    /**
     * 如果使用的是项目中的共用Toolbar
     * @param toolbar
     * @param title
     */
    public void setToolBar(Toolbar toolbar, String title) {
        //假如使用的是标准的toolbar
        if (toolbar != null && toolbar.getId() == R.id.tool_bar) {
            mToolbar = toolbar;
            try {
                TextView tvTitle = (TextView) toolbar.findViewById(R.id.tool_bar_title);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
            } catch (Exception e) {
                Logger.e(getClass().getSimpleName(), "请设置view_toolbar为标题栏");
            }
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_nav_return);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 设置toolbar
     * @param toolbar
     */
    public void setToolbar(Toolbar toolbar) {
        if(toolbar != null) {
            mToolbar = toolbar;
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.mipmap.ic_nav_return);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }


}
