package com.zj.zhijue.bean.bledata.receive;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 *  接收眼镜上传的训练数据 参数3
 */
public class ReceiveGlassesRunParam3BleDataBean extends BaseParseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	TxByte1	0xa5
     * 校验码	TxByte2	0x51
     * 命令	TxByte3	0x1d
     * 数据1	TxByte4	RunNumber
     * 数据2	TxByte5	RunSpeed
     * 数据3	TxByte6	SpeedInc
     * 数据4	TxByte7	SpeedSegment
     * 数据5	TxByte8	IntervalSegment
     * 数据6	TxByte9	BackSpeedSegment
     * 数据7	TxByte10	BackIntervalSegment
     * 数据8	TxByte11	SpeedKeyFre
     * 数据9	TxByte12	InterveneKeyFre
     * 数据10	TxByte13	IntervalAccMinute%256
     * 数据11	TxByte14	IntervalAccMinute/256
     * 数据12	TxByte15	MinusInterval2%256
     * 数据13	TxByte16	MinusInterval2/256
     * 数据14	TxByte17	PlusInterval2%256
     * 数据15	TxByte18	PlusInterval2/256
     * 数据16	TxByte19	MonitorData.CMD
     * 校验码	TxByte20	0xaa
     */

    public static final String USER_MONITOR_CMD = USER_DATA_PREFIX + "1d";

    private final int RUN_NUMBER_INDEX = 3;//一个字节
    private final int RUN_SPEED_INDEX = 4;//一个字节
    private final int SPEED_INC_INDEX = 5;//一个字节
    private final int SPEED_SEGMENT_INDEX = 6;//一个字节
    private final int INTERVAL_SEGMENT_INDEX = 7;//一个字节
    private final int BACK_SPEED_SEGMENT_INDEX = 8;//一个字节
    private final int BACK_INTERVAL_SEGMENT_INDEX = 9;//一个字节
    private final int SPEED_KEY_FRE_INDEX = 10;//一个字节
    private final int INTERVENE_KEY_FRE_INDEX = 11;//一个字节
    private final int INTERVAL_ACC_MINUTE_INDEX = 12;//两个个字节
    private final int MINUS_INTERVAL2_INDEX = 14;//两个个字节
    private final int PLUS_INTERVAL2_INDEX = 16;//两个个字节
    private final int MONITOR_DATA_CMD_INDEX = 18;//一个字节

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
    private String monitorData_CMD;



    @Override
    protected BaseParseCmdBean parseBleDataStr2Bean(String bleData) {
        if (!CommonUtils.isEmpty(bleData)
                && bleData.toLowerCase().startsWith(USER_MONITOR_CMD)
                && bleData.toLowerCase().endsWith(USER_DATA_SUFIX)) {

            String runNumberHexStr = bleData.substring( 2 * RUN_NUMBER_INDEX, 2 * (RUN_NUMBER_INDEX + 1));
            setRunNumber(Integer.parseInt(runNumberHexStr, 16));

            String runSpeedHexStr = bleData.substring( 2 * RUN_SPEED_INDEX, 2 * (RUN_SPEED_INDEX + 1));
            setRunSpeed(Integer.parseInt(runSpeedHexStr, 16));

            String speedIncHexStr = bleData.substring( 2 * SPEED_INC_INDEX, 2 * (SPEED_INC_INDEX + 1));
            setSpeedInc(Integer.parseInt(speedIncHexStr, 16));

            String speedSegmentHexStr = bleData.substring( 2 * SPEED_SEGMENT_INDEX, 2 * (SPEED_SEGMENT_INDEX + 1));
            setSpeedSegment(Integer.parseInt(speedSegmentHexStr, 16));

            String intervalSegmentHexStr = bleData.substring( 2 * INTERVAL_SEGMENT_INDEX, 2 * (INTERVAL_SEGMENT_INDEX + 1));
            setIntervalSegment(Integer.parseInt(intervalSegmentHexStr, 16));

            String backSpeedSegmentHexStr = bleData.substring( 2 * BACK_SPEED_SEGMENT_INDEX, 2 * (BACK_SPEED_SEGMENT_INDEX + 1));
            setBackSpeedSegment(Integer.parseInt(backSpeedSegmentHexStr, 16));

            String backIntervalSegmentHexStr = bleData.substring( 2 * BACK_INTERVAL_SEGMENT_INDEX, 2 * (BACK_INTERVAL_SEGMENT_INDEX + 1));
            setBackIntervalSegment(Integer.parseInt(backIntervalSegmentHexStr, 16));

            String speedKeyFreHexStr = bleData.substring( 2 * SPEED_KEY_FRE_INDEX, 2 * (SPEED_KEY_FRE_INDEX + 1));
            setSpeedKeyFre(Integer.parseInt(speedKeyFreHexStr, 16));

            String interveneKeyFreHexStr = bleData.substring( 2 * INTERVENE_KEY_FRE_INDEX, 2 * (INTERVENE_KEY_FRE_INDEX + 1));
            setInterveneKeyFre(Integer.parseInt(interveneKeyFreHexStr, 16));

            String intervalAccMinuteHexStr = bleData.substring( 2 * INTERVAL_ACC_MINUTE_INDEX, 2 * (INTERVAL_ACC_MINUTE_INDEX + 2));
            String[] intervalAccMinuteHexArray = reverseStringByte(intervalAccMinuteHexStr);
            StringBuilder intervalAccMinuteBuilder = new StringBuilder();
            for (String byteArray : intervalAccMinuteHexArray) {
                intervalAccMinuteBuilder.append(byteArray);
            }
            setIntervalAccMinute(Integer.parseInt(intervalAccMinuteBuilder.toString(), 16));

            String minusInterval2HexStr = bleData.substring( 2 * MINUS_INTERVAL2_INDEX, 2 * (MINUS_INTERVAL2_INDEX + 2));
            String[] minusInterval2HexArray = reverseStringByte(minusInterval2HexStr);
            StringBuilder minusInterval2Builder = new StringBuilder();
            for (String byteArray : minusInterval2HexArray) {
                minusInterval2Builder.append(byteArray);
            }
            setMinusInterval2(Integer.parseInt(minusInterval2Builder.toString(), 16));

            String plusInterval2HexStr = bleData.substring( 2 * PLUS_INTERVAL2_INDEX, 2 * (PLUS_INTERVAL2_INDEX + 2));
            String[] plusInterval2HexArray = reverseStringByte(plusInterval2HexStr);
            StringBuilder plusInterval2Builder = new StringBuilder();
            for (String byteArray : plusInterval2HexArray) {
                plusInterval2Builder.append(byteArray);
            }
            setPlusInterval2(Integer.parseInt(plusInterval2Builder.toString(), 16));

            String monitorData_CMDHexStr = bleData.substring( 2 * MONITOR_DATA_CMD_INDEX, 2 * (MONITOR_DATA_CMD_INDEX + 1));
            setMonitorData_CMD(monitorData_CMDHexStr);

            setParseSuccess(true);
        } else {
            setParseSuccess(false);
        }
        return this;
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

    public String getMonitorData_CMD() {
        return monitorData_CMD;
    }

    public void setMonitorData_CMD(String monitorData_CMD) {
        this.monitorData_CMD = monitorData_CMD;
    }
}
