package com.zj.zhijue.http.request;

/***
 * 获取最新的训练数据 发送给蓝牙设备
 */
public interface IGetTrainInfoBleDataInfo extends IBaseRequest{
    String MEMBERID = "memberId";//	是	string	用户ID
    String LOGIN_NAME = "login_name";//是	string	用户账号
}
