package com.cpgc.baseproject.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by chenmingzhen on 16-8-18.
 */
public class ServiceUtils {

    private static final int MAX_RUNNING_SERVICE_NUM = 320; //获取到系统正在运行的服务数

    public static void stopService(Context context, Class className) {
        //判断服务是否还在运行，如果还在运行，退出
        if (ServiceUtils.isServiceWorking(context, className.getName())) {
        //    Logger.i("info", "服务已经启动了！！");
            Intent intent = new Intent();
            intent.setClass(context, className);
            context.stopService(intent);
        //    Logger.i("info", "服务已经结束了！！");
        }
    }

    public static void stopAllService(Context context) {

    }


    public static boolean isServiceWorking(Context context, String className) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(MAX_RUNNING_SERVICE_NUM);
    //    Logger.i("ServiceUtils", runningService.size() + "个服务正在系统运行中");
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(className)) {

            //    Logger.i("ServiceUtils", "找到了相关的Service");
                return true;
            }
        }
        return false;
    }
}
