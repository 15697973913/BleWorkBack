package com.zj.zhijue.http.request;

/**
 * Created by Administrator on 2018/12/1.
 */

public interface IMemberRegister extends IBaseRequest{
    String PHONENUM = "phone";//string	手机号
    ///String NAME = "name";//string	姓名
    String IMEI = "imei";//string	设备IMEI编号
    String PLATFORM = "platformType";//int	平台类型（0-pc,1-android,2-ios）
    String PASSWORD = "password";//string	md5加密后密码串
    String VERFICATIONCODE = "verificationCode";//string	验证码

    //Response
    //注册状态（0-失败，1-账号已存在，2-成功，3-无效验证码）
    String STATE_FAILURE = IBaseRequest.ERROR;
    String STATE_ACCOUNT_EXIST = "500";
    String STATE_SUCCESS = IBaseRequest.SUCCESS;
    String STATE_INVALID_VERFICATION_CODE = "015";

    //Response
    //找回密码状态（0-无效验证码，1-不存在该用户，2-修改成功，3-接口异常）
    int STATE_INVALIDE_CODE = 0;
    String STATE_NOT_EXIST_USER = "001";
    int STATE_UPDATE_SUCCESS = 2;
    int STATE_INTERFACE_ERROR = 3;
    String RECOMMENDER = "inviteNo";
}
