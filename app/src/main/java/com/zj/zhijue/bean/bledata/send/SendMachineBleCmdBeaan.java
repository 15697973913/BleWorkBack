package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

public class SendMachineBleCmdBeaan extends BaseCmdBean {
    /**
     定义	序号	内容
     校验码	RxByte1	0xa5
     校验码	RxByte2	0x51
     命令	RxByte3	0x11
     数据1	RxByte4	MachineData.MaxRunNumber
     数据2	RxByte5	MachineData.StratSpeed
     数据3	RxByte6	MachineData.SetSpeedInc
     数据4	RxByte7	MachineData.StopSpeed
     数据5	RxByte8	MachineData.CommonSpeed
     数据6	RxByte9	MachineData.MachineData6
     数据7	RxByte10	MachineData.MachineData7
     数据8	RxByte11	MachineData.MachineData8
     数据9	RxByte12	MachineData.MachineData9
     数据10	RxByte13	MachineData.SystemYearL
     数据11	RxByte14	MachineData.SystemYearH
     数据12	RxByte15	MachineData.SystemMonth
     数据13	RxByte16	MachineData.SystemDay
     数据14	RxByte17	MachineData.SystemHour
     数据15	RxByte18	MachineData.SystemMinute
     数据16	RxByte19	MachineData.SystemSecond
     校验码	RxByte20	0xaa
     */
    protected String[] robotReceiveBleCmdDataStr = {"a5", "51", "11", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "aa"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "a55111";


    /**
     * 下标从0开始
     */
    private final int MAX_RUN_NUMBER_INDEX = 3;

    private final int START_SPEED_INDEX = 4;

    private final int SET_SPEED_INC_INDEX = 5;

    private final int STOP_SPEED_INDEX = 6;
    /**
     * 7 到 11  之间的5个字节是预留位
     */

    private final int COMMON_SPEED_INDEX = 7;
    private final int MACHINE_DATA_6_INDEX = 8;
    private final int MACHINE_DATA_7_INDEX = 9;
    private final int MACHINE_DATA_8_INDEX = 10;
    private final int MACHINE_DATA_9_INDEX = 11;
    /**
     * 年
     */
    private final int SYSTEM_YEAR_LOW_INDEX = 12;
    private final int SYSTEM_YEAR_HIGH_INDEX = 13;
    /**
     * 月
     */
    private final int SYSTEM_MONTH_INDEX = 14;
    /**
     * 日
     */
    private final int SYSTEM_DAY_INDEX = 15;

    /**
     * 时
     */
    private final int SYSTEM_HOUR_INDEX = 16;

    /**
     * 分
     */
    private final int SYSTEM_MINUTE_INDEX = 17;

    /**
     * 秒
     */
    private final int SYSTEM_SECOND_INDEX = 18;

    private int maxRunNumber;

    private int startSpeed;

    private int setSpeedInc;

    private int stopSpeed;

    private int year;

    private int month;

    private int day;

    private int hour;

    private int minute;

    private int seconds;

    private int commonSpeed;
    private int machineData6;
    private int machineData7;
    private int machineData8;
    private int machineData9;

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
     *  向 cmdDataArary 中 put 数据
     */
    @Override
    protected void putData2DataArray() {

        String maxRunNumber = decimalism2Hex(getMaxRunNumber(), 2);
        cmdDataArary.put(MAX_RUN_NUMBER_INDEX, maxRunNumber);

        String startSpeed = decimalism2Hex(getStartSpeed(), 2);
        cmdDataArary.put(START_SPEED_INDEX, startSpeed);

        String setSpeedInc = decimalism2Hex(getSetSpeedInc(), 2);
        cmdDataArary.put(SET_SPEED_INC_INDEX, setSpeedInc);

        String stopSpeed = decimalism2Hex(getStopSpeed(), 2);
        cmdDataArary.put(STOP_SPEED_INDEX, stopSpeed);

        String machineData5 = decimalism2Hex(getCommonSpeed(), 2);
        cmdDataArary.put(COMMON_SPEED_INDEX, machineData5);

        String machineData6 = decimalism2Hex(getMachineData6(), 2);
        cmdDataArary.put(MACHINE_DATA_6_INDEX, machineData6);

        String machineData7 = decimalism2Hex(getMachineData7(), 2);
        cmdDataArary.put(MACHINE_DATA_7_INDEX, machineData7);

        String machineData8 = decimalism2Hex(getMachineData8(), 2);
        cmdDataArary.put(MACHINE_DATA_8_INDEX, machineData8);

        String machineData9 = decimalism2Hex(getMachineData9(), 2);
        cmdDataArary.put(MACHINE_DATA_9_INDEX, machineData9);

        /**
         * 年
         */

        String yearHexStr = decimalism2Hex(getYear(), 4);
        String[] yearHexArray = reverseStringByte(yearHexStr);

        for(int i =0; i < yearHexArray.length; i++) {
            cmdDataArary.put(SYSTEM_YEAR_LOW_INDEX + i, yearHexArray[i]);
        }

        /**
         * 月
         */
        String monthHexStr = decimalism2Hex(getMonth(), 2);
        cmdDataArary.put(SYSTEM_MONTH_INDEX, monthHexStr);

        /**
         * 日
         */
        String dayHexStr = decimalism2Hex(getDay(), 2);
        cmdDataArary.put(SYSTEM_DAY_INDEX, dayHexStr);

        /**
         * 时
         */
        String hourHexStr = decimalism2Hex(getHour(), 2);
        cmdDataArary.put(SYSTEM_HOUR_INDEX, hourHexStr);

        /**
         * 分
         */
        String minuteHexStr = decimalism2Hex(getMinute(), 2);
        cmdDataArary.put(SYSTEM_MINUTE_INDEX, minuteHexStr);

        /**
         * 秒
         */
        String secondHexStr = decimalism2Hex(getSeconds(), 2);
        cmdDataArary.put(SYSTEM_SECOND_INDEX, secondHexStr);


    }


    public int getMaxRunNumber() {
        return maxRunNumber;
    }

    public void setMaxRunNumber(int maxRunNumber) {
        this.maxRunNumber = maxRunNumber;
    }

    public int getStartSpeed() {
        return startSpeed;
    }

    public void setStartSpeed(int startSpeed) {
        this.startSpeed = startSpeed;
    }

    public int getSetSpeedInc() {
        return setSpeedInc;
    }

    public void setSetSpeedInc(int setSpeedInc) {
        this.setSpeedInc = setSpeedInc;
    }

    public int getStopSpeed() {
        return stopSpeed;
    }

    public void setStopSpeed(int stopSpeed) {
        this.stopSpeed = stopSpeed;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }


    public int getCommonSpeed() {
        return commonSpeed;
    }

    public void setCommonSpeed(int commonSpeed) {
        this.commonSpeed = commonSpeed;
    }

    public int getMachineData6() {
        return machineData6;
    }

    public void setMachineData6(int machineData6) {
        this.machineData6 = machineData6;
    }

    public int getMachineData7() {
        return machineData7;
    }

    public void setMachineData7(int machineData7) {
        this.machineData7 = machineData7;
    }

    public int getMachineData8() {
        return machineData8;
    }

    public void setMachineData8(int machineData8) {
        this.machineData8 = machineData8;
    }

    public int getMachineData9() {
        return machineData9;
    }

    public void setMachineData9(int machineData9) {
        this.machineData9 = machineData9;
    }


    @Override
    public String toString() {
        return "SendMachineBleCmdBeaan{" + "\n" +
                "maxRunNumber=" + maxRunNumber + "\n" +
                ", startSpeed=" + startSpeed + "\n" +
                ", setSpeedInc=" + setSpeedInc + "\n" +
                ", stopSpeed=" + stopSpeed + "\n" +
                ", year=" + year + "\n" +
                ", month=" + month + "\n" +
                ", day=" + day + "\n" +
                ", hour=" + hour + "\n" +
                ", minute=" + minute + "\n" +
                ", seconds=" + seconds + "\n" +
                ", commonSpeed=" + commonSpeed + "\n" +
                ", machineData6=" + machineData6 + "\n" +
                ", machineData7=" + machineData7 + "\n" +
                ", machineData8=" + machineData8 + "\n" +
                ", machineData9=" + machineData9 + "\n" +
                '}';
    }
}
