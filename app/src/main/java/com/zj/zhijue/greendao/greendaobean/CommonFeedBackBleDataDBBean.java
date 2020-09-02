package com.zj.zhijue.greendao.greendaobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 实时反馈
 */
@Entity(nameInDb = "comfeedback_bledata_tab")
public class CommonFeedBackBleDataDBBean {

    @Id
    @Index
    @Property(nameInDb = "localid")
    private String localid;

    @Property(nameInDb = "userId")
    private String  userId;

    @Property(nameInDb = "currentUserCode")
    private long currentUserCode;

    @Property(nameInDb = "glassesMAC")
    private String glassesMAC;

    @Property(nameInDb = "mearsureDistance")
    private int mearsureDistance;

    @Property(nameInDb = "battery")
    private int battery;

    @Property(nameInDb = "trainTimeYear")
    private int trainTimeYear;

    @Property(nameInDb = "trainTimeMonth")
    private int trainTimeMonth;

    @Property(nameInDb = "trainTimeDay")
    private int trainTimeDay;

    @Property(nameInDb = "trainTimeHour")
    private int trainTimeHour;

    @Property(nameInDb = "trainTimeMinute")
    private int trainTimeMinute;

    @Property(nameInDb = "trainTimeSecond")
    private int trainTimeSecond;

    @Property(nameInDb = "interveneAccMinute")
    private int interveneAccMinute;

    @Property(nameInDb = "intervalAccMinute")
    private int intervalAccMinute;

    @Property(nameInDb = "intervalAccMinute2")
    private int intervalAccMinute2;

    @Property(nameInDb = "operationCmd")
    private String operationCmd;

    @Property(nameInDb = "receiveLocalTime")
    private long receiveLocalTime;

    @Property(nameInDb = "receiveLocalTimeStr")
    private String receiveLocalTimeStr;

    @Property(nameInDb = "isReportedServer")
    private boolean isReportedServer;

    @Property(nameInDb = "reserve1")
    private String reserve1;

    @Property(nameInDb = "reservedStr0")
    private String reservedStr0;

    @Property(nameInDb = "reservedStr1")
    private String reservedStr1;

    @Property(nameInDb = "reservedStr2")
    private String reservedStr2;

    @Property(nameInDb = "reservedStr3")
    private String reservedStr3;

    @Property(nameInDb = "reservedLong0")
    private long reservedLong0;

    @Property(nameInDb = "reservedLong1")
    private long reservedLong1;

    @Property(nameInDb = "reservedInt0")
    private int reservedInt0;

    @Property(nameInDb = "reservedInt1")
    private int reservedInt1;

    @Property(nameInDb = "reservedInt2")
    private int reservedInt2;

    @Property(nameInDb = "reservedInt3")
    private int reservedInt3;

    @Property(nameInDb = "reservedInt4")
    private int reservedInt4;

    @Generated(hash = 341885335)
    public CommonFeedBackBleDataDBBean(String localid, String userId,
            long currentUserCode, String glassesMAC, int mearsureDistance,
            int battery, int trainTimeYear, int trainTimeMonth, int trainTimeDay,
            int trainTimeHour, int trainTimeMinute, int trainTimeSecond,
            int interveneAccMinute, int intervalAccMinute, int intervalAccMinute2,
            String operationCmd, long receiveLocalTime, String receiveLocalTimeStr,
            boolean isReportedServer, String reserve1, String reservedStr0,
            String reservedStr1, String reservedStr2, String reservedStr3,
            long reservedLong0, long reservedLong1, int reservedInt0,
            int reservedInt1, int reservedInt2, int reservedInt3,
            int reservedInt4) {
        this.localid = localid;
        this.userId = userId;
        this.currentUserCode = currentUserCode;
        this.glassesMAC = glassesMAC;
        this.mearsureDistance = mearsureDistance;
        this.battery = battery;
        this.trainTimeYear = trainTimeYear;
        this.trainTimeMonth = trainTimeMonth;
        this.trainTimeDay = trainTimeDay;
        this.trainTimeHour = trainTimeHour;
        this.trainTimeMinute = trainTimeMinute;
        this.trainTimeSecond = trainTimeSecond;
        this.interveneAccMinute = interveneAccMinute;
        this.intervalAccMinute = intervalAccMinute;
        this.intervalAccMinute2 = intervalAccMinute2;
        this.operationCmd = operationCmd;
        this.receiveLocalTime = receiveLocalTime;
        this.receiveLocalTimeStr = receiveLocalTimeStr;
        this.isReportedServer = isReportedServer;
        this.reserve1 = reserve1;
        this.reservedStr0 = reservedStr0;
        this.reservedStr1 = reservedStr1;
        this.reservedStr2 = reservedStr2;
        this.reservedStr3 = reservedStr3;
        this.reservedLong0 = reservedLong0;
        this.reservedLong1 = reservedLong1;
        this.reservedInt0 = reservedInt0;
        this.reservedInt1 = reservedInt1;
        this.reservedInt2 = reservedInt2;
        this.reservedInt3 = reservedInt3;
        this.reservedInt4 = reservedInt4;
    }

    @Generated(hash = 297055288)
    public CommonFeedBackBleDataDBBean() {
    }

