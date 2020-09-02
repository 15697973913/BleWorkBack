package com.zj.zhijue.bean.bledata.receive;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 * 实时反馈数据（APP每次发送给蓝牙眼镜数据之后，眼镜的反馈数据）
 */
public class ReceiveGlassesFeedbackBleDataBean extends BaseParseCmdBean {

    /**定义 序号 内容
     1* 定义	序号	内容
     2* 校验码	TxByte1	0xa5
     3* 校验码	TxByte2	0x51
     4* 命令	TxByte3	0x13
     5* 数据1	TxByte4	MonitorData.MeasDistance
     6* 数据2	TxByte5	MonitorData.Battery  最高位是充电状态，第七位是电量百分比
     7* 数据3	TxByte6	MonitorData.TrainingYear
     8* 数据4	TxByte7	MonitorData.TrainingMonth
     9* 数据5	TxByte8	MonitorData.Trainingday
     10*数据6	TxByte9	MonitorData.TrainingHour
     11*数据7	TxByte10	MonitorData.TrainingMinute
     12*数据8	TxByte11	MonitorData.TrainingSecond
     13*数据9	TxByte12	InterveneAccMinute%256
     14*数据10	TxByte13	InterveneAccMinute/256
     15*数据11	TxByte14	IntervalAccMinute%256
     16*数据12	TxByte15	IntervalAccMinute/256
     17*数据13	TxByte16	IntervalAccMinute2%256
     18*数据14	TxByte17	IntervalAccMinute2/256
     19*数据15	TxByte18	ControlData.ControlRun
     20*数据16	TxByte19	MonitorData.CMD
     */
    /**
     * 监控数据上传指令 a4
     * 4 是距离测量
     * 5 是电池电量
     * 6 年（时长，不是时间点）
     * 7 月
     * 8 天
     * 9 小时
     * 10 分钟
     * 11 秒
     * 12， 13 InterveneAccMinute  12 是低位，13 是高位
     * 14， 15 IntervalAccMinute 14 是低位，15 是高位
     * 16， 17 IntervalAccMinute2 16 是低位，17是高位
     * 18 0
     * 19 MonitorData.CMD
     * 20 是固定校验码
     */

    /**
     * AAP牙蓝向下位机通信时，设备都会把下发的命令码 MonitorData.CMD 进行回传，
     * 如果回传的命令码 MonitorData.CMD为0，则表示数据接收不成功，放弃当次的数据接收
     */


    public static final String USER_MONITOR_CMD = USER_DATA_PREFIX + "13";

    private int mearsureDistance;
    private int battery;
    private int trainTimeYear;
    private int trainTimeMonth;
    private int trainTimeDay;
    private int trainTimeHour;
    private int trainTimeMinute;
    private int trainTimeSecond;


    private int interveneAccMinute;
    private int intervalAccMinute;
    private int intervalAccMinute2;

    private String controlCmd;
    private String operationCmd;

    private final int DISTANCE_DATA_INDEX = 3;
    private final int BATTER_DATA_INDEX = 4;
    private final int TRAIN_TIME_YEAR_IDNEX = 5;//一个字节
    private final int TRAIN_TIME_MONTH_IDNEX = 6;//一个字节
    private final int TRAIN_TIME_DAY_IDNEX = 7;//一个字节

    private final int TRAIN_TIME_HOUR_IDNEX = 8;//一个字节
    private final int TRAIN_TIME_MINUTE_IDNEX = 9;//一个字节
    private final int TRAIN_TIME_SECOND_IDNEX = 10;//一个字节

    private final int INTERVENE_ACCMINUTE_INDEX = 11;// 两个字节

    private final int INTERVAL_ACCMINUTE_INDEX = 13;// 两个字节

    private final int INTERVAL_ACCMINUTE2_INDEX = 15;// 两个字节

    private final int CONTROL_RUN_CMD_INDEX = 17;//一个字节，操作的具体控制指令



    private final int OPERATION_CMD_INDEX = 18;//操作码


