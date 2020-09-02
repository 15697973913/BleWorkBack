package com.zj.zhijue.util;

import android.text.TextUtils;

import com.huige.library.utils.SharedPreferencesUtils;
import com.zj.zhijue.config.Constants;

/**
 * Date:2020/6/29
 * Time:14:45
 * Des:
 * Author:Sonne
 */
public class UserInfoUtil {

    /**
     * 清除用户信息
     */
    public static void cleanMember() {
        SharedPreferencesUtils.put(Constants.USER_TOKEN, "");
        SharedPreferencesUtils.put(Constants.PHONE, "");
        SharedPreferencesUtils.put(Constants.IS_LOGIN, false);
        removeCommonToken();
    }

    /**
     * //是否需要登录
     *
     */
    public static boolean needLogin() {
        return TextUtils.isEmpty(getToken());
    }

    /**
     * @return 登录状态
     */
    public static boolean checkLogin() {
        return (boolean) SharedPreferencesUtils.get(Constants.IS_LOGIN, false);
    }

    /**
     * @return 获取token
     */
    public static String getToken() {
        String token = (String) SharedPreferencesUtils.get(Constants.USER_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            return "";
        }
        return token;
    }

    /**
     * @return 获取手机号
     */
    public static String getPhone() {
        String phone = (String) SharedPreferencesUtils.get(Constants.PHONE, "");
        if (TextUtils.isEmpty(phone)) {
            return "";
        }
        return phone;
    }

    public static void loginSuccess(String token,String phone) {
        SharedPreferencesUtils.put(Constants.PHONE, phone);
        SharedPreferencesUtils.put(Constants.IS_LOGIN, true);
        SharedPreferencesUtils.put(Constants.USER_TOKEN, token);
    }

    public static void removeCommonToken() {
        SharedPreferencesUtils.put(Constants.USER_TOKEN, "");
    }

}
