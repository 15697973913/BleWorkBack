package com.zj.zhijue.http.request;

public interface IMemberUpdatePasswd extends IBaseRequest{
    String PHONENUM = "phoneNum";
    String LOGINNAME = "loginName";//string	手机号
    String IMEI = "imei";//string	设备IMEI编号
    String PLATFORM = "platform";//int	平台类型（0-pc,1-android,2-ios）
    String OLDPASSWORD = "oldPassword";//string	md5加密后密码串 旧密码
    String NEWPASSWORD = "newPassword";//string	md5加密后密码串 新密码

    //Response

    int STATE_FAILURE = 0;
    int STATE_ACCOUNT_EXIST = 1;
    int STATE_SUCCESS = 2;
    int STATE_INVALID_VERFICATION_CODE = 3;
}
