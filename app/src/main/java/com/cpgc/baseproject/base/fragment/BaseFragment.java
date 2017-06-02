package com.cpgc.baseproject.base.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cpgc.baseproject.base.activity.BaseActivity;
import com.cpgc.baseproject.utils.ActivityTransition;

import butterknife.Unbinder;

/**
 * Created by leo on 2017/5/15.
 */

public abstract class BaseFragment extends Fragment {

    private static final String FRAGMENTATION_STATE_SAVE_IS_HIDDEN = "fragmentation_state_save_status";

    private boolean mIsHidden = true;   // 用于记录Fragment show/hide 状态

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            mIsHidden = savedInstanceState.getBoolean(FRAGMENTATION_STATE_SAVE_IS_HIDDEN);
        }
    }

    /**
     *
     * @param intent
     * @param transition {@see ActivityTransition}
     */
    protected void startActivity(Intent intent, ActivityTransition transition) {
        intent.putExtra(ActivityTransition.TRANSITION_ANIMATION, transition.ordinal());

        super.startActivity(intent);
        getActivity().overridePendingTransition(transition.inAnimId, transition.outAnimId);
    }

    @Override
    public void startActivity(Intent intent) {
        ActivityTransition transition = ActivityTransition.getDefaultInTransition();
        intent.putExtra(ActivityTransition.TRANSITION_ANIMATION, transition.ordinal());

        super.startActivity(intent);
        getActivity().overridePendingTransition(transition.inAnimId, transition.outAnimId);
    }

    public void startActivityForResult(Intent intent, int requestCode, ActivityTransition transition) {
        intent.putExtra(ActivityTransition.TRANSITION_ANIMATION, transition.ordinal());
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(transition.inAnimId, transition.outAnimId);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        ActivityTransition transition = ActivityTransition.getDefaultInTransition();
        intent.putExtra(ActivityTransition.TRANSITION_ANIMATION, transition.ordinal());
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(transition.inAnimId, transition.outAnimId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FRAGMENTATION_STATE_SAVE_IS_HIDDEN, isHidden());
    }

    /**
     * 如果使用的是项目中的共用Toolbar
     * @param toolbar
     * @param title
     */
    public void setToolBar(Toolbar toolbar, String title) {
        ((BaseActivity)getActivity()).setToolBar(toolbar, title);
    }

    /**
     * 仅在内存重启后有意义(saveInstanceState!=null时)
     *
     * @return Fragment状态 hide : show
     */
    public boolean isSupportHidden() {
        return mIsHidden;
    }

}
