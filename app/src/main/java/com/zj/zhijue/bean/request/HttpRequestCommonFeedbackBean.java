package com.zj.zhijue.bean.request;


/**
 *  上报实时反馈请求体
 */
//@Keep
public class HttpRequestCommonFeedbackBean {
    private String memberId;
    private String glassMacStr;
    private String utdid;
    private String collectTime;
    private long userCode;

    private int mearsureDistance;
    private int battery;
    private int trainTimeYear;
    private int trainTimeMonth;
    private int trainTimeDay;
    private int trainTimeHour;
    private int trainTimeMinute;
    private int trainTimeSecond;
    private int interveneAccMinute;
    private int intervalAccMinute;
    private int intervalAccMinute2;
    private String operationCmd;

    private String localId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getGlassMacStr() {
        return glassMacStr;
    }

    public void setGlassMacStr(String glassMacStr) {
        this.glassMacStr = glassMacStr;
    }

    public String getUtdid() {
        return utdid;
    }

    public void setUtdid(String utdid) {
        this.utdid = utdid;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public int getMearsureDistance() {
        return mearsureDistance;
    }

    public void setMearsureDistance(int mearsureDistance) {
        this.mearsureDistance = mearsureDistance;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getTrainTimeYear() {
        return trainTimeYear;
    }

    public void setTrainTimeYear(int trainTimeYear) {
        this.trainTimeYear = trainTimeYear;
    }

    public int getTrainTimeMonth() {
        return trainTimeMonth;
    }

    public void setTrainTimeMonth(int trainTimeMonth) {
        this.trainTimeMonth = trainTimeMonth;
    }

    public int getTrainTimeDay() {
        return trainTimeDay;
    }

    public void setTrainTimeDay(int trainTimeDay) {
        this.trainTimeDay = trainTimeDay;
    }

    public int getTrainTimeHour() {
        return trainTimeHour;
    }

    public void setTrainTimeHour(int trainTimeHour) {
        this.trainTimeHour = trainTimeHour;
    }

    public int getTrainTimeMinute() {
        return trainTimeMinute;
    }

    public void setTrainTimeMinute(int trainTimeMinute) {
        this.trainTimeMinute = trainTimeMinute;
    }

    public int getTrainTimeSecond() {
        return trainTimeSecond;
    }

    public void setTrainTimeSecond(int trainTimeSecond) {
        this.trainTimeSecond = trainTimeSecond;
    }

    public int getInterveneAccMinute() {
        return interveneAccMinute;
    }

    public void setInterveneAccMinute(int interveneAccMinute) {
        this.interveneAccMinute = interveneAccMinute;
    }

    public int getIntervalAccMinute() {
        return intervalAccMinute;
    }

    public void setIntervalAccMinute(int intervalAccMinute) {
        this.intervalAccMinute = intervalAccMinute;
    }

    public int getIntervalAccMinute2() {
        return intervalAccMinute2;
    }

    public void setIntervalAccMinute2(int intervalAccMinute2) {
        this.intervalAccMinute2 = intervalAccMinute2;
    }

    public String getOperationCmd() {
        return operationCmd;
    }

    public void setOperationCmd(String operationCmd) {
        this.operationCmd = operationCmd;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }
}
