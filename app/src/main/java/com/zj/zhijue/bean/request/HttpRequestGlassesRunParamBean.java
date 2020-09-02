package com.zj.zhijue.bean.request;

import androidx.annotation.Keep;

/**
 * 接收眼镜发送的运行参数之后，将 Param1 - Param5 Bean 对象封装成一个对象
 */
@Keep
public class HttpRequestGlassesRunParamBean{
    private String memeberId;
    private String mac;
    private String utid;
    private String localCollectTime;

    /**
     * Param 1
     */
    private long currentUserCode;
    private int minMinusInterval;
    private int minPlusInterval;
    private int commonNumber;
    private int interveneAccMinute;
    private int weekKeyFre;
    private int weekAccMinute;
    private String monitorDataCMD;

    /**
     *  Param 2
     */

    private int backWeekAccMinute0;
    private int backWeekAccMinute1;
    private int backWeekAccMinute2;
    private int backWeekAccMinute3;
    private int plusInterval;
    private int minusInterval;
    private int plusInc;
    private int minusInc;
    private int incPer;

    /**
     *  Param 3
     */
    private int runNumber;
    private int runSpeed;
    private int speedInc;
    private int speedSegment;
    private int intervalSegment;
    private int backSpeedSegment;
    private int backIntervalSegment;
    private int speedKeyFre;
    private int interveneKeyFre;
    private int intervalAccMinute;
    private int minusInterval2;
    private int plusInterval2;

    /**
     *  Param 4
     */
    private int minusInc2;
    private int plusInc2;
    private int incPer2;
    private int runNumber2;
    private int runSpeed2;
    private int speedSegment2;
    private int speedInc2;
    private int intervalSegment2;
    private int backSpeedSegment2;
    private int backIntervalSegment2;
    private int speedKeyFre2;
    private int interveneKeyFre2;
    private int intervalAccMinute2;
    private int currentUserNewUser;

    /**
     *
     *  Param 5
     */

    private int trainingState;
    private int trainingState2;

    /**
     *     private int AdjustSpeed;
     *     private int MaxRunSpeed;
     *     private int MinRunSpeed;
     *     private int AdjustSpeed2;
     *     private int MaxRunSpeed2;
     *     private int MinRunSpeed2;
     */
    private int AdjustSpeed;
    private int MaxRunSpeed;
    private int MinRunSpeed;
    private int AdjustSpeed2;
    private int MaxRunSpeed2;
    private int MinRunSpeed2;
    private int txByte12;
    private int txByte13;
    private int txByte14;
    private int txByte15;
    private int txByte16;
    private int txByte17;
    private int txByte18;

    public long getCurrentUserCode() {
        return currentUserCode;
    }

    public void setCurrentUserCode(long currentUserCode) {
        this.currentUserCode = currentUserCode;
    }

    public int getMinMinusInterval() {
        return minMinusInterval;
    }

    public void setMinMinusInterval(int minMinusInterval) {
        this.minMinusInterval = minMinusInterval;
    }

    public int getMinPlusInterval() {
        return minPlusInterval;
    }

    public void setMinPlusInterval(int minPlusInterval) {
        this.minPlusInterval = minPlusInterval;
    }

    public int getCommonNumber() {
        return commonNumber;
    }

    public void setCommonNumber(int commonNumber) {
        this.commonNumber = commonNumber;
    }

    public int getInterveneAccMinute() {
        return interveneAccMinute;
    }

    public void setInterveneAccMinute(int interveneAccMinute) {
        this.interveneAccMinute = interveneAccMinute;
    }

    public int getWeekKeyFre() {
        return weekKeyFre;
    }

    public void setWeekKeyFre(int weekKeyFre) {
        this.weekKeyFre = weekKeyFre;
    }

    public int getWeekAccMinute() {
        return weekAccMinute;
    }

    public void setWeekAccMinute(int weekAccMinute) {
        this.weekAccMinute = weekAccMinute;
    }

    public String getMonitorDataCMD() {
        return monitorDataCMD;
    }

    public void setMonitorDataCMD(String monitorDataCMD) {
        this.monitorDataCMD = monitorDataCMD;
    }

    public int getBackWeekAccMinute0() {
        return backWeekAccMinute0;
    }

