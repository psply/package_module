package com.psply.mypackage.utils.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Data: 2019/1/12
 * Author: shipan
 * Description:
 */
public class TimeUtils {

    public static final String YMD_FORMAT = "yyyy-MM-dd";
    public static final String HMS_FORMAT = "HH:mm:ss";
    public static final String YMD_HMS_FROMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取时间戳对应的时分秒字符串
     * @param timestamp 时间戳
     * @return 时间戳转换后的字符串
     */
    public static String formatToHMS(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HMS_FORMAT,Locale.CHINA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return simpleDateFormat.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }

    /**
     * 获取时间戳对应的年月日字符串
     * @param timestamp 时间戳
     * @return 时间戳转换后的年月日字符串
     */
    public static String formatToYMD(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YMD_FORMAT,Locale.CHINA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return simpleDateFormat.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }

    /**
     * 获取时间戳对应的年月日时分秒字符串
     * @param timestamp 时间戳
     * @return 时间戳转换后的年月日时分秒字符串
     */
    public static String formatToYMDAndHMS(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YMD_HMS_FROMAT,Locale.CHINA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            simpleDateFormat.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }


}
