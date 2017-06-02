package com.cpgc.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cpgc.baseproject.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 组件辅助工具类
 * <p/>
 * 去除的方法
 * $ getXmlLinearLayout(Context context,int layoutId)
 * $ getXmlView(Context context,int layoutId)
 * $ initTextView(int id,Activity activity)
 * $ initButton(int id,Activity activity)
 * $ initEditView(int id,Activity activity)
 * $ initListView(int id,Activity activity)
 * $ setFullBar(Activity activity)
 * $ setTranslucentStatus(Activity activity,boolean on)
 * $ getRootView(Activity context)
 * $ setViewClipToPadding(Activity activity)
 * $ changeViewsByString(Context context,List<String> viewpagerViewList,int layoutId)
 * <p/>
 * Author name venco
 * Created on 2016/6/6.
 */
public class ViewUtils {

    private static final String TAG = ViewUtils.class.getSimpleName();

    private ViewUtils() {
        /**cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 检查文本框内容是否为空
     */
    public static boolean checkIsEmpty(EditText edit) {
        if (edit == null) {
            return true;
        } else if (edit.getText().toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 获取文本框中的值
     */
    public static String getEditString(EditText edit) {
        if (edit == null) {
            return null;
        } else {
            return edit.getText().toString().trim();
        }
    }

    /**
     * 判断文本框中的值是否相同
     */
    public static boolean IsSameStr(EditText edit1, EditText edit2) {
        if (ViewUtils.getEditString(edit1).equals(ViewUtils.getEditString(edit2))) {
            return true;
        }
        return false;
    }

    /**
     * 设置组件左边drawable
     */
    public static void setDrawableLeft(View view, int drawableId) {
        try {

            Drawable drawableImg;
            Resources res = view.getContext().getResources();
            drawableImg = res.getDrawable(drawableId);
            //调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            drawableImg.setBounds(0, 0, drawableImg.getMinimumWidth(), drawableImg.getMinimumHeight());

            if (view instanceof Button) {
                ((Button) view).setCompoundDrawables(drawableImg, null, null, null); //设置左图标
            }
            if (view instanceof TextView) {
                ((TextView) view).setCompoundDrawables(drawableImg, null, null, null); //设置左图标
            }
            if (view instanceof EditText) {
                ((EditText) view).setCompoundDrawables(drawableImg, null, null, null); //设置左图标
            }

        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
        }
    }

    /**
     * 设置组件左边drawable
     */
    public static void setDrawableRight(View view, int drawableId) {
        try {

            Drawable drawableImg;
            Resources res = view.getContext().getResources();
            drawableImg = res.getDrawable(drawableId);
            //调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            drawableImg.setBounds(0, 0, drawableImg.getMinimumWidth(), drawableImg.getMinimumHeight());

            if (view instanceof Button) {
                ((Button) view).setCompoundDrawables(null, null, drawableImg, null); //设置左图标
            }
            if (view instanceof TextView) {
                ((TextView) view).setCompoundDrawables(null, null, drawableImg, null); //设置左图标
            }
            if (view instanceof EditText) {
                ((EditText) view).setCompoundDrawables(null, null, drawableImg, null); //设置左图标
            }

        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
        }
    }


    /**
     * 隐藏输入法键盘
     */
    public static void hideInput(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<Activity>(activity);
        hideInput(weakReference);
    }

    /**
     * 隐藏键盘
     */
    public static void hideInput(WeakReference<Activity> activity) {
        InputMethodManager imm = (InputMethodManager) activity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.get().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.get().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 显示输入法键盘
     */
    public static void showInput(WeakReference<Activity> activity, EditText editText) {
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 改变Activity的透明度
     **/
    public static void changeBackGroupAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();//设置背景变暗
        params.alpha = alpha;
        activity.getWindow().setAttributes(params);
    }


    /**
     * 设置透明状态栏
     * @param activity
     */
    static public void setTranslucentWindows(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * 19API以上 读取到状态栏高度才有意义
     *
     * @param context
     * @return
     */
    static public int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
        } else {
            return 0;
        }
    }

    /**
     * 设置状态栏颜色为App主色
     * 配合{@link #setTranslucentWindows(Activity)}方法使用
     * 主要方法为添加一个View并设置背景色添加到系统contentView中
     *
     * @param activity
     */
    static public void addStatusBarBackground(Activity activity) {

        //当API21+：能够调用系统API直接对状态栏着色
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        }

        int height;
        height = getStatusBarHeight(activity);
        if (height <= 0) {
            return;
        }

        FrameLayout layout = (FrameLayout) activity.findViewById(android.R.id.content);
        FrameLayout statusLayout = new FrameLayout(activity);
        statusLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        TypedValue typedValue = new TypedValue();
        TypedArray a = activity.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        statusLayout.setBackgroundColor(color);
        layout.addView(statusLayout);
    }




    /**
     *设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int StatusBarLightMode(Activity activity){
        int result=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(MIUISetStatusBarLightMode(activity.getWindow(), true)){
                result=1;
            }else if(FlymeSetStatusBarLightMode(activity.getWindow(), true)){
                result=2;
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result=3;
            }
        }
        return result;
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity
     * @param type 1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode(Activity activity, int type){
        if(type==1){
            MIUISetStatusBarLightMode(activity.getWindow(), true);
        }else if(type==2){
            FlymeSetStatusBarLightMode(activity.getWindow(), true);
        }else if(type==3){
            activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                     | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    /**
     * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
     */
    public static void StatusBarDarkMode(Activity activity, int type){
        if(type==1){
            MIUISetStatusBarLightMode(activity.getWindow(), false);
        }else if(type==2){
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        }else if(type==3){
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if(dark){
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
                }else{
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result=true;
            }catch (Exception e){

            }
        }
        return result;
    }

}

