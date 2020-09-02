package com.zj.zhijue.http.request;

/**
 * Created by Administrator on 2018/12/1.
 */

public interface IMemberLogin extends IBaseRequest{
    String LOGIN_NAME = "loginName";//string	客户账号
    String PASSWORD = "password";//string	md5加密串

    String IMEI = "imei";
    String IP = "IP";
    String PLATFORMTYPE = "platformType";
}
