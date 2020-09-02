package com.zj.zhijue.http.request;

/***
 * 训练时间数据
 */
public interface ITrainTimeInfo extends IBaseRequest{
    String MEMBERID = "memberId";//	是	string	用户ID
    String DEVICEID = "deviceId"; // 设备ID
    String BT = "bt";//	是	date	开始时间
    String ET = "et";//	是	date	结束时间
}
