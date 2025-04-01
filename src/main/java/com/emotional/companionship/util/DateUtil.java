package com.emotional.companionship.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {
    
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    
    /**
     * 格式化日期时间为 yyyy-MM-dd HH:mm 格式
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        return sdf.format(date);
    }
    
    /**
     * 格式化日期为 yyyy-MM-dd 格式
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(date);
    }
    
    /**
     * 计算两个日期之间的秒数差
     */
    public static int calculateSecondsBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        long diff = endDate.getTime() - startDate.getTime();
        return (int) (diff / 1000);
    }
} 