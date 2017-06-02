package com.cpgc.baseproject.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 与字符串操作相关的工具类
 * Created by chenmingzhen on 16-6-7.
 */
public class StringUtils {

    public static final String EMPTY = "";

    private static final String TAG = StringUtils.class.getSimpleName();

    /**
     * 查看一个字符是否为空
     *
     * @param str 需要检查的字条
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否引用为空
     *
     * @param str
     * @return
     */
    public static String checkNotNull(String str) {
        if (str == null) {
            return EMPTY;
        }
        return str;
    }

    public static int getWordCount(String str) {
        str = str.replaceAll("[^\\x00-\\xff]", "**"); //汉字或全角的情况
        int length = str.length();
        return length;
    }

    /**
     * 生成中间省略的字符串
     * 这个方法是为了使得字符串过长的时候进行中间截取成
     * XXX..XXX.DOC 为什么不用系统的ellipse=middle，使用这个属性在某种命名的文件下会导致系统崩溃，这是Android系统的bug.
     *
     * @param content           字符串长度
     * @param max               允许字符串最大值
     * @param allowChineseCount 允许中文最大值
     * @param start             省略开始于
     * @param end               省略结束于
     * @return
     */
    public static String middleEllipse(String content, int max, int allowChineseCount, int start, int end) {
        Integer index = 0;
        int chineseCount = 0;
        for (int i = 0; i < content.length(); i++) {
            String retContent = content.substring(i, i + 1);
            // 生成一个Pattern,同时编译一个正则表达式
            boolean isChina = retContent.matches("[\u4E00-\u9FA5]");
            if (isChina) {
                index = index + 2;
                chineseCount++;
            } else {
                index = index + 1;
            }
        }

        if (index < max) {
            return content;
        }
        StringBuffer sb = new StringBuffer();
        if (chineseCount > allowChineseCount) {
            sb.append(content.substring(0, start + 2));
        } else {
            sb.append(content.substring(0, start + 5));
        }
        sb.append("...");
        sb.append(content.substring(content.length() - end, content.length()));
        return sb.toString();
    }

