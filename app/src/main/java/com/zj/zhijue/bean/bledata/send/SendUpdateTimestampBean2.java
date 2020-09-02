package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;
import com.android.common.baselibrary.log.MLog;

/**
 * 更新时间戳
 */
public class SendUpdateTimestampBean2 extends BaseCmdBean {
    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xaa
     * 命令	RxByte3	0x60
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
    protected String[] robotReceiveBleCmdDataStr = {"aa", "60", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "aa60";


    /**
     * 年
     */
    private final int SYSTEM_YEAR_LOW_INDEX = 2;
    /**
     * 月
     */
    private final int SYSTEM_MONTH_INDEX = 4;
    /**
     * 日
     */
    private final int SYSTEM_DAY_INDEX = 5;

    /**
     * 时
     */
    private final int SYSTEM_HOUR_INDEX = 6;

    /**
     * 分
     */
    private final int SYSTEM_MINUTE_INDEX = 7;

    /**
     * 秒
     */
    private final int SYSTEM_SECOND_INDEX = 8;

    /**
     * 时区
     */
    private final int SYSTEM_UTC_INDEX = 9;

    /**
     * 时区-时间
     */
    private final int SYSTEM_UTC_TIME_INDEX = 10;

    /**
     * 星期
     */
    private final int SYSTEM_WEEK_INDEX = 14;


    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int seconds;
    private int utc;
    private long utcTime;
    private int week;

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
         * 年
         */
        String yearHexStr = decimalism2Hex(getYear(), 4);
        String[] yearHexArray = reverseStringByte(yearHexStr);

        for (int i = 0; i < yearHexArray.length; i++) {
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
         * 秒utcTime
         */
        String secondHexStr = decimalism2Hex(getSeconds(), 2);
        cmdDataArary.put(SYSTEM_SECOND_INDEX, secondHexStr);

        /**
         * 时区
         */
        String utcHexStr = decimalism2Hex(getUtc(), 2);
        cmdDataArary.put(SYSTEM_UTC_INDEX, utcHexStr);

        /**
         * 时区-time
         */
        String utcTimeHexStr = decimalismLong2Hex(String.valueOf(getUtcTime()), 8);
        String[] utcTimeHexStr2Array = reverseStringByte(utcTimeHexStr);
        for (int i = 0; i < utcTimeHexStr2Array.length; i++) {
            cmdDataArary.put(SYSTEM_UTC_TIME_INDEX + i, utcTimeHexStr2Array[i]);
        }

        /**
         * 星期
         */
        String weekHexStr = decimalism2Hex(getWeek(), 2);
        cmdDataArary.put(SYSTEM_WEEK_INDEX, weekHexStr);

    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
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


    public int getUtc() {
        return utc;
    }

    public void setUtc(int utc) {
        this.utc = utc;
    }

    public long getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(long utcTime) {
        this.utcTime = utcTime;
    }

    @Override
    public String toString() {
        return "SendMachineBleCmdBeaan{" + "\n" +
                ", year=" + year + "\n" +
                ", month=" + month + "\n" +
                ", day=" + day + "\n" +
                ", hour=" + hour + "\n" +
                ", minute=" + minute + "\n" +
                ", seconds=" + seconds + "\n" +
                ", utc=" + utc + "\n" +
                ", utcTime=" + utcTime + "\n" +
                '}';
    }
}
