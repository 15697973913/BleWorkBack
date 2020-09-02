package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 *  下发眼镜运行参数2
 */
public class SendGlassesRunParam2BleCmdBeaan extends BaseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xa5
     * 校验码	RxByte2	0x51
     * 命令	RxByte3	0x17
     * 数据1	RxByte4	BackWeekAccMinute[2]/256
     * 数据2	RxByte5	BackWeekAccMinute[3]%256
     * 数据3	RxByte6	BackWeekAccMinute[3]/256
     * 数据4	RxByte7	PlusInterval%256
     * 数据5	RxByte8	PlusInterval/256
     * 数据6	RxByte9	MinusInterval%256
     * 数据7	RxByte10	MinusInterval/256
     * 数据8	RxByte11	PlusInc
     * 数据9	RxByte12	MinusInc
     * 数据10	RxByte13	IncPer
     * 数据11	RxByte14	RunNumber
     * 数据12	RxByte15	RunSpeed
     * 数据13	RxByte16	SpeedInc
     * 数据14	RxByte17	SpeedSegment
     * 数据15	RxByte18	IntervalSegment
     * 数据16	RxByte19	0
     * 校验码	RxByte20	0xaa
     */

    protected String[] sendGlassesRunParam2BleDataArray = {"a5", "51", "17", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "aa"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "a55117";


    /**
     * 下标从0开始
     */
    private final int BACK_WEEK_ACC_MINUTE_2_INDEX = 3;//一个字节 << 0xff 高位

    private final int BACK_WEEK_ACC_MINUTE_3_INDEX = 4;//两个字节

    private final int PLUS_INTERVAL_INDEX = 6;//两个字节
    private final int MINUS_INTERVAL_INDEX = 8;//两个字节
    private final int PLUS_INC_INDEX = 10;//一个字节
    private final int MINUS_INC_INDEX = 11;//一个字节
    private final int INC_PER_INDEX = 12;//一个字节
    private final int RUN_NUMBER_INDEX = 13;//一个字节
    private final int RUN_SPEED_INDEX = 14;//一个字节
    private final int SPEED_INC_INDEX = 15;//一个字节
    private final int SPEED_SEGMENT_INDEX = 16;//一个字节
    private final int INTERVAL_SEGMENT_INDEX = 17;//一个字节


    private int backWeekAccMinute2;
    private int backWeekAccMinute3;
    private int plusInterval;
    private int minusInterval;
    private int plusInc;
    private int minusInc;
    private int incPer;
    private int runNumber;
    private int runSpeed;
    private int speedInc;
    private int speedSegment;
    private int intervalSegment;


    @Override
    protected String buildSourceData() {
        putData2DataArray();
        for (int i = 0; i < cmdDataArary.size(); i++) {
            int key = cmdDataArary.keyAt(i);
            String value = cmdDataArary.get(key);
            sendGlassesRunParam2BleDataArray[key] = value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String byteStr : sendGlassesRunParam2BleDataArray) {
            stringBuilder.append(byteStr);
        }

        return stringBuilder.toString();
    }

    /**
     *  向 cmdDataArary 中 put 数据
     */
    @Override
    protected void putData2DataArray() {


        String backWeekAccMinute2HexStr = decimalism2Hex(getBackWeekAccMinute2(), 2);
        cmdDataArary.put(BACK_WEEK_ACC_MINUTE_2_INDEX, backWeekAccMinute2HexStr);

        String backWeekAccMinute3HexStr = decimalism2Hex(getBackWeekAccMinute3(), 4);
        String[] backWeekAccMinute3HexArray = reverseStringByte(backWeekAccMinute3HexStr);
        for (int i = 0; i < backWeekAccMinute3HexArray.length; i++) {
            cmdDataArary.put(BACK_WEEK_ACC_MINUTE_3_INDEX + i, backWeekAccMinute3HexArray[i]);
        }

        String plusIntervalHexStr = decimalism2Hex(getPlusInterval(), 4);
        String[] plusIntervalHexArray = reverseStringByte(plusIntervalHexStr);
        for (int i = 0; i < plusIntervalHexArray.length; i++) {
            cmdDataArary.put(PLUS_INTERVAL_INDEX + i, plusIntervalHexArray[i]);
        }

        String minusIntervalHexStr = decimalism2Hex(getMinusInterval(), 4);
        String[] minusIntervalHexArray = reverseStringByte(minusIntervalHexStr);
        for (int i = 0; i < minusIntervalHexArray.length; i++) {
            cmdDataArary.put(MINUS_INTERVAL_INDEX + i, minusIntervalHexArray[i]);
        }

        String plusInc = decimalism2Hex(getPlusInc(), 2);
        cmdDataArary.put(PLUS_INC_INDEX, plusInc);

        String minusInc = decimalism2Hex(getMinusInc(), 2);
        cmdDataArary.put(MINUS_INC_INDEX, minusInc);

        String incPer = decimalism2Hex(getIncPer(), 2);
        cmdDataArary.put(INC_PER_INDEX, incPer);

        String runNumber = decimalism2Hex(getRunNumber(), 2);
        cmdDataArary.put(RUN_NUMBER_INDEX, runNumber);

        String runSpeed = decimalism2Hex(getRunSpeed(), 2);
        cmdDataArary.put(RUN_SPEED_INDEX, runSpeed);

        String speedInc = decimalism2Hex(getSpeedInc(), 2);
        cmdDataArary.put(SPEED_INC_INDEX, speedInc);

        String speedSegment = decimalism2Hex(getSpeedSegment(), 2);
        cmdDataArary.put(SPEED_SEGMENT_INDEX, speedSegment);

        String intervalSegment = decimalism2Hex(getIntervalSegment(), 2);
        cmdDataArary.put(INTERVAL_SEGMENT_INDEX, intervalSegment);
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
}
