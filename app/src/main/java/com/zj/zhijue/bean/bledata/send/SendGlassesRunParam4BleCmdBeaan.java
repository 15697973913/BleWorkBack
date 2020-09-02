package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 *  下发眼镜运行参数4
 */
public class SendGlassesRunParam4BleCmdBeaan extends BaseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xa5
     * 校验码	RxByte2	0x51
     * 命令	RxByte3	0x19
     * 数据1	RxByte4	SpeedSegment2
     * 数据2	RxByte5	SpeedInc2
     * 数据3	RxByte6	IntervalSegment2
     * 数据4	RxByte7	BackSpeedSegment2
     * 数据5	RxByte8	BackIntervalSegment2
     * 数据6	RxByte9	SpeedKeyFre2
     * 数据7	RxByte10	InterveneKeyFre2
     * 数据8	RxByte11	IntervalAccMinute2%256
     * 数据9	RxByte12	IntervalAccMinute2/256
     * 数据10	RxByte13	TrainingState  0 训练中   1 训练完成
     * 数据11	RxByte14	TrainingState2  0 训练中   1 训练完成
     * 数据12	RxByte15	AdjustSpeed
     * 数据13	RxByte16	MaxRunSpeed
     * 数据14	RxByte17	MinRunSpeed
     * 数据15	RxByte18	AdjustSpeed2
     * 数据16	RxByte19	MaxRunSpeed2
     * 校验码	RxByte20	0xaa
     */

    protected String[] sendGlassesRunParam4BleDataArray = {"a5", "51", "19", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "aa"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "a55119";


    /**
     * 下标从0开始
     */

    private final int SPEED_SEGMENT2_INDEX = 3;
    private final int SPEED_INC2_INDEX = 4;
    private final int INTERVAL_SEGMENT2_INDEX = 5;
    private final int BACK_SPEED_SEGMENT2_INDEX = 6;
    private final int BACK_INTERVAL_SEGMENT2_INDEX = 7;
    private final int SPEED_KEY_FRE2_INDEX = 8;
    private final int INTERVENE_KEY_FRE2_INDEX = 9;

    private final int INTERVAL_ACC_MINUTE2_INDEX = 10;//两个字节

    private final int TRAININGSTATE_INDEX = 12;
    private final int TRAININGSTATE2_INDEX = 13;

    private final int ADJUSTSPEED_INDEX = 14;
    private final int MAXRUNSPEED_INDEX = 15;
    private final int MINRUNSPEED_INDEX = 16;

    private final int ADJUSTSPEED2_INDEX = 17;
    private final int MAXRUNSPEED2_INDEX = 18;


    private int speedSegment2;
    private int speedInc2;
    private int intervalSegment2;
    private int backSpeedSegment2;
    private int backIntervalSegment2;
    private int speedKeyFre2;
    private int interveneKeyFre2;
    private int intervalAccMinute2;

    private int trainingState;
    private int TrainingState2;
    private int AdjustSpeed;
    private int MaxRunSpeed;
    private int MinRunSpeed;
    private int AdjustSpeed2;
    private int MaxRunSpeed2;
    //private int MinRunSpeed2;


    @Override
    protected String buildSourceData() {
        putData2DataArray();
        for (int i = 0; i < cmdDataArary.size(); i++) {
            int key = cmdDataArary.keyAt(i);
            String value = cmdDataArary.get(key);
            sendGlassesRunParam4BleDataArray[key] = value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String byteStr : sendGlassesRunParam4BleDataArray) {
            stringBuilder.append(byteStr);
        }

        return stringBuilder.toString();
    }

    /**
     *  向 cmdDataArary 中 put 数据
     */
    @Override
    protected void putData2DataArray() {

        String speedSegment2HexStr = decimalism2Hex(getSpeedSegment2(), 2);
        cmdDataArary.put(SPEED_SEGMENT2_INDEX, speedSegment2HexStr);

        String speedInc2HexStr = decimalism2Hex(getSpeedInc2(), 2);
        cmdDataArary.put(SPEED_INC2_INDEX, speedInc2HexStr);

        String intervalSegment2HexStr = decimalism2Hex(getIntervalSegment2(), 2);
        cmdDataArary.put(INTERVAL_SEGMENT2_INDEX, intervalSegment2HexStr);

        String backSpeedSegment2HexStr = decimalism2Hex(getBackSpeedSegment2(), 2);
        cmdDataArary.put(BACK_SPEED_SEGMENT2_INDEX, backSpeedSegment2HexStr);

        String backIntervalSegment2HexStr = decimalism2Hex(getBackIntervalSegment2(), 2);
        cmdDataArary.put(BACK_INTERVAL_SEGMENT2_INDEX, backIntervalSegment2HexStr);

        String speedKeyFre2HexStr = decimalism2Hex(getSpeedKeyFre2(), 2);
        cmdDataArary.put(SPEED_KEY_FRE2_INDEX, speedKeyFre2HexStr);

        String interveneKeyFre2HexStr = decimalism2Hex(getInterveneKeyFre2(), 2);
        cmdDataArary.put(INTERVENE_KEY_FRE2_INDEX, interveneKeyFre2HexStr);

        String intervalAccMinute2HexStr = decimalism2Hex(getIntervalAccMinute2(), 4);
        String[] intervalAccMinute2HexArray = reverseStringByte(intervalAccMinute2HexStr);
        for (int i = 0; i < intervalAccMinute2HexArray.length; i++) {
            cmdDataArary.put(INTERVAL_ACC_MINUTE2_INDEX + i, intervalAccMinute2HexArray[i]);
        }

        String trainingState = decimalism2Hex(getTrainingState(), 2);
        cmdDataArary.put(TRAININGSTATE_INDEX, trainingState);

        String trainingState2 = decimalism2Hex(getTrainingState2(), 2);
        cmdDataArary.put(TRAININGSTATE2_INDEX, trainingState2);

        String adJustSpeed = decimalism2Hex(getAdjustSpeed(), 2);
        cmdDataArary.put(ADJUSTSPEED_INDEX, adJustSpeed);

        String maxRunSpeed = decimalism2Hex(getMaxRunSpeed(), 2);
        cmdDataArary.put(MAXRUNSPEED_INDEX, maxRunSpeed);

        String minRunSpeed = decimalism2Hex(getMinRunSpeed(), 2);
        cmdDataArary.put(MINRUNSPEED_INDEX, minRunSpeed);

        String adJustSpeed2 = decimalism2Hex(getAdjustSpeed2(), 2);
        cmdDataArary.put(ADJUSTSPEED2_INDEX, adJustSpeed2);

        String maxRunSpeed2= decimalism2Hex(getMaxRunSpeed2(), 2);
        cmdDataArary.put(MAXRUNSPEED2_INDEX, maxRunSpeed2);


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

    public int getTrainingState() {
        return trainingState;
    }

    public void setTrainingState(int trainingState) {
        this.trainingState = trainingState;
    }

    public int getTrainingState2() {
        return TrainingState2;
    }

    public void setTrainingState2(int trainingState2) {
        TrainingState2 = trainingState2;
    }

    public int getAdjustSpeed() {
        return AdjustSpeed;
    }

    public void setAdjustSpeed(int adjustSpeed) {
        AdjustSpeed = adjustSpeed;
    }

    public int getMaxRunSpeed() {
        return MaxRunSpeed;
    }

    public void setMaxRunSpeed(int maxRunSpeed) {
        MaxRunSpeed = maxRunSpeed;
    }

    public int getMinRunSpeed() {
        return MinRunSpeed;
    }

    public void setMinRunSpeed(int minRunSpeed) {
        MinRunSpeed = minRunSpeed;
    }

    public int getAdjustSpeed2() {
        return AdjustSpeed2;
    }

    public void setAdjustSpeed2(int adjustSpeed2) {
        AdjustSpeed2 = adjustSpeed2;
    }

    public int getMaxRunSpeed2() {
        return MaxRunSpeed2;
    }

    public void setMaxRunSpeed2(int maxRunSpeed2) {
        MaxRunSpeed2 = maxRunSpeed2;
    }

}
