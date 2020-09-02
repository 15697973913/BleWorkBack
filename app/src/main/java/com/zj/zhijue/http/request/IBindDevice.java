package com.zj.zhijue.http.request;

/***
 * 检测设备的绑定状态
 */
public interface IBindDevice extends IBaseRequest{
    String MEMBERID = "memberId";//	是	string	用户ID
    String SN = "sn";
    String MAC = "mac";
    String LOGIN_NAME = "loginName";

    String BIND_STATUS_BINDED = "019";//该设备已被其他用户绑定
    String BIND_STATUS_NO_DEVICE = "018";//找不到该设备
}
