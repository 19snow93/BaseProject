package com.cpgc.baseproject.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cpgc.baseproject.R;
import com.cpgc.baseproject.utils.ActivityUtils;

/**
 * 所有托管单一fragment的Activity的父类
 * Created by leo on 2017/5/19.
 */

public abstract class SingleFragmentActivity extends BaseActivity {

    private Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initFragment();
    }

    protected void initContentView() {
        setContentView(getLayoutResId());
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    final protected void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (null == mFragment) {
            mFragment = onCreateFragment();
            ActivityUtils.addFragmentToActivity(fragmentManager, mFragment, R.id.fragmentContainer);
        }
    }

    /**
     * R.layout.activity_fragment和 R.layout.activity_fragment_with_title 有无标题的差别，根据需要设置
     *
     * @return
     */
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    protected abstract Fragment onCreateFragment();

}
