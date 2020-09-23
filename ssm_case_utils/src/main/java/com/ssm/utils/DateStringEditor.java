package com.ssm.utils;

import org.springframework.beans.propertyeditors.PropertiesEditor;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期类型和String类型转换类
 * @Author Harlan
 * @Date 2020/9/23
 */
public class DateStringEditor extends PropertiesEditor {

    /**
     * 重写setAsText()方法
     * @param text 日期字符串
     * @throws IllegalArgumentException 异常
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try{
            Date date = DateUtils.string2Date(text, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
