package com.zj.zhijue.bean.request;

/**
 *  上报干预反馈请求体
 */
//@Keep
public class HttpRequestInterveneFeedbackBean {
    private String memberId;
    private String glassMacStr;
    private String utdid;
    private String collectTime;
    private long userCode;

    private int interveneYear;
    private int interveneMonth;
    private int interveneDay;
    private int interveneHour;
    private int interveneMinute;
    private int interveneSecond;
    private int weekKeyFre;
    private int speedKeyFre;
    private int interveneKeyFre;
    private int speedKeyFre2;
    private int interveneKeyFre2;
    private int weekAccMinute;
    private String monitorCmd;

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

    public int getInterveneYear() {
        return interveneYear;
    }

    public void setInterveneYear(int interveneYear) {
        this.interveneYear = interveneYear;
    }

    public int getInterveneMonth() {
        return interveneMonth;
    }

    public void setInterveneMonth(int interveneMonth) {
        this.interveneMonth = interveneMonth;
    }

    public int getInterveneDay() {
        return interveneDay;
    }

    public void setInterveneDay(int interveneDay) {
        this.interveneDay = interveneDay;
    }

    public int getInterveneHour() {
        return interveneHour;
    }

    public void setInterveneHour(int interveneHour) {
        this.interveneHour = interveneHour;
    }

    public int getInterveneMinute() {
        return interveneMinute;
    }

    public void setInterveneMinute(int interveneMinute) {
        this.interveneMinute = interveneMinute;
    }

    public int getInterveneSecond() {
        return interveneSecond;
    }

    public void setInterveneSecond(int interveneSecond) {
        this.interveneSecond = interveneSecond;
    }

    public int getWeekKeyFre() {
        return weekKeyFre;
    }

    public void setWeekKeyFre(int weekKeyFre) {
        this.weekKeyFre = weekKeyFre;
    }

    public int getSpeedKeyFre() {
        return speedKeyFre;
    }

    public void setSpeedKeyFre(int speedKeyFre) {
        this.speedKeyFre = speedKeyFre;
    }

    public int getInterveneKeyFre() {
        return interveneKeyFre;
    }

    public void setInterveneKeyFre(int interveneKeyFre) {
        this.interveneKeyFre = interveneKeyFre;
    }

    public int getSpeedKeyFre2() {
        return speedKeyFre2;
    }

    public void setSpeedKeyFre2(int speedKeyFre2) {
        this.speedKeyFre2 = speedKeyFre2;
    }

    public int getInterveneKeyFre2() {
        return interveneKeyFre2;
    }

    public void setInterveneKeyFre2(int interveneKeyFre2) {
        this.interveneKeyFre2 = interveneKeyFre2;
    }

    public int getWeekAccMinute() {
        return weekAccMinute;
    }

    public void setWeekAccMinute(int weekAccMinute) {
        this.weekAccMinute = weekAccMinute;
    }

    public String getMonitorCmd() {
        return monitorCmd;
    }

    public void setMonitorCmd(String monitorCmd) {
        this.monitorCmd = monitorCmd;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }
}