    public void setBackWeekAccMinute0(int backWeekAccMinute0) {
        this.backWeekAccMinute0 = backWeekAccMinute0;
    }

    public int getBackWeekAccMinute1() {
        return backWeekAccMinute1;
    }

    public void setBackWeekAccMinute1(int backWeekAccMinute1) {
        this.backWeekAccMinute1 = backWeekAccMinute1;
    }

    public int getBackWeekAccMinute2() {
        return backWeekAccMinute2;
    }

    public void setBackWeekAccMinute2(int backWeekAccMinute2) {
        this.backWeekAccMinute2 = backWeekAccMinute2;
    }

    public int getBackWeekAccMinute3() {
        return backWeekAccMinute3;
    }

    public void setBackWeekAccMinute3(int backWeekAccMinute3) {
        this.backWeekAccMinute3 = backWeekAccMinute3;
    }

    public int getPlusInterval() {
        return plusInterval;
    }

    public void setPlusInterval(int plusInterval) {
        this.plusInterval = plusInterval;
    }

    public int getMinusInterval() {
        return minusInterval;
    }

    public void setMinusInterval(int minusInterval) {
        this.minusInterval = minusInterval;
    }

    public int getPlusInc() {
        return plusInc;
    }

    public void setPlusInc(int plusInc) {
        this.plusInc = plusInc;
    }

    public int getMinusInc() {
        return minusInc;
    }

    public void setMinusInc(int minusInc) {
        this.minusInc = minusInc;
    }

    public int getIncPer() {
        return incPer;
    }

