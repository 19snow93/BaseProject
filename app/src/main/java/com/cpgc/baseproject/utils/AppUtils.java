package com.cpgc.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;

import java.io.File;
import java.util.UUID;

/**
 * 跟App相关的辅助类,可以获取到App的一些安装信息和进行一些App的操作
 * Created by chenmingzhen on 16-6-1.
 */
public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();

    //工具类，不允许实例化
    private AppUtils() {
        /**cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null) {
            return null;
        }
        int labelRes = packageInfo.applicationInfo.labelRes;
        return context.getResources().getString(labelRes);
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null) {
            return null;
        }
        return packageInfo.versionName;
    }

    /**
     * 获取到versionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null) {
            return 0;
        }
        return packageInfo.versionCode;
    }

    /**
     * 获取packageName
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null) {
            return "";
        }
        return packageInfo.packageName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取设备唯一标识, 即时通讯可能会用到
     *
     * @param context
     * @return
     */
    public static String getUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();

        return uniqueId;
    }

    /**
     * 安装app
     */
    public static void installApp(Context context, String filePath) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filePath)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static void unInstallApp(Activity context, String pkgName) {
        Intent i = new Intent();
        Uri uri = Uri.parse("package:" + pkgName);//获取删除包名的URI
        i.setAction(Intent.ACTION_DELETE);//设置我们要执行的卸载动作
        i.setData(uri);//设置获取到的URI
        context.startActivity(i);
    }

    public static String getAPKPkgName(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info.applicationInfo.packageName;
        }
        return "";
    }

}
