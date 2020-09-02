package com.zj.zhijue.util;

/**
 * String 工具类
 */
public class MyStringUtils {
    /**
     * 如果为0，返回空字符串
     * @param i 需要判断的数据
     * @return result
     */
    public static String zeroToEmptyStr(int i){
        if (i==0){
            return "";
        }
        return String.valueOf(i);
    }

    /**
     * 如果为0，返回空字符串
     * @param f 需要判断的数据
     * @return result
     */
    public static String zeroToEmptyStr(float f){
        if (f==0){
            return "";
        }
        return String.valueOf(f);
    }

    /**
     * 如果为0，返回空字符串
     * @param b 需要判断的数据
     * @return result
     */
    public static String zeroToEmptyStr(double b){
        if (b==0){
            return "";
        }
        return String.valueOf(b);
    }

    /**
     * 如何str为空，返回0
     * @param str 需要转换的str
     * @return result
     */
    public static int emptyStrToZero(String str){
        if (str.isEmpty()){
            return 0;
        }
        return Integer.parseInt(str);
    }



}
