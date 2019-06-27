package com.cims.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author baidu
 * @date 2018/10/14 下午7:03
 * @description 时间处理
 **/
public class DateUtils {
    public final static String YY_MM_DD_SLASH = "yyyy/MM/dd";
    public final static String YY_MM_DD = "yyyy-MM-dd";
    public final static String YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 字符串转日期
     */
    public static Date tryParseDate(String date){
        Date result = DateUtils.parse(date, YY_MM_DD_SLASH);
        if (result == null){
            result = DateUtils.parse(date, YY_MM_DD);
        }
        return result;
    }

    /**
     * 字符串转日期
     * @param date
     * @return
     */
    public static Date parseDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(YY_MM_DD);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转时间
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date,String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间转字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date,String pattern){
        if(date == null){
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 时间转字符串 默认格式 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        return format(date,YY_MM_DD);
    }

    /**
     * 时间转字符串 默认格式 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatTime(Date date){
        return format(date,YY_MM_DD_HH_MM_SS);
    }

    /**
     * 时间去掉时分秒
     * @param date
     * @param pattern
     * @return
     */
    public static Date formatToDay(Date date,String pattern){
        if(date == null){
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String dayStr = dateFormat.format(date);

        try {
            return dateFormat.parse(dayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间计算
     * @param date
     * @param n 向前或向后n天
     * @return
     */
    public static Date getDate(Date date, Integer n){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, n);
        return c.getTime();
    }

    /**
     * 根据时间差获取天数
     * @param stopDate 结束时间
     * @param startDate 起始时间
     * @return
     */
    public static Integer getDays(Date stopDate, Date startDate){
        Long days=(stopDate.getTime() - startDate.getTime())/(1000L*3600*24);
        return days.intValue();

    }


    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat(YY_MM_DD);
        }
    };
}
