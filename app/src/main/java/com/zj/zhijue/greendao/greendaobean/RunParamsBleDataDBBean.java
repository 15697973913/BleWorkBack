package com.zj.zhijue.greendao.greendaobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 运行参数
 */
@Entity(nameInDb = "runparams_bledata_tab")
public class RunParamsBleDataDBBean {

    @Id
    @Index
    @Property(nameInDb = "localid")
    private String localid;

    @Property(nameInDb = "userId")
    private String  userId;

    @Property(nameInDb = "glassesMAC")
    private String glassesMAC;

    @Property(nameInDb = "currentUserCode")
    private long currentUserCode;

    @Property(nameInDb = "minMinusInterval")
    private int minMinusInterval;

    @Property(nameInDb = "minPlusInterval")
    private int minPlusInterval;

    @Property(nameInDb = "commonNumber")
    private int commonNumber;

    @Property(nameInDb = "interveneAccMinute")
    private int interveneAccMinute;

    @Property(nameInDb = "weekKeyFre")
    private int weekKeyFre;

    @Property(nameInDb = "weekAccMinute")
    private int weekAccMinute;

    @Property(nameInDb = "backWeekAccMinute0")
    private int backWeekAccMinute0;

    @Property(nameInDb = "backWeekAccMinute1")
    private int backWeekAccMinute1;

    @Property(nameInDb = "backWeekAccMinute2")
    private int backWeekAccMinute2;

    @Property(nameInDb = "backWeekAccMinute3")
    private int backWeekAccMinute3;

    @Property(nameInDb = "plusInterval")
    private int plusInterval;

    @Property(nameInDb = "minusInterval")
    private int minusInterval;

    @Property(nameInDb = "plusInc")
    private int plusInc;

    @Property(nameInDb = "minusInc")
    private int minusInc;

    @Property(nameInDb = "incPer")
    private int incPer;

    @Property(nameInDb = "runNumber")
    private int runNumber;

    @Property(nameInDb = "runSpeed")
    private int runSpeed;

    @Property(nameInDb = "speedInc")
    private int speedInc;

    @Property(nameInDb = "speedSegment")
    private int speedSegment;

    @Property(nameInDb = "intervalSegment")
    private int intervalSegment;

    @Property(nameInDb = "backSpeedSegment")
    private int backSpeedSegment;

    @Property(nameInDb = "backIntervalSegment")
    private int backIntervalSegment;

    @Property(nameInDb = "speedKeyFre")
    private int speedKeyFre;

    @Property(nameInDb = "interveneKeyFre")
    private int interveneKeyFre;

    @Property(nameInDb = "intervalAccMinute")
    private int intervalAccMinute;

    @Property(nameInDb = "minusInterval2")
    private int minusInterval2;

    @Property(nameInDb = "plusInterval2")
    private int plusInterval2;

    @Property(nameInDb = "minusInc2")
    private int minusInc2;

    @Property(nameInDb = "plusInc2")
    private int plusInc2;

    @Property(nameInDb = "incPer2")
    private int incPer2;

    @Property(nameInDb = "runNumber2")
    private int runNumber2;

    @Property(nameInDb = "runSpeed2")
    private int runSpeed2;

    @Property(nameInDb = "speedSegment2")
    private int speedSegment2;

    @Property(nameInDb = "speedInc2")
    private int speedInc2;

    @Property(nameInDb = "intervalSegment2")
    private int intervalSegment2;

    @Property(nameInDb = "backSpeedSegment2")
    private int backSpeedSegment2;

    @Property(nameInDb = "backIntervalSegment2")
    private int backIntervalSegment2;

    @Property(nameInDb = "speedKeyFre2")
    private int speedKeyFre2;

    @Property(nameInDb = "interveneKeyFre2")
    private int interveneKeyFre2;

    @Property(nameInDb = "intervalAccMinute2")
    private int intervalAccMinute2;

    @Property(nameInDb = "currentUserNewUser")
    private int currentUserNewUser;

    @Property(nameInDb = "monitorDataCMD")
    private String monitorDataCMD;

    @Property(nameInDb = "receiveLocalTime")
    private long receiveLocalTime;

