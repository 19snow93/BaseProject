package com.cpgc.baseproject.utils;

/**
 * 预处理工具类，比如预先判断变量引用非空
 * Created by chenmingzhen on 16-6-6.
 */
public class Preconditions {

    /**
     * 默认的判断引用非空方法
     *
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     *  检查非空,如果为空，返回默认数据
     * @param reference
     * @param defaultVal
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference, T defaultVal) {
        try {
            if (reference == null) { //这里可能有发生一些异常，如拆箱和装箱操作,传入了空值等
                return defaultVal;
            }
        } catch (NullPointerException e1) {
            return defaultVal;
        } catch (Exception e) {
            return defaultVal;
        }

        return reference;
    }

}
