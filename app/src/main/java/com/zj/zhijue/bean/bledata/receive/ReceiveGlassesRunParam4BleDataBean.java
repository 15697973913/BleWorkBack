package com.zj.zhijue.bean.bledata.receive;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 *  接收眼镜上传的训练数据 参数4
 */
public class ReceiveGlassesRunParam4BleDataBean extends BaseParseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	TxByte1	0xa5
     * 校验码	TxByte2	0x51
     * 命令	TxByte3	0x1e
     * 数据1	TxByte4	MinusInc2
     * 数据2	TxByte5	PlusInc2
     * 数据3	TxByte6	IncPer2
     * 数据4	TxByte7	RunNumber2
     * 数据5	TxByte8	RunSpeed2
     * 数据6	TxByte9	SpeedSegment2
     * 数据7	TxByte10	SpeedInc2
     * 数据8	TxByte11	IntervalSegment2
     * 数据9	TxByte12	BackSpeedSegment2
     * 数据10	TxByte13	BackIntervalSegment2
     * 数据11	TxByte14	SpeedKeyFre2
     * 数据12	TxByte15	InterveneKeyFre2
     * 数据13	TxByte16	IntervalAccMinute2%256
     * 数据14	TxByte17	IntervalAccMinute2/256
     * 数据15	TxByte18	CurrentUser.NewUser
     * 数据16	TxByte19	MonitorData.CMD
     * 校验码	TxByte20	0xaa
     */

    public static final String USER_MONITOR_CMD = USER_DATA_PREFIX + "1e";

    private final int MINUS_INC2_INDEX = 3;
    private final int PLUS_INC2_INDEX = 4;
    private final int INC_PER2_INDEX = 5;
    private final int RUN_NUMBER2_INDEX = 6;
    private final int RUN_SPEED2_INDEX = 7;
    private final int SPEED_SEGMENT2_INDEX = 8;
    private final int SPEED_INC2_INDEX = 9;
    private final int INTERVAL_SEGMENT2_INDEX = 10;
    private final int BACK_SPEED_SEGMENT2_INDEX = 11;
    private final int BACK_INTERVAL_SEGMENT2_INDEX = 12;
    private final int SPEED_KEY_FRE2_INDEX = 13;
    private final int INTERVENE_KEY_FRE2_INDEX = 14;
    private final int INTERVAL_ACC_MINUTE2_INDEX = 15;//两个字节
    private final int CURRENTUSER_NEWUSER_INDEX = 17;//一个字节
    private final int MONITOR_DATA_CMD_INDEX = 18;//一个字节

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
    private String monitorData_CMD;



    @Override
    protected BaseParseCmdBean parseBleDataStr2Bean(String bleData) {
        if (!CommonUtils.isEmpty(bleData)
                && bleData.toLowerCase().startsWith(USER_MONITOR_CMD)
                && bleData.toLowerCase().endsWith(USER_DATA_SUFIX)) {

            String minusInc2HexStr = bleData.substring( 2 * MINUS_INC2_INDEX, 2 * (MINUS_INC2_INDEX + 1));
            setMinusInc2(Integer.parseInt(minusInc2HexStr, 16));

            String plusInc2HexStr = bleData.substring( 2 * PLUS_INC2_INDEX, 2 * (PLUS_INC2_INDEX + 1));
            setPlusInc2(Integer.parseInt(plusInc2HexStr, 16));

            String incPer2HexStr = bleData.substring( 2 * INC_PER2_INDEX, 2 * (INC_PER2_INDEX + 1));
            setIncPer2(Integer.parseInt(incPer2HexStr, 16));

            String runNumber2HexStr = bleData.substring( 2 * RUN_NUMBER2_INDEX, 2 * (RUN_NUMBER2_INDEX + 1));
            setRunNumber2(Integer.parseInt(runNumber2HexStr, 16));

            String runSpeed2HexStr = bleData.substring( 2 * RUN_SPEED2_INDEX, 2 * (RUN_SPEED2_INDEX + 1));
            setRunSpeed2(Integer.parseInt(runSpeed2HexStr, 16));

            String speedSegment2HexStr = bleData.substring( 2 * SPEED_SEGMENT2_INDEX, 2 * (SPEED_SEGMENT2_INDEX + 1));
            setSpeedSegment2(Integer.parseInt(speedSegment2HexStr, 16));

            String speedInc2HexStr = bleData.substring( 2 * SPEED_INC2_INDEX, 2 * (SPEED_INC2_INDEX + 1));
            setSpeedInc2(Integer.parseInt(speedInc2HexStr, 16));

            String intervalSegment2HexStr = bleData.substring( 2 * INTERVAL_SEGMENT2_INDEX, 2 * (INTERVAL_SEGMENT2_INDEX + 1));
            setIntervalSegment2(Integer.parseInt(intervalSegment2HexStr, 16));

            String backSpeedSegment2HexStr = bleData.substring( 2 * BACK_SPEED_SEGMENT2_INDEX, 2 * (BACK_SPEED_SEGMENT2_INDEX + 1));
            setBackSpeedSegment2(Integer.parseInt(backSpeedSegment2HexStr, 16));

            String backIntervalSegment2HexStr = bleData.substring( 2 * BACK_INTERVAL_SEGMENT2_INDEX, 2 * (BACK_INTERVAL_SEGMENT2_INDEX + 1));
            setBackIntervalSegment2(Integer.parseInt(backIntervalSegment2HexStr, 16));

            String speedKeyFre2HexStr = bleData.substring( 2 * SPEED_KEY_FRE2_INDEX, 2 * (SPEED_KEY_FRE2_INDEX + 1));
            setSpeedKeyFre2(Integer.parseInt(speedKeyFre2HexStr, 16));

            String interveneKeyFre2HexStr = bleData.substring( 2 * INTERVENE_KEY_FRE2_INDEX, 2 * (INTERVENE_KEY_FRE2_INDEX + 1));
            setInterveneKeyFre2(Integer.parseInt(interveneKeyFre2HexStr, 16));


            String intervalAccMinute2HexStr = bleData.substring( 2 * INTERVAL_ACC_MINUTE2_INDEX, 2 * (INTERVAL_ACC_MINUTE2_INDEX + 2));
            String[] intervalAccMinute2HexArray = reverseStringByte(intervalAccMinute2HexStr);
            StringBuilder intervalAccMinute2Builder = new StringBuilder();
            for (String byteArray : intervalAccMinute2HexArray) {
                intervalAccMinute2Builder.append(byteArray);
            }
            setIntervalAccMinute2(Integer.parseInt(intervalAccMinute2Builder.toString(), 16));

            String currentUser_NewUserHexStr = bleData.substring( 2 * CURRENTUSER_NEWUSER_INDEX, 2 * (CURRENTUSER_NEWUSER_INDEX + 1));
            setCurrentUserNewUser(Integer.parseInt(currentUser_NewUserHexStr, 16));

            String monitorData_CMDHexStr = bleData.substring( 2 * MONITOR_DATA_CMD_INDEX, 2 * (MONITOR_DATA_CMD_INDEX + 1));
            setMonitorData_CMD(monitorData_CMDHexStr);

            setParseSuccess(true);
        } else {
            setParseSuccess(false);
        }
        return this;
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

    public String getMonitorData_CMD() {
        return monitorData_CMD;
    }

    public void setMonitorData_CMD(String monitorData_CMD) {
        this.monitorData_CMD = monitorData_CMD;
    }


}