    public String getLocalid() {
        return this.localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGlassesMAC() {
        return this.glassesMAC;
    }

    public void setGlassesMAC(String glassesMAC) {
        this.glassesMAC = glassesMAC;
    }

    public int getMearsureDistance() {
        return this.mearsureDistance;
    }

    public void setMearsureDistance(int mearsureDistance) {
        this.mearsureDistance = mearsureDistance;
    }

    public int getBattery() {
        return this.battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getTrainTimeYear() {
        return this.trainTimeYear;
    }

    public void setTrainTimeYear(int trainTimeYear) {
        this.trainTimeYear = trainTimeYear;
    }

    public int getTrainTimeMonth() {
        return this.trainTimeMonth;
    }

    public void setTrainTimeMonth(int trainTimeMonth) {
        this.trainTimeMonth = trainTimeMonth;
    }

    public int getTrainTimeDay() {
        return this.trainTimeDay;
    }

    public void setTrainTimeDay(int trainTimeDay) {
        this.trainTimeDay = trainTimeDay;
    }

    public int getTrainTimeHour() {
        return this.trainTimeHour;
    }

    public void setTrainTimeHour(int trainTimeHour) {
        this.trainTimeHour = trainTimeHour;
    }

    public int getTrainTimeMinute() {
        return this.trainTimeMinute;
    }

    public void setTrainTimeMinute(int trainTimeMinute) {
        this.trainTimeMinute = trainTimeMinute;
    }

    public int getTrainTimeSecond() {
        return this.trainTimeSecond;
    }

    public void setTrainTimeSecond(int trainTimeSecond) {
        this.trainTimeSecond = trainTimeSecond;
    }

    public int getInterveneAccMinute() {
        return this.interveneAccMinute;
    }

    public void setInterveneAccMinute(int interveneAccMinute) {
        this.interveneAccMinute = interveneAccMinute;
    }

    public int getIntervalAccMinute() {
        return this.intervalAccMinute;
    }

    public void setIntervalAccMinute(int intervalAccMinute) {
        this.intervalAccMinute = intervalAccMinute;
    }

    public int getIntervalAccMinute2() {
        return this.intervalAccMinute2;
    }

    public void setIntervalAccMinute2(int intervalAccMinute2) {
        this.intervalAccMinute2 = intervalAccMinute2;
    }

    public String getOperationCmd() {
        return this.operationCmd;
    }

    public void setOperationCmd(String operationCmd) {
        this.operationCmd = operationCmd;
    }

    public long getReceiveLocalTime() {
        return this.receiveLocalTime;
    }

    public void setReceiveLocalTime(long receiveLocalTime) {
        this.receiveLocalTime = receiveLocalTime;
    }

    public String getReceiveLocalTimeStr() {
        return this.receiveLocalTimeStr;
    }

    public void setReceiveLocalTimeStr(String receiveLocalTimeStr) {
        this.receiveLocalTimeStr = receiveLocalTimeStr;
    }

    public boolean getIsReportedServer() {
        return this.isReportedServer;
    }

    public void setIsReportedServer(boolean isReportedServer) {
        this.isReportedServer = isReportedServer;
    }

    public String getReserve1() {
        return this.reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public long getCurrentUserCode() {
        return this.currentUserCode;
    }

    public void setCurrentUserCode(long currentUserCode) {
        this.currentUserCode = currentUserCode;
    }

    public String getReservedStr0() {
        return this.reservedStr0;
    }

    public void setReservedStr0(String reservedStr0) {
        this.reservedStr0 = reservedStr0;
    }

    public String getReservedStr1() {
        return this.reservedStr1;
    }

    public void setReservedStr1(String reservedStr1) {
        this.reservedStr1 = reservedStr1;
    }

    public String getReservedStr2() {
        return this.reservedStr2;
    }

    public void setReservedStr2(String reservedStr2) {
        this.reservedStr2 = reservedStr2;
    }

    public String getReservedStr3() {
        return this.reservedStr3;
    }

    public void setReservedStr3(String reservedStr3) {
        this.reservedStr3 = reservedStr3;
    }

    public long getReservedLong0() {
        return this.reservedLong0;
    }

    public void setReservedLong0(long reservedLong0) {
        this.reservedLong0 = reservedLong0;
    }

    public long getReservedLong1() {
        return this.reservedLong1;
    }

    public void setReservedLong1(long reservedLong1) {
        this.reservedLong1 = reservedLong1;
    }

    public int getReservedInt0() {
        return this.reservedInt0;
    }

    public void setReservedInt0(int reservedInt0) {
        this.reservedInt0 = reservedInt0;
    }

    public int getReservedInt1() {
        return this.reservedInt1;
    }

    public void setReservedInt1(int reservedInt1) {
        this.reservedInt1 = reservedInt1;
    }

    public int getReservedInt2() {
        return this.reservedInt2;
    }

    public void setReservedInt2(int reservedInt2) {
        this.reservedInt2 = reservedInt2;
    }

    public int getReservedInt3() {
        return this.reservedInt3;
    }

    public void setReservedInt3(int reservedInt3) {
        this.reservedInt3 = reservedInt3;
    }

    public int getReservedInt4() {
        return this.reservedInt4;
    }

    public void setReservedInt4(int reservedInt4) {
        this.reservedInt4 = reservedInt4;
    }













}