    public void setIncPer(int incPer) {
        this.incPer = incPer;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    public int getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(int runSpeed) {
        this.runSpeed = runSpeed;
    }

    public int getSpeedInc() {
        return speedInc;
    }

    public void setSpeedInc(int speedInc) {
        this.speedInc = speedInc;
    }

    public int getSpeedSegment() {
        return speedSegment;
    }

    public void setSpeedSegment(int speedSegment) {
        this.speedSegment = speedSegment;
    }

    public int getIntervalSegment() {
        return intervalSegment;
    }

    public void setIntervalSegment(int intervalSegment) {
        this.intervalSegment = intervalSegment;
    }

    public int getBackSpeedSegment() {
        return backSpeedSegment;
    }

    public void setBackSpeedSegment(int backSpeedSegment) {
        this.backSpeedSegment = backSpeedSegment;
    }

    public int getBackIntervalSegment() {
        return backIntervalSegment;
    }

    public void setBackIntervalSegment(int backIntervalSegment) {
        this.backIntervalSegment = backIntervalSegment;
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

    public int getIntervalAccMinute() {
        return intervalAccMinute;
    }

    public void setIntervalAccMinute(int intervalAccMinute) {
        this.intervalAccMinute = intervalAccMinute;
    }

    public int getMinusInterval2() {
        return minusInterval2;
    }

    public void setMinusInterval2(int minusInterval2) {
        this.minusInterval2 = minusInterval2;
    }

    public int getPlusInterval2() {
        return plusInterval2;
    }

    public void setPlusInterval2(int plusInterval2) {
        this.plusInterval2 = plusInterval2;
    }

    public int getMinusInc2() {
        return minusInc2;
    }

    public void setMinusInc2(int minusInc2) {
        this.minusInc2 = minusInc2;
    }

    public int getPlusInc2() {
        return plusInc2;
    }

    public void setPlusInc2(int plusInc2) {
        this.plusInc2 = plusInc2;
    }

    public int getIncPer2() {
        return incPer2;
    }

    public void setIncPer2(int incPer2) {
        this.incPer2 = incPer2;
    }

    public int getRunNumber2() {
        return runNumber2;
    }

    public void setRunNumber2(int runNumber2) {
        this.runNumber2 = runNumber2;
    }

    public int getRunSpeed2() {
        return runSpeed2;
    }

    public void setRunSpeed2(int runSpeed2) {
        this.runSpeed2 = runSpeed2;
    }

    public int getSpeedSegment2() {
        return speedSegment2;
    }

    public void setSpeedSegment2(int speedSegment2) {
        this.speedSegment2 = speedSegment2;
    }

    public int getSpeedInc2() {
        return speedInc2;
    }

    public void setSpeedInc2(int speedInc2) {
        this.speedInc2 = speedInc2;
    }

    public int getIntervalSegment2() {
        return intervalSegment2;
    }

    public void setIntervalSegment2(int intervalSegment2) {
        this.intervalSegment2 = intervalSegment2;
    }

    public int getBackSpeedSegment2() {
        return backSpeedSegment2;
    }

    public void setBackSpeedSegment2(int backSpeedSegment2) {
        this.backSpeedSegment2 = backSpeedSegment2;
    }

    public int getBackIntervalSegment2() {
        return backIntervalSegment2;
    }

    public void setBackIntervalSegment2(int backIntervalSegment2) {
        this.backIntervalSegment2 = backIntervalSegment2;
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

    public int getIntervalAccMinute2() {
        return intervalAccMinute2;
    }

    public void setIntervalAccMinute2(int intervalAccMinute2) {
        this.intervalAccMinute2 = intervalAccMinute2;
    }

    public int getCurrentUserNewUser() {
        return currentUserNewUser;
    }

    public void setCurrentUserNewUser(int currentUserNewUser) {
        this.currentUserNewUser = currentUserNewUser;
    }

    public String getMemeberId() {
        return memeberId;
    }

    public void setMemeberId(String memeberId) {
        this.memeberId = memeberId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUtid() {
        return utid;
    }

    public void setUtid(String utid) {
        this.utid = utid;
    }

    public String getLocalCollectTime() {
        return localCollectTime;
    }

    public void setLocalCollectTime(String localCollectTime) {
        this.localCollectTime = localCollectTime;
    }

    public int getTrainingState() {
        return trainingState;
    }

    public void setTrainingState(int trainingState) {
        this.trainingState = trainingState;
    }

    public int getTrainingState2() {
        return trainingState2;
    }

    public void setTrainingState2(int trainingState2) {
        this.trainingState2 = trainingState2;
    }

    public int getAdjustSpeed() {
        return AdjustSpeed;
    }

    public void setAdjustSpeed(int adjustSpeed) {
        this.AdjustSpeed = adjustSpeed;
    }

    public int getMaxRunSpeed() {
        return MaxRunSpeed;
    }

    public void setMaxRunSpeed(int maxRunSpeed) {
        this.MaxRunSpeed = maxRunSpeed;
    }

    public int getMinRunSpeed() {
        return MinRunSpeed;
    }

    public void setMinRunSpeed(int minRunSpeed) {
        this.MinRunSpeed = minRunSpeed;
    }

    public int getAdjustSpeed2() {
        return AdjustSpeed2;
    }

    public void setAdjustSpeed2(int adjustSpeed2) {
        this.AdjustSpeed2 = adjustSpeed2;
    }

    public int getMaxRunSpeed2() {
        return MaxRunSpeed2;
    }

    public void setMaxRunSpeed2(int maxRunSpeed2) {
        this.MaxRunSpeed2 = maxRunSpeed2;
    }

    public int getMinRunSpeed2() {
        return MinRunSpeed2;
    }

    public void setMinRunSpeed2(int minRunSpeed2) {
        this.MinRunSpeed2 = minRunSpeed2;
    }

    public int getTxByte12() {
        return txByte12;
    }

    public void setTxByte12(int txByte12) {
        this.txByte12 = txByte12;
    }

    public int getTxByte13() {
        return txByte13;
    }

    public void setTxByte13(int txByte13) {
        this.txByte13 = txByte13;
    }

    public int getTxByte14() {
        return txByte14;
    }

    public void setTxByte14(int txByte14) {
        this.txByte14 = txByte14;
    }

    public int getTxByte15() {
        return txByte15;
    }

    public void setTxByte15(int txByte15) {
        this.txByte15 = txByte15;
    }

    public int getTxByte16() {
        return txByte16;
    }

    public void setTxByte16(int txByte16) {
        this.txByte16 = txByte16;
    }

    public int getTxByte17() {
        return txByte17;
    }

    public void setTxByte17(int txByte17) {
        this.txByte17 = txByte17;
    }

    public int getTxByte18() {
        return txByte18;
    }

    public void setTxByte18(int txByte18) {
        this.txByte18 = txByte18;
    }

    @Override
    public String toString() {
        return "运行参数" +"\n" +
                "(0)currentUserCode=" + currentUserCode + "\n" +
                "(1)minMinusInterval=" + minMinusInterval + "\n" +
                "(2)minPlusInterval=" + minPlusInterval + "\n" +
                "(3)commonNumber=" + commonNumber +"\n" +
                "(4)interveneAccMinute=" + interveneAccMinute + "\n" +
                "(5)weekKeyFre=" + weekKeyFre +"\n" +
                "(6)weekAccMinute=" + weekAccMinute + "\n" +
                "(7)backWeekAccMinute0=" + backWeekAccMinute0 + "\n" +
                "(8)backWeekAccMinute1=" + backWeekAccMinute1 + "\n" +
                "(9)backWeekAccMinute2=" + backWeekAccMinute2 +"\n" +
                "(10)backWeekAccMinute3=" + backWeekAccMinute3 +"\n" +
                "(11)plusInterval=" + plusInterval + "\n" +
                "(12)minusInterval=" + minusInterval + "\n" +
                "(13)plusInc=" + plusInc +"\n" +
                "(14)minusInc=" + minusInc + "\n" +
                "(15)incPer=" + incPer +"\n" +
                "(16)runNumber=" + runNumber +"\n" +
                "(17)runSpeed=" + runSpeed + "\n" +
                "(18)speedInc=" + speedInc + "\n" +
                "(19)speedSegment=" + speedSegment + "\n" +
                "(20)intervalSegment=" + intervalSegment + "\n" +
                "(21)backSpeedSegment=" + backSpeedSegment + "\n" +
                "(22)backIntervalSegment=" + backIntervalSegment + "\n" +
                "(23)speedKeyFre=" + speedKeyFre + "\n" +
                "(24)interveneKeyFre=" + interveneKeyFre + "\n" +
                "(25)intervalAccMinute=" + intervalAccMinute + "\n" +
                "(26)minusInterval2=" + minusInterval2 + "\n" +
                "(27)plusInterval2=" + plusInterval2 + "\n" +
                "(28)minusInc2=" + minusInc2 + "\n" +
                "(29)plusInc2=" + plusInc2 + "\n" +
                "(30)incPer2=" + incPer2 + "\n" +
                "(31)runNumber2=" + runNumber2 + "\n" +
                "(32)runSpeed2=" + runSpeed2 + "\n" +
                "(33)speedSegment2=" + speedSegment2 + "\n" +
                "(34)speedInc2=" + speedInc2 + "\n" +
                "(35)intervalSegment2=" + intervalSegment2 + "\n" +
                "(36)backSpeedSegment2=" + backSpeedSegment2 + "\n" +
                "(37)backIntervalSegment2=" + backIntervalSegment2 + "\n" +
                "(38)speedKeyFre2=" + speedKeyFre2 + "\n" +
                "(39)interveneKeyFre2=" + interveneKeyFre2 + "\n" +
                "(40)intervalAccMinute2=" + intervalAccMinute2 + "\n" +
                "(41)trainingState=" + trainingState + "\n" +
                "(42)trainingState2=" + trainingState2 + "\n" +
                "(43)AdjustSpeed=" + AdjustSpeed + "\n" +
                "(44)MaxRunSpeed=" + MaxRunSpeed + "\n" +
                "(45)MinRunSpeed=" + MinRunSpeed + "\n" +
                "(46)AdjustSpeed2=" + AdjustSpeed2 + "\n" +
                "(47)MaxRunSpeed2=" + MaxRunSpeed2 + "\n" +
                "(48)MinRunSpeed2=" + MinRunSpeed2 + "\n" +
                "(49)txByte12=" + txByte12 + "\n" +
                "(50)txByte13=" + txByte13 + "\n" +
                "(51)txByte14=" + txByte14 + "\n" +
                "(52)txByte15=" + txByte15 + "\n" +
                "(53)txByte16=" + txByte16 + "\n" +
                "(54)txByte17=" + txByte17 + "\n" +
                "(55)txByte18=" + txByte18 + "\n" +
                "(57)currentUserNewUser=" + currentUserNewUser + "\n" +
                "(58)monitorDataCMD='" + monitorDataCMD + '\'' + "\n" +
                '}';
    }
}