    /**
     * 压缩字符串
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        long time1 = System.currentTimeMillis();
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        String rs = out.toString("ISO-8859-1");
        long time2 = System.currentTimeMillis();
        System.out.println("compress String coast time:" + (time2 - time1));
        return rs;
    }

    /**
     * 解压缩字符串
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {
        long time1 = System.currentTimeMillis();
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        GZIPInputStream ungzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = ungzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        String rs = out.toString();
        long time2 = System.currentTimeMillis();
        System.out.println("uncompress String coast time:" + (time2 - time1));
        return rs;
    }


    public static String uncompress(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        // System.out.println("bytes..length:"+bytes.length);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        String rs = out.toString("GBK");
        return rs;
    }

    /**
     * 防止字符串编码错误
     *
     * @param value
     * @return
     */
    public static String encode(String value) {
        String result = "";
        try {
            result = java.net.URLEncoder.encode(value, "utf-8");
        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
        }
        return result;
    }


    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * 将字符串转化为int类型
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static int toInt(String str, int defaultValue) {
        if (isEmpty(str))
            return defaultValue;
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            Logger.d(TAG, e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 将字符串转化为long类型
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static long toLong(String str, long defaultValue) {
        if (isEmpty(str))
            return defaultValue;
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
        }
        return defaultValue;
    }

    /**
     * String转化为float
     *
     * @param value
     * @return
     */
    public static float toFloat(String value) {
        if (isEmpty(value))
            return 0;
        try {
            return Float.valueOf(value);
        } catch (NumberFormatException e) {
            Logger.d(TAG, e.getMessage());
        }
        return 0;
    }

    /**
     * 字符在去除空格
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * 验证是否由数字或者英文组成（用于企业端账号名）
     */
    public static boolean isAcount(String acount) {
        try {
            String str = "[a-zA-Z0-9]{1,20}";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(acount);
            return m.matches();
        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 验证是否由数字或者英文组成（用于企业注册号限制）
     */
    public static boolean isCompanyCode(String acount) {
        try {
            String str = "^[0-9A-Za-z]{9,22}$";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(acount);
            return m.matches();
        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 验证是否是邮箱
     */
    public static boolean isEmail(String email) {
        try {
            String str = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(email);
            return m.matches();
        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 验证是否手机号码
     */
    public static boolean isPhone(String phone) {
        try {
            String regExp = "^[1][34578][0-9]{9}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(phone);
            return m.matches();
        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 判断是否为固定电话，需要加入区号
     */
    public static boolean isCall(String call) {
        try {
            String regExp = "^\\d{3,4}-\\d{7,8}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(call);
            return m.matches();
        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 判断是否为正确的邮政编码
     */
    public static boolean isPostCode(String postCode) {
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(postCode).matches();
    }

    /**
     * 校验短信中的验证码：格式正确返回
     */
    public static String getVerifyCode(String VerifyCode) {
        String regExp = "[0-9]{6}";
        try {
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(VerifyCode);
            if (m.find()) {
                return m.group();
            } else {
                return EMPTY;
            }
        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
            return EMPTY;
        }
    }

    /**
     * 判断密码是否符合要求6到20位字母或字母
     */
    public static boolean isPassword(String password) {
        String str = "[a-zA-Z0-9]{6,20}$";
        return Pattern.compile(str).matcher(password).matches();
    }

    /**
     * 截取字符串
     * 把 "广东省 - 广州市 - 天河区"截取返回String[]
     */
    public static String[] getPlaces(String placeStr, String spiltStr) {

        if (placeStr != null && !placeStr.equals("")) {
            try {
                if (spiltStr.equals("-")) {
                    String temp = placeStr.replaceAll(" ", "");
                    String[] places = temp.split("-");
                    return places;
                }
                if (spiltStr.equals(",")) {
                    String temp = placeStr.replaceAll(" ", "");
                    String[] places = temp.split(",");
                    return places;
                }

            } catch (Exception e) {
                Logger.d(TAG, e.getMessage());
            }
        }
        return null;
    }

    /**
     * 将薪资的1000转化为K
     */
    public static String thousand2K(String source) {

        if (!StringUtils.isEmpty(source)) {
            try {
                String[] str = source.replace(" ", "").split("-");
                if (str.length > 1) {
                    str[0] = (Integer.parseInt(str[0]) / 1000) + (((Integer.parseInt(str[0]) / 1000) > 0) ? "k" : "");
                    str[1] = (Integer.parseInt(str[1]) / 1000) + (((Integer.parseInt(str[1]) / 1000) > 0) ? "k" : "");
                    source = str[0] + "-" + str[1];
                } else {
                    char[] numArray = source.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    String temp = "";
                    char ele;
                    for (int i = 0; i < numArray.length; i++) {
                        ele = numArray[i];
                        if (ele >= '0' && ele <= '9') {
                            sb.append(ele);
                        } else {
                            temp = source.substring(i);
                            break;
                        }
                    }
                    source = sb.toString();
                    source = (Integer.parseInt(source) / 1000) + (((Integer.parseInt(source) / 1000) > 0) ? "k" : "")
                            + temp;
                }
            } catch (Exception e) {
                Logger.d(TAG, e.getMessage());
                source = "";
            }
            return source;
        } else {
            return EMPTY;
        }
    }

    /**
     * 判断信息是否为空类型,默认返回不空  true为空,false为不空。如果是纯空字符组成的字符串也是返回true
     *
     * @author liaofucheng
     * @date 2016年6月12日
     */
    public static boolean isNull(Object value) {
        try {
            return (value == null || String.valueOf(value).trim().length() == 0) ? true : false;
        } catch (Exception e) {
            Logger.e(TAG, e.getMessage());
        }
        return false;
    }


    /*
     * 将Object类型转换成字符串，如果为null，返回的是""
     * @return String
     * @param
     * @author liaofucheng
     * @date 2016/7/14 9:34
     */
    public static String object2String(Object object) {
        try {
            if (object != null) {
                return object.toString();
            }
        } catch (Exception e) {
            Logger.e(TAG, e.getMessage());
        }
        return "";
    }

    /**
     * 数字转中文数字
     *
     * @param d
     */
    public static String numberToChinese(int d) {
        String ss[] = new String[]{"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿"};
        String s = String.valueOf(d);
        StringBuffer sb = new StringBuffer();
        try {
            String sss = String.valueOf(sb);
            int i = 0;
            for (int j = sss.length(); j > 0; j--) {
                sb = sb.insert(j, ss[i++]);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 在薪酬的后面添加/月，/日，/天，/时,如果为以上则是+/
     */
    /*
    public static String salayString(SalaryType salaryType,JobInfo jobInfo) {
        String salaryStr="";
        try {
            String typeName=salaryType.getDtName().replace("薪", "");
            String salName = jobInfo.getSalaryName().replace("以上", "+");
            salaryStr= salName+ "/" + typeName;
            return salaryStr;
        } catch (Exception e) {
        }
        return salaryStr;
    }
    */

    /**
     * 分钟转天或者转小时
     *
     * @param minutes
     * @param returnType 1=返回天带2位小数，2=返回小时+分钟，returnType不传则智能返回(未测试)
     * @returns {String}
     * @author konglingxian
     */
    public static String minuteToStr(double minutes, int returnType) {
        try {
            String str = "";
            if (minutes == 0)
                return "0分钟";
            double millisecond = minutes * 60 * 1000;
            double day = millisecond / 86400000;
            double hour = (millisecond % 86400000) / 3600000;
            double minute = (millisecond % 86400000 % 3600000) / 60000;
            DecimalFormat dcmFmt = new DecimalFormat("0.0");
            if (day > 1) {
                str = (int) day + "天";
                if (hour > 0) {
                    str += dcmFmt.format(hour) + "小时";
                }
            } else {
                if (hour > 1) {
                    str += (int) hour + "小时";
                }
            }
            if (minute > 0) {
                str += (int) minute + "分钟";
            }

            //返回*天*小时
            if (returnType == 1) {
                str = "";
                if (day > 1) {
                    str = (int) day + "天";
                    if (hour > 0) {
                        str += dcmFmt.format(hour) + "小时";
                    }
                }
            }

            //返回*小时*分钟
            if (returnType == 2) {
                str = "";
                if (day > 0 && hour > 0) {
                    str += (int) (hour + (int) day * 24) + "小时";
                }
                if (minute > 0) {
                    str += (int) minute + "分钟";
                }
            }

            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转全角和半角
     * @param src
     * @return
     */
    public static String toSemiangle(String src) {
        char[] c = src.toCharArray();
        for (int index = 0; index < c.length; index++) {
            if (c[index] == 12288) {// 全角空格
                c[index] = (char) 32;
            } else if (c[index] > 65280 && c[index] < 65375) {// 其他全角字符
                c[index] = (char) (c[index] - 65248);
            }
        }
        return new String(c);
    }

    public static String getHostName(String urlString) {
        String head = "";
        int index = urlString.indexOf("://");
        if (index != -1) {
            head = urlString.substring(0, index + 3);
            urlString = urlString.substring(index + 3);
        }
        index = urlString.indexOf("/");
        if (index != -1) {
            urlString = urlString.substring(0, index + 1);
        }
        return head + urlString;
    }

    public static String getDataSize(long var0) {
        DecimalFormat var2 = new DecimalFormat("###.00");
        return var0 < 1024L ? var0 + "bytes" : (var0 < 1048576L ? var2.format((double) ((float) var0 / 1024.0F))
                + "KB" : (var0 < 1073741824L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F))
                + "MB" : (var0 < 0L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F / 1024.0F))
                + "GB" : "error")));
    }

    public static boolean isNnull(String value) {
        return (value == null || value.trim().length() == 0) ? true : false;
    }

}
