package com.cpgc.baseproject.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理相关的工具类
 * Created by chenmingzhen on 16-7-21.
 * //todo 添加时间格式化的方法，显示例如，昨天，早晨，等的显示方式
 */
public class TimeUtil {

    public static long timeToServer = 0; //本地时间与服务器时间的时间差

    public static final String YYYY_MM_DD = "yyyy_MM_dd";
    public static final String YYYY_MM_DD_M = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM = "yyyy-MM";

    private static final String TAG = TimeUtil.class.getSimpleName();

    /**
     * 获取当前时间的豪秒数
     *
     * @return
     */
    public static long getCurrentTimeInMillis() {
        return System.currentTimeMillis() - timeToServer;
    }

    /**
     * 获取当前时间，并返回格式化字符串
     *
     * @param format
     * @return
     */
    public static String getCurrentTimeStr(String format) {
        long currentTimeMillis = System.currentTimeMillis();
        return getStringForMillis(currentTimeMillis, format);
    }


    /**
     * 获取一定天数之前的豪秒数
     *
     * @param day
     * @return
     */
    public static long getTimeInMillisDaysBefore(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, (0 - day));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取一定天数之后的豪秒数
     *
     * @param day
     * @return
     */
    public static long getTimeInMillisDaysAfter(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, (day));
        return calendar.getTimeInMillis();
    }

    /**
     * 返回豪秒的时间数
     *
     * @param millis
     * @return
     */
    public static String getStringForMillis(Long millis, String format) {
        if (millis == null) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return dateFormat.format(c.getTime());
    }

    public static int compareTo(long lhs, long rhs) {
        Calendar cLhs = Calendar.getInstance();
        Calendar cRhs = Calendar.getInstance();
        cLhs.setTimeInMillis(lhs);
        cRhs.setTimeInMillis(rhs);
        return cLhs.compareTo(cRhs);
    }

    public static int compareToReverted(long lhs, long rhs) {
        return compareTo(rhs, lhs);
    }

    /**
     * 返回该日期是星期几
     *
     * @param millis
     * @return
     */
    public static String getWeekStr(long millis) {
        Date date = new Date(millis);
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }


    public static long getMillisFromStr(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);

            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getCurrentTimeInMillis();
    }

}
