package com.xiaoming.rabbit_course.utils;

import org.apache.commons.lang.StringUtils;

public class ConcealDataUtils {

    public static String concealPhone(String phone){
        if (StringUtils.isEmpty(phone)|| phone.length()!=11){
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}
