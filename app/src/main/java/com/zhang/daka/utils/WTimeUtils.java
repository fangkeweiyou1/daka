package com.zhang.daka.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ================================================
 * Created by ${chenyuexueer}
 * 时间： 2018/7/6.
 * 说明：时间格式化工具类
 * ================================================
 */

public class WTimeUtils {

    public static SimpleDateFormat format_dd_MM_yyyy = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat format_yyyy_MM_dd = new SimpleDateFormat("yyyy_MM_dd");
    public static String text_yyyy_MM_dd = "yyyy-MM-dd";
    public static String text_mm_ss = "mm:ss";
    public static String text_dd_MM_yyyy = "dd-MM-yyyy";
    public static String text_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    /**
     * 所有解析时间格式异常全丢在这里
     *
     * @param strDate 时间格式的字符串 例如:2017年04月14日
     * @param format  yyyy年MM月dd日
     * @return Date
     */
    public static Date getDate(String strDate, SimpleDateFormat format) {
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    /**
     * 转换日期格式
     * 将格式formatStr1转换为formatStr2
     *
     * @param date
     * @return
     */
    public static String switchDateFormatter(String date, String formatStr1, String formatStr2) {
        if (TextUtils.isEmpty(date))
            return "";
        SimpleDateFormat format1 = new SimpleDateFormat(formatStr1);

        return getDateFormatter(getDate(date, format1), formatStr2);
    }

    /**
     * @param date      new Date(1492133240003)
     * @param formatStr 时间格式的字符串 例如:yyyy年MM月dd日
     * @return 例如:2017年04月14日
     */
    public static String getDateFormatter(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String time = format.format(date);
        if (TextUtils.isEmpty(time))
            return "";
        return time;
    }

}

