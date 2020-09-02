package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;
import com.android.common.baselibrary.log.MLog;

/**
 * APP同步用户眼睛信息到眼镜
 */
public class SendUpdateEyeInfoBean2 extends BaseCmdBean {
    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xaa
     * 命令	RxByte3	0x62
     * 数据1	RxByte4	MachineData.MaxRunNumber
     * 数据2	RxByte5	MachineData.StratSpeed
     * 数据3	RxByte6	MachineData.SetSpeedInc
     * 数据4	RxByte7	MachineData.StopSpeed
     * 数据5	RxByte8	MachineData.CommonSpeed
     * 数据6	RxByte9	MachineData.MachineData6
     * 数据7	RxByte10	MachineData.MachineData7
     * 数据8	RxByte11	MachineData.MachineData8
     * 数据9	RxByte12	MachineData.MachineData9
     * 数据10	RxByte13	MachineData.SystemYearL
     * 数据11	RxByte14	MachineData.SystemYearH
     * 数据12	RxByte15	MachineData.SystemMonth
     * 数据13	RxByte16	MachineData.SystemDay
     * 数据14	RxByte17	MachineData.SystemHour
     * 数据15	RxByte18	MachineData.SystemMinute
     * 数据16	RxByte19	MachineData.SystemSecond
     * 校验码	RxByte20	0xaa
     */
    protected String[] robotReceiveBleCmdDataStr = {"aa", "62", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "aa62";

    /**
     * 眼睛
     * 0x00:左眼
     * 0x01:右眼
     */
    private final int VUALUE_EYE_INDEX = 2;

    /**
     * 视力类型
     * 0x00:预留
     * 0x01:近视
     * 0x02:老花
     * 0x03:远视
     * 0x04:弱视
     */
    private final int VUALUE_VISION_TYPE_INDEX = 3;

    /**
     * 视力
     */
    private final int VUALUE_VISION_INDEX = 4;
    /**
     * 训练负区间
     */
    private final int VUALUE_TrainingMinusRegion_INDEX =6;
    /**
     * 训练正区间
     */
    private final int VUALUE_TrainingPlusRegion_INDEX = 8;
    /**
     * 训练负区间步长
     */
    private final int VUALUE_TrainingMinusRegionStep_INDEX = 10;
    /**
     * 训练正区间步长
     */
    private final int VUALUE_TrainingPlusRegionStep_INDEX = 11;
    /**
     * 训练负区间最大步长
     */
    private final int VUALUE_TrainingMinusRegionMaxStep_INDEX = 12;
    /**
     * 训练正区间最大步长
     */
    private final int VUALUE_TrainingPlusRegionMaxStep_INDEX = 13;
    /**
     * 训练最大周期数（默认每档运行30次）
     */
    private final int VUALUE_TrainingMaxTimes_INDEX = 14;
    /**
     * 训练最大速度（默认20度/S）
     */
    private final int VUALUE_TrainingMaxSpeed_INDEX = 15;
    /**
     * 训练开始速度
     */
    private final int VUALUE_TrainingStartSpeed_INDEX = 16;
    /**
     * 训练速度增量
     */
    private final int VUALUE_TrainingSpeedInc_INDEX = 17;
    /**
     * 训练步长系数
     */
    private final int VUALUE_TrainingStepParam_INDEX = 18;

    private int Eye;//眼睛
    private int VisionType;//视力类型
    private int Vision;//视力
    private int TrainingPlusRegion;//	训练正区间
    private int TrainingMinusRegion;//	训练负区间
    private int TrainingPlusRegionStep;//	训练正区间步长
    private int TrainingMinusRegionStep;//	训练负区间步长
    private int TrainingPlusRegionMaxStep;//	训练正区间最大步长
    private int TrainingMinusRegionMaxStep;//	训练负区间最大步长
    private int TrainingMaxTimes;//	训练最大周期数
    private int TrainingMaxSpeed;//	训练最大速度
    private int TrainingStartSpeed;//	训练开始速度
    private int TrainingSpeedInc;//	训练速度增量
    private int TrainingStepParam;//	训练步长系数

    @Override
    protected String buildSourceData() {
        putData2DataArray();
        for (int i = 0; i < cmdDataArary.size(); i++) {
            int key = cmdDataArary.keyAt(i);
            String value = cmdDataArary.get(key);
            robotReceiveBleCmdDataStr[key] = value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String byteStr : robotReceiveBleCmdDataStr) {
            stringBuilder.append(byteStr);
        }

        return stringBuilder.toString();
    }

    /**
     * 向 cmdDataArary 中 put 数据
     */
    @Override
    protected void putData2DataArray() {

        /**
         * 眼睛
         * 0x00:左眼
         * 0x01:右眼
         */
        String eye = decimalism2Hex(getEye(), 2);
        cmdDataArary.put(VUALUE_EYE_INDEX, eye);

        /**
         * 视力类型
         * 0x00:预留
         * 0x01:近视
         * 0x02:老花
         * 0x03:远视
         * 0x04:弱视
         */
        String VisionType = decimalism2Hex(getVisionType(), 2);
        cmdDataArary.put(VUALUE_VISION_TYPE_INDEX, VisionType);

        /**
         * 视力
         */
        String Vision = decimalism2Hex(getVision(), 4);
        String[] vision2Array = reverseStringByte(Vision);
        for (int i = 0; i < vision2Array.length; i++) {
            cmdDataArary.put(VUALUE_VISION_INDEX + i, vision2Array[i]);
        }


        /**
         * 训练负区间
         */
        String getTrainingMinusRegion = decimalism2Hex(getTrainingMinusRegion(), 4);
        String[] getTrainingMinusRegion2Array = reverseStringByte(getTrainingMinusRegion);
        for (int i = 0; i < getTrainingMinusRegion2Array.length; i++) {
            cmdDataArary.put(VUALUE_TrainingMinusRegion_INDEX + i, getTrainingMinusRegion2Array[i]);
        }

        /**
         * 训练正区间
         */
        String getTrainingPlusRegion = decimalism2Hex(getTrainingPlusRegion(), 4);
        String[] getTrainingPlusRegion2Array = reverseStringByte(getTrainingPlusRegion);
        for (int i = 0; i < getTrainingPlusRegion2Array.length; i++) {
            cmdDataArary.put(VUALUE_TrainingPlusRegion_INDEX + i, getTrainingPlusRegion2Array[i]);
        }

        /**
         * 训练负区间步长
         */
        String getTrainingMinusRegionStep = decimalism2Hex(getTrainingMinusRegionStep(), 2);
        cmdDataArary.put(VUALUE_TrainingMinusRegionStep_INDEX, getTrainingMinusRegionStep);
        /**
         * 训练正区间步长
         */
        String getTrainingPlusRegionStep = decimalism2Hex(getTrainingPlusRegionStep(), 2);
        cmdDataArary.put(VUALUE_TrainingPlusRegionStep_INDEX, getTrainingPlusRegionStep);
        /**
         * 训练负区间最大步长
         */
        String getTrainingMinusRegionMaxStep = decimalism2Hex(getTrainingMinusRegionMaxStep(), 2);
        cmdDataArary.put(VUALUE_TrainingMinusRegionMaxStep_INDEX, getTrainingMinusRegionMaxStep);
        /**
         * 训练正区间最大步长
         */
        String getTrainingPlusRegionMaxStep = decimalism2Hex(getTrainingPlusRegionMaxStep(), 2);
        cmdDataArary.put(VUALUE_TrainingPlusRegionMaxStep_INDEX, getTrainingPlusRegionMaxStep);
        /**
         * 训练最大周期数（默认每档运行30次）
         */
        String getTrainingMaxTimes = decimalism2Hex(getTrainingMaxTimes(), 2);
        cmdDataArary.put(VUALUE_TrainingMaxTimes_INDEX, getTrainingMaxTimes);
        /**
         * 训练最大速度（默认20度/S）
         */
        String getTrainingMaxSpeed = decimalism2Hex(getTrainingMaxSpeed(), 2);
        cmdDataArary.put(VUALUE_TrainingMaxSpeed_INDEX, getTrainingMaxSpeed);
        /**
         * 训练开始速度
         */
        String getTrainingStartSpeed = decimalism2Hex(getTrainingStartSpeed(), 2);
        cmdDataArary.put(VUALUE_TrainingStartSpeed_INDEX, getTrainingStartSpeed);
        /**
         * 训练速度增量
         */
        String getTrainingSpeedInc = decimalism2Hex(getTrainingSpeedInc(), 2);
        cmdDataArary.put(VUALUE_TrainingSpeedInc_INDEX, getTrainingSpeedInc);
        /**
         * 训练步长系数
         */
        String getTrainingStepParam = decimalism2Hex(getTrainingStepParam(), 2);
        cmdDataArary.put(VUALUE_TrainingStepParam_INDEX, getTrainingStepParam);

    }

    public int getEye() {
        return Eye;
    }

    public void setEye(int eye) {
        Eye = eye;
    }

    public int getVisionType() {
        return VisionType;
    }

    public void setVisionType(int visionType) {
        VisionType = visionType;
    }

    public int getVision() {
        return Vision;
    }

    public void setVision(int vision) {
        Vision = vision;
    }

    public int getTrainingPlusRegion() {
        return TrainingPlusRegion;
    }

    public void setTrainingPlusRegion(int trainingPlusRegion) {
        TrainingPlusRegion = trainingPlusRegion;
    }

    public int getTrainingMinusRegion() {
        return TrainingMinusRegion;
    }

    public void setTrainingMinusRegion(int trainingMinusRegion) {
        TrainingMinusRegion = trainingMinusRegion;
    }

    public int getTrainingPlusRegionStep() {
        return TrainingPlusRegionStep;
    }

    public void setTrainingPlusRegionStep(int trainingPlusRegionStep) {
        TrainingPlusRegionStep = trainingPlusRegionStep;
    }

    public int getTrainingMinusRegionStep() {
        return TrainingMinusRegionStep;
    }

    public void setTrainingMinusRegionStep(int trainingMinusRegionStep) {
        TrainingMinusRegionStep = trainingMinusRegionStep;
    }

    public int getTrainingPlusRegionMaxStep() {
        return TrainingPlusRegionMaxStep;
    }

    public void setTrainingPlusRegionMaxStep(int trainingPlusRegionMaxStep) {
        TrainingPlusRegionMaxStep = trainingPlusRegionMaxStep;
    }

    public int getTrainingMinusRegionMaxStep() {
        return TrainingMinusRegionMaxStep;
    }

    public void setTrainingMinusRegionMaxStep(int trainingMinusRegionMaxStep) {
        TrainingMinusRegionMaxStep = trainingMinusRegionMaxStep;
    }

    public int getTrainingMaxTimes() {
        return TrainingMaxTimes;
    }

    public void setTrainingMaxTimes(int trainingMaxTimes) {
        TrainingMaxTimes = trainingMaxTimes;
    }

    public int getTrainingMaxSpeed() {
        return TrainingMaxSpeed;
    }

    public void setTrainingMaxSpeed(int trainingMaxSpeed) {
        TrainingMaxSpeed = trainingMaxSpeed;
    }

    public int getTrainingStartSpeed() {
        return TrainingStartSpeed;
    }

    public void setTrainingStartSpeed(int trainingStartSpeed) {
        TrainingStartSpeed = trainingStartSpeed;
    }

    public int getTrainingSpeedInc() {
        return TrainingSpeedInc;
    }

    public void setTrainingSpeedInc(int trainingSpeedInc) {
        TrainingSpeedInc = trainingSpeedInc;
    }

    public int getTrainingStepParam() {
        return TrainingStepParam;
    }

    public void setTrainingStepParam(int trainingStepParam) {
        TrainingStepParam = trainingStepParam;
    }
}
