package com.zj.zhijue.greendao.greendaobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 干预反馈
 */
@Entity(nameInDb = "intevenefeedback_bledata_tab")
public class InterveneFeedBackBleDataDBBean {

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

    @Property(nameInDb = "interveneYear")
    private int interveneYear;

    @Property(nameInDb = "interveneMonth")
    private int interveneMonth;

    @Property(nameInDb = "interveneDay")
    private int interveneDay;

    @Property(nameInDb = "interveneHour")
    private int interveneHour;

    @Property(nameInDb = "interveneMinute")
    private int interveneMinute;

    @Property(nameInDb = "interveneSecond")
    private int interveneSecond;

    @Property(nameInDb = "weekKeyFre")
    private int weekKeyFre;

    @Property(nameInDb = "speedKeyFre")
    private int speedKeyFre;

    @Property(nameInDb = "interveneKeyFre")
    private int interveneKeyFre;

    @Property(nameInDb = "speedKeyFre2")
    private int speedKeyFre2;

    @Property(nameInDb = "interveneKeyFre2")
    private int interveneKeyFre2;

    @Property(nameInDb = "weekAccMinute")
    private int weekAccMinute;

    @Property(nameInDb = "monitorCmd")
    private String monitorCmd;

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

    @Generated(hash = 1837981606)
    public InterveneFeedBackBleDataDBBean(String localid, String userId,
            long currentUserCode, String glassesMAC, int interveneYear,
            int interveneMonth, int interveneDay, int interveneHour,
            int interveneMinute, int interveneSecond, int weekKeyFre,
            int speedKeyFre, int interveneKeyFre, int speedKeyFre2,
            int interveneKeyFre2, int weekAccMinute, String monitorCmd,
            long receiveLocalTime, String receiveLocalTimeStr,
            boolean isReportedServer, String reserve1, String reservedStr0,
            String reservedStr1, String reservedStr2, String reservedStr3,
            long reservedLong0, long reservedLong1, int reservedInt0,
            int reservedInt1, int reservedInt2, int reservedInt3,
            int reservedInt4) {
        this.localid = localid;
        this.userId = userId;
        this.currentUserCode = currentUserCode;
        this.glassesMAC = glassesMAC;
        this.interveneYear = interveneYear;
        this.interveneMonth = interveneMonth;
        this.interveneDay = interveneDay;
        this.interveneHour = interveneHour;
        this.interveneMinute = interveneMinute;
        this.interveneSecond = interveneSecond;
        this.weekKeyFre = weekKeyFre;
        this.speedKeyFre = speedKeyFre;
        this.interveneKeyFre = interveneKeyFre;
        this.speedKeyFre2 = speedKeyFre2;
        this.interveneKeyFre2 = interveneKeyFre2;
        this.weekAccMinute = weekAccMinute;
        this.monitorCmd = monitorCmd;
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

    @Generated(hash = 1918709401)
    public InterveneFeedBackBleDataDBBean() {
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

    public int getInterveneYear() {
        return this.interveneYear;
    }

    public void setInterveneYear(int interveneYear) {
        this.interveneYear = interveneYear;
    }

    public int getInterveneMonth() {
        return this.interveneMonth;
    }

    public void setInterveneMonth(int interveneMonth) {
        this.interveneMonth = interveneMonth;
    }

    public int getInterveneDay() {
        return this.interveneDay;
    }

    public void setInterveneDay(int interveneDay) {
        this.interveneDay = interveneDay;
    }

    public int getInterveneHour() {
        return this.interveneHour;
    }

    public void setInterveneHour(int interveneHour) {
        this.interveneHour = interveneHour;
    }

    public int getInterveneMinute() {
        return this.interveneMinute;
    }

    public void setInterveneMinute(int interveneMinute) {
        this.interveneMinute = interveneMinute;
    }

    public int getInterveneSecond() {
        return this.interveneSecond;
    }

    public void setInterveneSecond(int interveneSecond) {
        this.interveneSecond = interveneSecond;
    }

    public int getWeekKeyFre() {
        return this.weekKeyFre;
    }

    public void setWeekKeyFre(int weekKeyFre) {
        this.weekKeyFre = weekKeyFre;
    }

    public int getSpeedKeyFre() {
        return this.speedKeyFre;
    }

    public void setSpeedKeyFre(int speedKeyFre) {
        this.speedKeyFre = speedKeyFre;
    }

    public int getInterveneKeyFre() {
        return this.interveneKeyFre;
    }

    public void setInterveneKeyFre(int interveneKeyFre) {
        this.interveneKeyFre = interveneKeyFre;
    }

    public int getSpeedKeyFre2() {
        return this.speedKeyFre2;
    }

    public void setSpeedKeyFre2(int speedKeyFre2) {
        this.speedKeyFre2 = speedKeyFre2;
    }

    public int getInterveneKeyFre2() {
        return this.interveneKeyFre2;
    }

    public void setInterveneKeyFre2(int interveneKeyFre2) {
        this.interveneKeyFre2 = interveneKeyFre2;
    }

    public int getWeekAccMinute() {
        return this.weekAccMinute;
    }

    public void setWeekAccMinute(int weekAccMinute) {
        this.weekAccMinute = weekAccMinute;
    }

    public String getMonitorCmd() {
        return this.monitorCmd;
    }

    public void setMonitorCmd(String monitorCmd) {
        this.monitorCmd = monitorCmd;
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
