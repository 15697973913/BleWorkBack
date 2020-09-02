package com.zj.zhijue.http.request;

/**
 * Created by Administrator on 2018/12/1.
 */

public interface ISendVerficationCode extends IBaseRequest{
    String PHONENUM = "phoneNum";//string	手机号
    String IMEI = "imei";//string	设备IMEI码
    String PLATFORM = "platform";//int	平台类型（0-pc,1-android,2-ios）
    String SMSTYPE = "smsType";//短信类型（短信类型->Register:用户注册，Password:修改密码）
    String SENDTIME = "sendTime";//发送时间

    String SMSTYPE_REGISTER = "REGISTER";
    String SMSTYPE_PASSWORD = "FINDPASSWORD";

    //Response
    int SUCCESS = 2; //成功
    int overfrequency = 1;//过频
    int FAILURE = 0;//失败
}