    @Override
    protected BaseParseCmdBean parseBleDataStr2Bean(String bleData) {
        if (!CommonUtils.isEmpty(bleData)
                && bleData.toLowerCase().startsWith(USER_MONITOR_CMD)
                && bleData.toLowerCase().endsWith(USER_DATA_SUFIX)) {

            String disTanceData = bleData.substring( 2 * DISTANCE_DATA_INDEX, 2 * (DISTANCE_DATA_INDEX + 1));
            String batteryData = bleData.substring( 2 * BATTER_DATA_INDEX, 2 * (BATTER_DATA_INDEX + 1));

            String trainTimeYearLongData = bleData.substring(2 * TRAIN_TIME_YEAR_IDNEX, 2 * (TRAIN_TIME_YEAR_IDNEX + 1));
            setTrainTimeYear(Integer.parseInt(trainTimeYearLongData, 16));

            String trainTimeMonthLongData = bleData.substring(2 * TRAIN_TIME_MONTH_IDNEX, 2 * (TRAIN_TIME_MONTH_IDNEX + 1));
            setTrainTimeMonth(Integer.parseInt(trainTimeMonthLongData, 16));

            String trainTimeDayLongData = bleData.substring(2 * TRAIN_TIME_DAY_IDNEX, 2 * (TRAIN_TIME_DAY_IDNEX + 1));
            setTrainTimeDay(Integer.parseInt(trainTimeDayLongData, 16));

            String trainTimeHourLongData = bleData.substring(2 * TRAIN_TIME_HOUR_IDNEX, 2 * (TRAIN_TIME_HOUR_IDNEX + 1));
            setTrainTimeHour(Integer.parseInt(trainTimeHourLongData, 16));

            String trainTimeMinuteLongData = bleData.substring(2 * TRAIN_TIME_MINUTE_IDNEX, 2 * (TRAIN_TIME_MINUTE_IDNEX + 1));
            setTrainTimeMinute(Integer.parseInt(trainTimeMinuteLongData, 16));

            String trainTimeSecondLongData = bleData.substring(2 * TRAIN_TIME_SECOND_IDNEX, 2 * (TRAIN_TIME_SECOND_IDNEX + 1));
            setTrainTimeSecond(Integer.parseInt(trainTimeSecondLongData, 16));


            String interveneAccminuteStr = bleData.substring(2 * INTERVENE_ACCMINUTE_INDEX, 2 * (INTERVENE_ACCMINUTE_INDEX + 2));
            String[] interveneAccminuteArray = reverseStringByte(interveneAccminuteStr);

            StringBuilder interveneAccMinuteBuilder = new StringBuilder();
            for (String byteArray : interveneAccminuteArray) {
                interveneAccMinuteBuilder.append(byteArray);
            }
            setInterveneAccMinute(Integer.parseInt(interveneAccMinuteBuilder.toString(), 16));


            String intervalAccminuteIndexStr = bleData.substring(2 * INTERVAL_ACCMINUTE_INDEX, 2 * (INTERVAL_ACCMINUTE_INDEX + 2));
            String[] intervalAccminuteArray = reverseStringByte(intervalAccminuteIndexStr);
            StringBuilder intervalAccminuteBuilder = new StringBuilder();
            for (String byteArray : intervalAccminuteArray) {
                intervalAccminuteBuilder.append(byteArray);
            }
            setIntervalAccMinute(Integer.parseInt(intervalAccminuteBuilder.toString(), 16));


            String intervalAccminute2IndexStr = bleData.substring(2 * INTERVAL_ACCMINUTE2_INDEX, 2 * (INTERVAL_ACCMINUTE2_INDEX + 2));
            String[] intervalAccminute2Array = reverseStringByte(intervalAccminute2IndexStr);
            StringBuilder intervalAccminute2Builder = new StringBuilder();
            for (String byteArray : intervalAccminute2Array) {
                intervalAccminute2Builder.append(byteArray);
            }
            setIntervalAccMinute2(Integer.parseInt(intervalAccminute2Builder.toString(), 16));

            String operationCmdData = bleData.substring(2 * OPERATION_CMD_INDEX, 2 * (OPERATION_CMD_INDEX + 1));
            String controlCmdData = bleData.substring(2 * CONTROL_RUN_CMD_INDEX, 2 * (CONTROL_RUN_CMD_INDEX + 1));
            //Integer.parseInt()
            setMearsureDistance(Integer.parseInt(disTanceData, 16));
            setBattery(Integer.parseInt(batteryData, 16));

            setControlCmd(controlCmdData);
            setOperationCmd(operationCmdData);
            setParseSuccess(true);
        } else {
            setParseSuccess(false);
        }
        return this;
    }