    @Property(nameInDb = "receiveLocalTimeStr")
    private String receiveLocalTimeStr;

    @Property(nameInDb = "isReportedServer")
    private boolean isReportedServer;

    @Property(nameInDb = "trainingState")
    private int trainingState;//trainingState

    @Property(nameInDb = "TrainingState2")
    private int trainingState2;//trainingState2

    @Property(nameInDb = "AdjustSpeed")
    private int AdjustSpeed;

    @Property(nameInDb = "MaxRunSpeed")
    private int MaxRunSpeed;

    @Property(nameInDb = "MinRunSpeed")
    private int MinRunSpeed;

    @Property(nameInDb = "AdjustSpeed2")
    private int AdjustSpeed2;

    @Property(nameInDb = "MaxRunSpeed2")
    private int MaxRunSpeed2;

    @Property(nameInDb = "MinRunSpeed2")
    private int MinRunSpeed2;

    @Property(nameInDb = "txByte12")
    private int txByte12;

    @Property(nameInDb = "txByte13")
    private int txByte13;

    @Property(nameInDb = "txByte14")
    private int txByte14;

    @Property(nameInDb = "txByte15")
    private int txByte15;

    @Property(nameInDb = "txByte16")
    private int txByte16;

    @Property(nameInDb = "txByte17")
    private int txByte17;

    @Property(nameInDb = "txByte18")
    private int txByte18;

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


