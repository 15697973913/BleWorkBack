package com.zj.zhijue.http.request;



public interface IHttpRequestRunParasmBean extends IBaseRequest{
    String memberId = "memberId";
    String login_name = "login_name";
    String start_time = "start_time";
    String collect_time = "collect_time";
    String deviceId = "deviceId";
    String platform = "platform";
    String dataList = "dataList";
    String areaCode = "areaCode";



    String mac = "mac";
    String utdid = "utdid";
    String localCollectTime = "localCollectTime";

    /**
     * Param 1
     */
    String userCode = "userCode";
    String minMinusInterval = "minMinusInterval";
    String minPlusInterval = "minPlusInterval";
    String commonNumber = "commonNumber";
    String interveneAccMinute = "interveneAccMinute";
    String weekKeyFre = "weekKeyFre";
    String weekAccMinute = "weekAccMinute";
    String monitorDataCMD = "monitorDataCMD";

    /**
     *  Param 2
     */

    String backWeekAccMinute0 = "backWeekAccMinute0";
    String backWeekAccMinute1 = "backWeekAccMinute1";
    String backWeekAccMinute2 = "backWeekAccMinute2";
    String backWeekAccMinute3 = "backWeekAccMinute3";
    String plusInterval = "plusInterval";
    String minusInterval = "minusInterval";
    String plusInc = "plusInc";
    String minusInc = "minusInc";
    String incPer = "incPer";

    /**
     *  Param 3
     */
    String runNumber = "runNumber";
    String runSpeed = "runSpeed";
    String speedInc = "speedInc";
    String speedSegment = "speedSegment";
    String intervalSegment = "intervalSegment";
    String backSpeedSegment = "backSpeedSegment";
    String backIntervalSegment = "backIntervalSegment";
    String speedKeyFre = "speedKeyFre";
    String interveneKeyFre = "interveneKeyFre";
    String intervalAccMinute = "intervalAccMinute";
    String minusInterval2 = "minusInterval2";
    String plusInterval2 = "plusInterval2";

    /**
     *  Param 4
     */
    String minusInc2 = "minusInc2";
    String plusInc2 = "plusInc2";
    String incPer2 = "incPer2";
    String runNumber2 = "runNumber2";
    String runSpeed2 = "runSpeed2";
    String speedSegment2 = "speedSegment2";
    String speedInc2 = "speedInc2";
    String intervalSegment2 = "intervalSegment2";
    String backSpeedSegment2 = "backSpeedSegment2";
    String backIntervalSegment2 = "backIntervalSegment2";
    String speedKeyFre2 = "speedKeyFre2";
    String interveneKeyFre2 = "interveneKeyFre2";
    String intervalAccMinute2 = "intervalAccMinute2";
    String currentUserNewUser = "currentUserNewUser";
    String trainingState = "trainingState";
    String trainingState2 = "trainingState2";

    /**
     *
     *  Param 5
     */
    String adjustSpeed = "adjustSpeed";
    String maxRunSpeed = "maxRunSpeed";
    String minRunSpeed = "minRunSpeed";
    String adjustSpeed2 = "adjustSpeed2";
    String maxRunSpeed2 = "maxRunSpeed2";
    String minRunSpeed2 = "minRunSpeed2";
    String txByte12 = "txByte12";
    String txByte13 = "txByte13";
    String txByte14 = "txByte14";
    String txByte15 = "txByte15";
    String txByte16 = "txByte16";
    String txByte17 = "txByte17";
    String txByte18 = "txByte18";
}
