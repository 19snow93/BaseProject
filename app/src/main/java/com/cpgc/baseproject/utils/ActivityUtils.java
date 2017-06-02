package com.cpgc.baseproject.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.HashSet;
import java.util.Set;

import static com.cpgc.baseproject.utils.Preconditions.checkNotNull;


/**
 * 与Activity的基本操作相关的工具类
 * Created by chenmingzhen on 16-6-7.
 */
public class ActivityUtils {

    private static Set<Activity> mActivities;

    public static void initActivities(Set<Activity> activities) {
        mActivities = activities;
    }

    /**
     * 将Fragment附加到Activity中
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void showFragmentOfActivity(@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    public static void hideFragmentOfActivity(@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }

    /**
     * 添加Activity到自己的任务管理栈中
     *
     * @param activity 被压入栈的activity
     */
    public static void addActivity(@NonNull Activity activity) {
        if (mActivities == null) {
            mActivities = new HashSet<>();
        }
        mActivities.add(activity);
    }

    /**
     * 弹出任务管理栈中的Activity
     */
    public static void removeActivity(@NonNull Activity activity) {
        if (mActivities != null && mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    /**
     * 结束所有的Activity,退出程序
     */
    public static void removeAllActivity() {
        if (mActivities != null) {
            synchronized (mActivities) {
                for (Activity activity : mActivities) {
                    activity.finish();
                }
            }
            mActivities.clear();
        }
    }

}
