package com.zj.zhijue.http.request;

public interface IMemberFindPasswd extends IBaseRequest{
    String ACCOUNT = "login_name";
    String PHONENUM = "phoneNum";//string	手机号
    ///String NAME = "name";//string	姓名
    String IMEI = "imei";//string	设备IMEI编号
    String PLATFORM = "platform";//int	平台类型（0-pc,1-android,2-ios）
    String PASSWORD = "password";//string	md5加密后密码串
    String VERFICATIONCODE = "verficationCode";//string	验证码
    String UPDATER = "updater";//修改人

    //Response

    int STATE_FAILURE = 0;
    int STATE_ACCOUNT_EXIST = 1;
    int STATE_SUCCESS = 2;
    int STATE_INVALID_VERFICATION_CODE = 3;
}
