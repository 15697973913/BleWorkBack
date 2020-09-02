package com.zj.zhijue.http.request;

/**
 *  实时反馈数据请求参数
 */
public interface IHttpRequestCommonFeedBackBean extends IBaseRequest {
    String memberId = "memberId";
    String login_name = "login_name";
    String start_time = "start_time";
    String collect_time = "collect_time";
    String deviceId = "deviceId"; //眼镜的设备的ID
    String platform = "platform";
    String mac = "mac";
    String utdid = "utdid";
    String areaCode = "areaCode";

    String dataList = "dataList";


    String mearsureDistance = "mearsureDistance";
    String battery = "battery";
    String trainTimeYear = "trainTimeYear";
    String trainTimeMonth = "trainTimeMonth";
    String trainTimeDay = "trainTimeDay";
    String trainTimeHour = "trainTimeHour";
    String trainTimeMinute = "trainTimeMinute";
    String trainTimeSecond = "trainTimeSecond";
    String interveneAccMinute = "interveneAccMinute";
    String intervalAccMinute = "intervalAccMinute";
    String intervalAccMinute2 = "intervalAccMinute2";
    String operationCmd = "operationCmd";
    String controlRun = "controlRun";
    String userCode = "userCode";

    String mobileBluetoothTime = "mobileBluetoothTime";
    String mobileInterveneId = "mobileInterveneId";


}
