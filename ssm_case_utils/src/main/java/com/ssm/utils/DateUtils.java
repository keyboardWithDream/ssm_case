package com.ssm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期字符串互转
 * @Author Harlan
 * @Date 2020/9/21
 */
public class DateUtils {

    /**
     * 日期转字符串
     * @param date 日期对象
     * @param pattern 日期格式
     * @return 字符串
     */
    public static String date2String(Date date, String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串转日期对象
     * @param date 字符串
     * @param pattern 日期格式
     * @return 日期对象
     * @throws ParseException 转换错误异常
     */
    public static Date string2Date(String date, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(date);
    }
}
