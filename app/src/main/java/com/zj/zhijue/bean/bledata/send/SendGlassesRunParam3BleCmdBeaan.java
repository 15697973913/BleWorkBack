package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 *  下发眼镜运行参数3
 */
public class SendGlassesRunParam3BleCmdBeaan extends BaseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xa5
     * 校验码	RxByte2	0x51
     * 命令	RxByte3	0x18
     * 数据1	RxByte4	BackSpeedSegment
     * 数据2	RxByte5	BackIntervalSegment
     * 数据3	RxByte6	SpeedKeyFre
     * 数据4	RxByte7	InterveneKeyFre
     * 数据5	RxByte8	IntervalAccMinute%256
     * 数据6	RxByte9	IntervalAccMinute/256
     * 数据7	RxByte10	MinusInterval2%256
     * 数据8	RxByte11	MinusInterval2/256
     * 数据9	RxByte12	PlusInterval2%256
     * 数据10	RxByte13	PlusInterval2/256
     * 数据11	RxByte14	MinusInc2
     * 数据12	RxByte15	PlusInc2
     * 数据13	RxByte16	IncPer2
     * 数据14	RxByte17	RunNumber2
     * 数据15	RxByte18	RunSpeed2
     * 数据16	RxByte19	0
     * 校验码	RxByte20	0xaa
     */

    protected String[] sendGlassesRunParam3BleDataArray = {"a5", "51", "18", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "aa"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "a55118";


    /**
     * 下标从0开始
     */
    private final int BACK_SPEED_SEGMENT_INDEX = 3;
    private final int BACK_INTERVAL_SEGMENT_INDEX = 4;
    private final int SPEED_KEY_FRE_INDEX = 5;
    private final int INTERVENE_KEY_FRE_INDEX = 6;
    private final int INTERVAL_ACC_MINUTE_INDEX = 7;//两个字节
    private final int MINUS_INTERVAL2_INDEX = 9;//两个字节
    private final int PLUS_INTERVAL2_INDEX = 11;//两个字节
    private final int MINUS_INC2_INDEX = 13;
    private final int PLUS_INC2_INDEX = 14;
    private final int INC_PER2_INDEX = 15;
    private final int RUN_NUMBER2_INDEX = 16;
    private final int RUN_SPEED2_INDEX = 17;

    private int backSpeedSegment;
    private int backIntervalSegment;
    private int speedKeyFre;
    private int interveneKeyFre;
    private int intervalAccMinute;
    private int minusInterval2;
    private int plusInterval2;
    private int minusInc2;
    private int plusInc2;
    private int incPer2;
    private int runNumber2;
    private int runSpeed2;


    @Override
    protected String buildSourceData() {
        putData2DataArray();
        for (int i = 0; i < cmdDataArary.size(); i++) {
            int key = cmdDataArary.keyAt(i);
            String value = cmdDataArary.get(key);
            sendGlassesRunParam3BleDataArray[key] = value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String byteStr : sendGlassesRunParam3BleDataArray) {
            stringBuilder.append(byteStr);
        }

        return stringBuilder.toString();
    }

    /**
     *  向 cmdDataArary 中 put 数据
     */
    @Override
    protected void putData2DataArray() {


        String backSpeedSegmentHexStr = decimalism2Hex(getBackSpeedSegment(), 2);
        cmdDataArary.put(BACK_SPEED_SEGMENT_INDEX, backSpeedSegmentHexStr);

        String backIntervalSegmentHexStr = decimalism2Hex(getBackIntervalSegment(), 2);
        cmdDataArary.put(BACK_INTERVAL_SEGMENT_INDEX, backIntervalSegmentHexStr);

        String speedKeyFreHexStr = decimalism2Hex(getSpeedKeyFre(), 2);
        cmdDataArary.put(SPEED_KEY_FRE_INDEX, speedKeyFreHexStr);

        String interveneKeyFreHexStr = decimalism2Hex(getInterveneKeyFre(), 2);
        cmdDataArary.put(INTERVENE_KEY_FRE_INDEX, interveneKeyFreHexStr);

        String intervalAccMinuteHexStr = decimalism2Hex(getIntervalAccMinute(), 4);
        String[] intervalAccMinuteHexArray = reverseStringByte(intervalAccMinuteHexStr);
        for (int i = 0; i < intervalAccMinuteHexArray.length; i++) {
            cmdDataArary.put(INTERVAL_ACC_MINUTE_INDEX + i, intervalAccMinuteHexArray[i]);
        }

        String minusInterval2HexStr = decimalism2Hex(getMinusInterval2(), 4);
        String[] minusInterval2HexArray = reverseStringByte(minusInterval2HexStr);
        for (int i = 0; i < minusInterval2HexArray.length; i++) {
            cmdDataArary.put(MINUS_INTERVAL2_INDEX + i, minusInterval2HexArray[i]);
        }

        String plusInterval2HexStr = decimalism2Hex(getPlusInterval2(), 4);
        String[] plusInterval2HexArray = reverseStringByte(plusInterval2HexStr);
        for (int i = 0; i < plusInterval2HexArray.length; i++) {
            cmdDataArary.put(PLUS_INTERVAL2_INDEX + i, plusInterval2HexArray[i]);
        }

        String minusInc2HexStr = decimalism2Hex(getMinusInc2(), 2);
        cmdDataArary.put(MINUS_INC2_INDEX, minusInc2HexStr);

        String plusInc2HexStr = decimalism2Hex(getPlusInc2(), 2);
        cmdDataArary.put(PLUS_INC2_INDEX, plusInc2HexStr);

        String incPer2HexStr = decimalism2Hex(getIncPer2(), 2);
        cmdDataArary.put(INC_PER2_INDEX, incPer2HexStr);

        String runNumber2HexStr = decimalism2Hex(getRunNumber2(), 2);
        cmdDataArary.put(RUN_NUMBER2_INDEX, runNumber2HexStr);

        String runSpeed2HexStr = decimalism2Hex(getRunSpeed2(), 2);
        cmdDataArary.put(RUN_SPEED2_INDEX, runSpeed2HexStr);
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
}