    public int getMearsureDistance() {
        return mearsureDistance;
    }

    public void setMearsureDistance(int mearsureDistance) {
        this.mearsureDistance = mearsureDistance;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getTrainTimeYear() {
        return trainTimeYear;
    }

    public void setTrainTimeYear(int trainTimeYear) {
        this.trainTimeYear = trainTimeYear;
    }

    public int getTrainTimeMonth() {
        return trainTimeMonth;
    }

    public void setTrainTimeMonth(int trainTimeMonth) {
        this.trainTimeMonth = trainTimeMonth;
    }

    public int getTrainTimeDay() {
        return trainTimeDay;
    }

    public void setTrainTimeDay(int trainTimeDay) {
        this.trainTimeDay = trainTimeDay;
    }

    public int getTrainTimeHour() {
        return trainTimeHour;
    }

    public void setTrainTimeHour(int trainTimeHour) {
        this.trainTimeHour = trainTimeHour;
    }

    public int getTrainTimeMinute() {
        return trainTimeMinute;
    }

    public void setTrainTimeMinute(int trainTimeMinute) {
        this.trainTimeMinute = trainTimeMinute;
    }

    public int getTrainTimeSecond() {
        return trainTimeSecond;
    }

    public void setTrainTimeSecond(int trainTimeSecond) {
        this.trainTimeSecond = trainTimeSecond;
    }

    public int getInterveneAccMinute() {
        return interveneAccMinute;
    }

    public void setInterveneAccMinute(int interveneAccMinute) {
        this.interveneAccMinute = interveneAccMinute;
    }

    public int getIntervalAccMinute() {
        return intervalAccMinute;
    }

    public void setIntervalAccMinute(int intervalAccMinute) {
        this.intervalAccMinute = intervalAccMinute;
    }

    public int getIntervalAccMinute2() {
        return intervalAccMinute2;
    }

    public void setIntervalAccMinute2(int intervalAccMinute2) {
        this.intervalAccMinute2 = intervalAccMinute2;
    }

    public String getOperationCmd() {
        return operationCmd;
    }

    public void setOperationCmd(String operationCmd) {
        this.operationCmd = operationCmd;
    }

    public String getControlCmd() {
        return controlCmd;
    }

    public void setControlCmd(String controlCmd) {
        this.controlCmd = controlCmd;
    }

    @Override
    public String toString() {
        return "实时反馈 {" +"\n" +
                "  mearsureDistance=" + mearsureDistance + "\n" +
                ", battery=" + battery + "\n" +
                ", trainTimeYear=" + trainTimeYear + "\n" +
                ", trainTimeMonth=" + trainTimeMonth + "\n" +
                ", trainTimeDay=" + trainTimeDay + "\n" +
                ", trainTimeHour=" + trainTimeHour + "\n" +
                ", trainTimeMinute=" + trainTimeMinute + "\n" +
                ", trainTimeSecond=" + trainTimeSecond + "\n" +
                ", interveneAccMinute=" + interveneAccMinute + "\n" +
                ", intervalAccMinute=" + intervalAccMinute + "\n" +
                ", intervalAccMinute2=" + intervalAccMinute2 + "\n" +
                ", operationCmd='" + operationCmd + '\'' + "\n" +
                ", controlCmd='" + controlCmd + '\'' +
                '}';
    }

    public static int calcBattery(int sourceInt) {
        /**
         * 最高位 1 表示充电状态
         */
        // 二进制 1000 0000  ==  128
        if (sourceInt >= 128) {
            return sourceInt - 128;
        }
        return sourceInt;
    }
}
