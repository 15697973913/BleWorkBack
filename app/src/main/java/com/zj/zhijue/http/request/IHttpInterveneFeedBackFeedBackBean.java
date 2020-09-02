package com.zj.zhijue.http.request;

/**
 *  干预反馈数据请求参数
 */
public interface IHttpInterveneFeedBackFeedBackBean extends IBaseRequest {
    String memberId = "memberId";

    String login_name = "loginName";
    String start_time = "start_time";
    String collect_time = "collectTime";
    String deviceId = "deviceId"; //眼镜的设备的ID
    String platform = "platform";
    String areaCode = "areaCode";

    String mac = "mac";
    String utdid = "utdid";

    String dataList = "dataList";

    String interveneYear = "interveneYear";
    String interveneMonth = "interveneMonth";
    String interveneDay = "interveneDay";
    String interveneHour = "interveneHour";
    String interveneMinute = "interveneMinute";
    String interveneSecond = "interveneSecond";
    String weekKeyFre = "weekKeyFre";
    String speedKeyFre = "speedKeyFre";
    String interveneKeyFre = "interveneKeyFre";
    String speedKeyFre2 = "speedKeyFre2";
    String interveneKeyFre2 = "interveneKeyFre2";
    String weekAccMinute = "weekAccMinute";
    String monitorCmd = "monitorCmd";

    String userCode = "userCode";

    String mobileBluetoothTime = "mobileBluetoothTime";
    String locamobileRealTimeId = "mobileRealTimeId";
}
