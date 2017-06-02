package com.cpgc.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 屏幕设置相关类
 * Created by chenmingzhen on 16-6-6.
 */
public class ScreenUtils {

    private static int screenWidth, screenHeight, statusBarHeight; //状态栏高度

    private ScreenUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        if (screenWidth <= 0) {
            Display display = ((WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            screenWidth = outMetrics.widthPixels;
        }
        return screenWidth;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        if (screenHeight <= 0) {
            Display display = ((WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            screenHeight = outMetrics.heightPixels;
        }
        return screenHeight;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Activity act) {
        if (statusBarHeight <= 0) {
            View window = act.getWindow().findViewById(
                    Window.ID_ANDROID_CONTENT);
            Rect rect = new Rect();
            window.getWindowVisibleDisplayFrame(rect);
            statusBarHeight = rect.top;
        }
        return statusBarHeight;
    }
}
