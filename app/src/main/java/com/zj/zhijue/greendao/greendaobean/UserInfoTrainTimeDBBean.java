package com.zj.zhijue.greendao.greendaobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

@Entity(nameInDb = "usertraintimedb_tab")
public class UserInfoTrainTimeDBBean {
    @Index
    @Id
    @Property(nameInDb = "localid")
    private String localid;

    @Property(nameInDb = "serverId")
    private String serverId;

    @Property(nameInDb = "userName")
    private String userName;

    @NotNull
    @Property(nameInDb = "trainDate")
    private long trainDate;//当天佩戴日期

    @Unique
    @NotNull
    @Property(nameInDb = "trainDateStr")
    private String trainDateStr;//当天佩戴日期

    @Property(nameInDb = "trainTime")
    private float trainTime;//当天佩戴时间

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

    @Property(nameInDb = "trainCount")
    private int trainCount;//当天佩戴次数

    @NotNull
    @Property(nameInDb = "createTime")
    private String createTime;//本地数据库字段，非后台数据

    @NotNull
    @Property(nameInDb = "updateTime")
    private String updateTime;//本地数据库字段，非后台数据

    @NotNull
    @Property(nameInDb = "updateTimeLong")
    private long updateTimeLong;//本地数据库字段，非后台数据

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

    @Property(nameInDb = "reservedDouble0")
    private double reservedDouble0;

    @Property(nameInDb = "reservedDouble1")
    private double reservedDouble1;

    @Generated(hash = 1752131610)
    public UserInfoTrainTimeDBBean(String localid, String serverId, String userName,
            long trainDate, @NotNull String trainDateStr, float trainTime,
            int trainTimeYear, int trainTimeMonth, int trainTimeDay,
            int trainTimeHour, int trainTimeMinute, int trainTimeSecond,
            int trainCount, @NotNull String createTime, @NotNull String updateTime,
            long updateTimeLong, String reservedStr0, String reservedStr1,
            String reservedStr2, String reservedStr3, long reservedLong0,
            long reservedLong1, double reservedDouble0, double reservedDouble1) {
        this.localid = localid;
        this.serverId = serverId;
        this.userName = userName;
        this.trainDate = trainDate;
        this.trainDateStr = trainDateStr;
        this.trainTime = trainTime;
        this.trainTimeYear = trainTimeYear;
        this.trainTimeMonth = trainTimeMonth;
        this.trainTimeDay = trainTimeDay;
        this.trainTimeHour = trainTimeHour;
        this.trainTimeMinute = trainTimeMinute;
        this.trainTimeSecond = trainTimeSecond;
        this.trainCount = trainCount;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.updateTimeLong = updateTimeLong;
        this.reservedStr0 = reservedStr0;
        this.reservedStr1 = reservedStr1;
        this.reservedStr2 = reservedStr2;
        this.reservedStr3 = reservedStr3;
        this.reservedLong0 = reservedLong0;
        this.reservedLong1 = reservedLong1;
        this.reservedDouble0 = reservedDouble0;
        this.reservedDouble1 = reservedDouble1;
    }

    @Generated(hash = 1908081993)
    public UserInfoTrainTimeDBBean() {
    }

    public String getLocalid() {
        return this.localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

    public String getServerId() {
        return this.serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getTrainDate() {
        return this.trainDate;
    }

    public void setTrainDate(Long trainDate) {
        this.trainDate = trainDate;
    }

    public String getTrainDateStr() {
        return this.trainDateStr;
    }

    public void setTrainDateStr(String trainDateStr) {
        this.trainDateStr = trainDateStr;
    }

    public float getTrainTime() {
        return this.trainTime;
    }

    public void setTrainTime(float trainTime) {
        this.trainTime = trainTime;
    }

    public int getTrainCount() {
        return this.trainCount;
    }

    public void setTrainCount(int trainCount) {
        this.trainCount = trainCount;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateTimeLong() {
        return this.updateTimeLong;
    }

    public void setUpdateTimeLong(Long updateTimeLong) {
        this.updateTimeLong = updateTimeLong;
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

    public void setTrainDate(long trainDate) {
        this.trainDate = trainDate;
    }

    public void setUpdateTimeLong(long updateTimeLong) {
        this.updateTimeLong = updateTimeLong;
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

    public double getReservedDouble0() {
        return this.reservedDouble0;
    }

    public void setReservedDouble0(double reservedDouble0) {
        this.reservedDouble0 = reservedDouble0;
    }

    public double getReservedDouble1() {
        return this.reservedDouble1;
    }

    public void setReservedDouble1(double reservedDouble1) {
        this.reservedDouble1 = reservedDouble1;
    }

}