    @Generated(hash = 2062324972)
    public RunParamsBleDataDBBean(String localid, String userId, String glassesMAC,
            long currentUserCode, int minMinusInterval, int minPlusInterval,
            int commonNumber, int interveneAccMinute, int weekKeyFre,
            int weekAccMinute, int backWeekAccMinute0, int backWeekAccMinute1,
            int backWeekAccMinute2, int backWeekAccMinute3, int plusInterval,
            int minusInterval, int plusInc, int minusInc, int incPer, int runNumber,
            int runSpeed, int speedInc, int speedSegment, int intervalSegment,
            int backSpeedSegment, int backIntervalSegment, int speedKeyFre,
            int interveneKeyFre, int intervalAccMinute, int minusInterval2,
            int plusInterval2, int minusInc2, int plusInc2, int incPer2,
            int runNumber2, int runSpeed2, int speedSegment2, int speedInc2,
            int intervalSegment2, int backSpeedSegment2, int backIntervalSegment2,
            int speedKeyFre2, int interveneKeyFre2, int intervalAccMinute2,
            int currentUserNewUser, String monitorDataCMD, long receiveLocalTime,
            String receiveLocalTimeStr, boolean isReportedServer, int trainingState,
            int trainingState2, int AdjustSpeed, int MaxRunSpeed, int MinRunSpeed,
            int AdjustSpeed2, int MaxRunSpeed2, int MinRunSpeed2, int txByte12,
            int txByte13, int txByte14, int txByte15, int txByte16, int txByte17,
            int txByte18, String reserve1, String reservedStr0, String reservedStr1,
            String reservedStr2, String reservedStr3, long reservedLong0,
            long reservedLong1, int reservedInt0, int reservedInt1,
            int reservedInt2, int reservedInt3, int reservedInt4) {
        this.localid = localid;
        this.userId = userId;
        this.glassesMAC = glassesMAC;
        this.currentUserCode = currentUserCode;
        this.minMinusInterval = minMinusInterval;
        this.minPlusInterval = minPlusInterval;
        this.commonNumber = commonNumber;
        this.interveneAccMinute = interveneAccMinute;
        this.weekKeyFre = weekKeyFre;
        this.weekAccMinute = weekAccMinute;
        this.backWeekAccMinute0 = backWeekAccMinute0;
        this.backWeekAccMinute1 = backWeekAccMinute1;
        this.backWeekAccMinute2 = backWeekAccMinute2;
        this.backWeekAccMinute3 = backWeekAccMinute3;
        this.plusInterval = plusInterval;
        this.minusInterval = minusInterval;
        this.plusInc = plusInc;
        this.minusInc = minusInc;
        this.incPer = incPer;
        this.runNumber = runNumber;
        this.runSpeed = runSpeed;
        this.speedInc = speedInc;
        this.speedSegment = speedSegment;
        this.intervalSegment = intervalSegment;
        this.backSpeedSegment = backSpeedSegment;
        this.backIntervalSegment = backIntervalSegment;
        this.speedKeyFre = speedKeyFre;
        this.interveneKeyFre = interveneKeyFre;
        this.intervalAccMinute = intervalAccMinute;
        this.minusInterval2 = minusInterval2;
        this.plusInterval2 = plusInterval2;
        this.minusInc2 = minusInc2;
        this.plusInc2 = plusInc2;
        this.incPer2 = incPer2;
        this.runNumber2 = runNumber2;
        this.runSpeed2 = runSpeed2;
        this.speedSegment2 = speedSegment2;
        this.speedInc2 = speedInc2;
        this.intervalSegment2 = intervalSegment2;
        this.backSpeedSegment2 = backSpeedSegment2;
        this.backIntervalSegment2 = backIntervalSegment2;
        this.speedKeyFre2 = speedKeyFre2;
        this.interveneKeyFre2 = interveneKeyFre2;
        this.intervalAccMinute2 = intervalAccMinute2;
        this.currentUserNewUser = currentUserNewUser;
        this.monitorDataCMD = monitorDataCMD;
        this.receiveLocalTime = receiveLocalTime;
        this.receiveLocalTimeStr = receiveLocalTimeStr;
        this.isReportedServer = isReportedServer;
        this.trainingState = trainingState;
        this.trainingState2 = trainingState2;
        this.AdjustSpeed = AdjustSpeed;
        this.MaxRunSpeed = MaxRunSpeed;
        this.MinRunSpeed = MinRunSpeed;
        this.AdjustSpeed2 = AdjustSpeed2;
        this.MaxRunSpeed2 = MaxRunSpeed2;
        this.MinRunSpeed2 = MinRunSpeed2;
        this.txByte12 = txByte12;
        this.txByte13 = txByte13;
        this.txByte14 = txByte14;
        this.txByte15 = txByte15;
        this.txByte16 = txByte16;
        this.txByte17 = txByte17;
        this.txByte18 = txByte18;
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

    @Generated(hash = 1959555681)
    public RunParamsBleDataDBBean() {
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

    public long getCurrentUserCode() {
        return this.currentUserCode;
    }

    public void setCurrentUserCode(long currentUserCode) {
        this.currentUserCode = currentUserCode;
    }

    public int getMinMinusInterval() {
        return this.minMinusInterval;
    }

    public void setMinMinusInterval(int minMinusInterval) {
        this.minMinusInterval = minMinusInterval;
    }

    public int getMinPlusInterval() {
        return this.minPlusInterval;
    }

    public void setMinPlusInterval(int minPlusInterval) {
        this.minPlusInterval = minPlusInterval;
    }

    public int getCommonNumber() {
        return this.commonNumber;
    }

    public void setCommonNumber(int commonNumber) {
        this.commonNumber = commonNumber;
    }

    public int getInterveneAccMinute() {
        return this.interveneAccMinute;
    }

    public void setInterveneAccMinute(int interveneAccMinute) {
        this.interveneAccMinute = interveneAccMinute;
    }

    public int getWeekKeyFre() {
        return this.weekKeyFre;
    }

    public void setWeekKeyFre(int weekKeyFre) {
        this.weekKeyFre = weekKeyFre;
    }

    public int getWeekAccMinute() {
        return this.weekAccMinute;
    }

    public void setWeekAccMinute(int weekAccMinute) {
        this.weekAccMinute = weekAccMinute;
    }

    public int getBackWeekAccMinute0() {
        return this.backWeekAccMinute0;
    }

    public void setBackWeekAccMinute0(int backWeekAccMinute0) {
        this.backWeekAccMinute0 = backWeekAccMinute0;
    }

    public int getBackWeekAccMinute1() {
        return this.backWeekAccMinute1;
    }

    public void setBackWeekAccMinute1(int backWeekAccMinute1) {
        this.backWeekAccMinute1 = backWeekAccMinute1;
    }

    public int getBackWeekAccMinute2() {
        return this.backWeekAccMinute2;
    }

    public void setBackWeekAccMinute2(int backWeekAccMinute2) {
        this.backWeekAccMinute2 = backWeekAccMinute2;
    }

    public int getBackWeekAccMinute3() {
        return this.backWeekAccMinute3;
    }

    public void setBackWeekAccMinute3(int backWeekAccMinute3) {
        this.backWeekAccMinute3 = backWeekAccMinute3;
    }

    public int getPlusInterval() {
        return this.plusInterval;
    }

    public void setPlusInterval(int plusInterval) {
        this.plusInterval = plusInterval;
    }

    public int getMinusInterval() {
        return this.minusInterval;
    }

    public void setMinusInterval(int minusInterval) {
        this.minusInterval = minusInterval;
    }

    public int getPlusInc() {
        return this.plusInc;
    }

    public void setPlusInc(int plusInc) {
        this.plusInc = plusInc;
    }

    public int getMinusInc() {
        return this.minusInc;
    }

    public void setMinusInc(int minusInc) {
        this.minusInc = minusInc;
    }

    public int getIncPer() {
        return this.incPer;
    }

    public void setIncPer(int incPer) {
        this.incPer = incPer;
    }

    public int getRunNumber() {
        return this.runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    public int getRunSpeed() {
        return this.runSpeed;
    }

    public void setRunSpeed(int runSpeed) {
        this.runSpeed = runSpeed;
    }

    public int getSpeedInc() {
        return this.speedInc;
    }

    public void setSpeedInc(int speedInc) {
        this.speedInc = speedInc;
    }

    public int getSpeedSegment() {
        return this.speedSegment;
    }

    public void setSpeedSegment(int speedSegment) {
        this.speedSegment = speedSegment;
    }

    public int getIntervalSegment() {
        return this.intervalSegment;
    }

    public void setIntervalSegment(int intervalSegment) {
        this.intervalSegment = intervalSegment;
    }

    public int getBackSpeedSegment() {
        return this.backSpeedSegment;
    }

    public void setBackSpeedSegment(int backSpeedSegment) {
        this.backSpeedSegment = backSpeedSegment;
    }

    public int getBackIntervalSegment() {
        return this.backIntervalSegment;
    }

    public void setBackIntervalSegment(int backIntervalSegment) {
        this.backIntervalSegment = backIntervalSegment;
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

    public int getIntervalAccMinute() {
        return this.intervalAccMinute;
    }

    public void setIntervalAccMinute(int intervalAccMinute) {
        this.intervalAccMinute = intervalAccMinute;
    }

    public int getMinusInterval2() {
        return this.minusInterval2;
    }

    public void setMinusInterval2(int minusInterval2) {
        this.minusInterval2 = minusInterval2;
    }

    public int getPlusInterval2() {
        return this.plusInterval2;
    }

    public void setPlusInterval2(int plusInterval2) {
        this.plusInterval2 = plusInterval2;
    }

    public int getMinusInc2() {
        return this.minusInc2;
    }

    public void setMinusInc2(int minusInc2) {
        this.minusInc2 = minusInc2;
    }

    public int getPlusInc2() {
        return this.plusInc2;
    }

    public void setPlusInc2(int plusInc2) {
        this.plusInc2 = plusInc2;
    }

    public int getIncPer2() {
        return this.incPer2;
    }

    public void setIncPer2(int incPer2) {
        this.incPer2 = incPer2;
    }

    public int getRunNumber2() {
        return this.runNumber2;
    }

    public void setRunNumber2(int runNumber2) {
        this.runNumber2 = runNumber2;
    }

    public int getRunSpeed2() {
        return this.runSpeed2;
    }

    public void setRunSpeed2(int runSpeed2) {
        this.runSpeed2 = runSpeed2;
    }

    public int getSpeedSegment2() {
        return this.speedSegment2;
    }

    public void setSpeedSegment2(int speedSegment2) {
        this.speedSegment2 = speedSegment2;
    }

    public int getSpeedInc2() {
        return this.speedInc2;
    }

    public void setSpeedInc2(int speedInc2) {
        this.speedInc2 = speedInc2;
    }

    public int getIntervalSegment2() {
        return this.intervalSegment2;
    }

    public void setIntervalSegment2(int intervalSegment2) {
        this.intervalSegment2 = intervalSegment2;
    }

    public int getBackSpeedSegment2() {
        return this.backSpeedSegment2;
    }

    public void setBackSpeedSegment2(int backSpeedSegment2) {
        this.backSpeedSegment2 = backSpeedSegment2;
    }

    public int getBackIntervalSegment2() {
        return this.backIntervalSegment2;
    }

    public void setBackIntervalSegment2(int backIntervalSegment2) {
        this.backIntervalSegment2 = backIntervalSegment2;
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

    public int getIntervalAccMinute2() {
        return this.intervalAccMinute2;
    }

    public void setIntervalAccMinute2(int intervalAccMinute2) {
        this.intervalAccMinute2 = intervalAccMinute2;
    }

    public int getCurrentUserNewUser() {
        return this.currentUserNewUser;
    }

    public void setCurrentUserNewUser(int currentUserNewUser) {
        this.currentUserNewUser = currentUserNewUser;
    }

    public String getMonitorDataCMD() {
        return this.monitorDataCMD;
    }

    public void setMonitorDataCMD(String monitorDataCMD) {
        this.monitorDataCMD = monitorDataCMD;
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

    public int getTrainingState() {
        return this.trainingState;
    }

    public void setTrainingState(int trainingState) {
        this.trainingState = trainingState;
    }

    public int getTrainingState2() {
        return this.trainingState2;
    }

    public void setTrainingState2(int trainingState2) {
        this.trainingState2 = trainingState2;
    }

    public int getAdjustSpeed() {
        return this.AdjustSpeed;
    }

    public void setAdjustSpeed(int adjustSpeed) {
        this.AdjustSpeed = adjustSpeed;
    }

    public int getMaxRunSpeed() {
        return this.MaxRunSpeed;
    }

    public void setMaxRunSpeed(int maxRunSpeed) {
        this.MaxRunSpeed = maxRunSpeed;
    }

    public int getMinRunSpeed() {
        return this.MinRunSpeed;
    }

    public void setMinRunSpeed(int minRunSpeed) {
        this.MinRunSpeed = minRunSpeed;
    }

    public int getAdjustSpeed2() {
        return this.AdjustSpeed2;
    }

    public void setAdjustSpeed2(int adjustSpeed2) {
        this.AdjustSpeed2 = adjustSpeed2;
    }

    public int getMaxRunSpeed2() {
        return this.MaxRunSpeed2;
    }

    public void setMaxRunSpeed2(int maxRunSpeed2) {
        this.MaxRunSpeed2 = maxRunSpeed2;
    }

    public int getMinRunSpeed2() {
        return this.MinRunSpeed2;
    }

    public void setMinRunSpeed2(int minRunSpeed2) {
        this.MinRunSpeed2 = minRunSpeed2;
    }

    public int getTxByte12() {
        return this.txByte12;
    }

    public void setTxByte12(int txByte12) {
        this.txByte12 = txByte12;
    }

    public int getTxByte13() {
        return this.txByte13;
    }

    public void setTxByte13(int txByte13) {
        this.txByte13 = txByte13;
    }

    public int getTxByte14() {
        return this.txByte14;
    }

    public void setTxByte14(int txByte14) {
        this.txByte14 = txByte14;
    }

    public int getTxByte15() {
        return this.txByte15;
    }

    public void setTxByte15(int txByte15) {
        this.txByte15 = txByte15;
    }

    public int getTxByte16() {
        return this.txByte16;
    }

    public void setTxByte16(int txByte16) {
        this.txByte16 = txByte16;
    }

    public int getTxByte17() {
        return this.txByte17;
    }

    public void setTxByte17(int txByte17) {
        this.txByte17 = txByte17;
    }

    public int getTxByte18() {
        return this.txByte18;
    }

    public void setTxByte18(int txByte18) {
        this.txByte18 = txByte18;
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
