package com.zj.zhijue.bean.bledata.receive;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 * 干预反馈数据格式
 */
public class ReceiveInteveneFeedbackBleDataBean extends BaseParseCmdBean {
    /**
     * 定义	序号	内容
     * 校验码	TxByte1	0xa5
     * 校验码	TxByte2	0x51
     * 命令	TxByte3	0x14
     * 数据1	TxByte4	InterveneYear%256
     * 数据2	TxByte5	InterveneYear/256
     * 数据3	TxByte6	InterveneMonth
     * 数据4	TxByte7	Interveneday
     * 数据5	TxByte8	InterveneHour
     * 数据6	TxByte9	InterveneMinute
     * 数据7	TxByte10	InterveneSecond
     * 数据8	TxByte11	WeekKeyFre
     * 数据9	TxByte12	SpeedKeyFre
     * 数据10	TxByte13	InterveneKeyFre
     * 数据11	TxByte14	SpeedKeyFre2
     * 数据12	TxByte15	InterveneKeyFre2
     * 数据13	TxByte16	WeekAccMinute%256
     * 数据14	TxByte17	WeekAccMinute/256
     * 数据15	TxByte18	0
     * 数据16	TxByte19	MonitorData.CMD
     * 校验码	TxByte20	0xaa
     */

    public static final String USER_MONITOR_CMD = USER_DATA_PREFIX + "14";

    private final int INTERVENE_YEAR_INDEX = 3;//两个字节
    private final int INTERVENE_MONTH_INDEX = 5;//一个字节
    private final int INTERVENE_DAY_INDEX = 6;//
    private final int INTERVENE_HOUR_INDEX = 7;//
    private final int INTERVENE_MINUTE_INDEX = 8;//
    private final int INTERVENE_SECOND_INDEX = 9;//

    private final int WEEK_KEY_FRE_INDEX = 10;
    private final int SPEED_KEY_FRE_INDEX = 11;
    private final int INTERVENE_KEY_FRE_INDEX = 12;
    private final int SPEED_KEY_FRE2_INDEX = 13;
    private final int INTERVENE_KEY_FRE2_INDEX = 14;
    private final int WEEK_ACC_MINUTE_INDEX = 15;//两个字节

    private final int MONITORDATA_CMD_INDEX = 18;//


    private int interveneYear;
    private int interveneMonth;
    private int interveneDay;
    private int interveneHour;
    private int interveneMinute;
    private int interveneSecond;
    private int weekKeyFre;
    private int speedKeyFre;
    private int interveneKeyFre;
    private int speedKeyFre2;
    private int interveneKeyFre2;
    private int weekAccMinute;
    private String monitorCmd;

    /**     *
     * @param bleData
     * @return
     */
    @Override
    protected BaseParseCmdBean parseBleDataStr2Bean(String bleData) {
        if (!CommonUtils.isEmpty(bleData)
            && bleData.toLowerCase().startsWith(USER_MONITOR_CMD)
            && bleData.toLowerCase().endsWith(USER_DATA_SUFIX)) {

            String interveneYear = bleData.substring( 2 * INTERVENE_YEAR_INDEX, 2 * (INTERVENE_YEAR_INDEX + 2));
            String[] interveneYearArray = reverseStringByte(interveneYear);
            StringBuilder interveneYearArrayBuilder = new StringBuilder();
            for (String byteArray : interveneYearArray) {
                interveneYearArrayBuilder.append(byteArray);
            }
            setInterveneYear(Integer.parseInt(interveneYearArrayBuilder.toString(), 16));

            String interveneMonth = bleData.substring(2 * INTERVENE_MONTH_INDEX, 2 * (INTERVENE_MONTH_INDEX + 1));
            setInterveneMonth(Integer.parseInt(interveneMonth,  16));

            String interveneDay = bleData.substring(2 * INTERVENE_DAY_INDEX, 2 * (INTERVENE_DAY_INDEX + 1));
            setInterveneDay(Integer.parseInt(interveneDay, 16));

            String interveneHour = bleData.substring(2 * INTERVENE_HOUR_INDEX, 2 * (INTERVENE_HOUR_INDEX + 1));
            setInterveneHour(Integer.parseInt(interveneHour, 16));

            String interveneMinute = bleData.substring(2 * INTERVENE_MINUTE_INDEX, 2 * (INTERVENE_MINUTE_INDEX + 1));
            setInterveneMinute(Integer.parseInt(interveneMinute, 16));

            String interveneSecond = bleData.substring(2 * INTERVENE_SECOND_INDEX,  2 * (INTERVENE_SECOND_INDEX +1));
            setInterveneSecond(Integer.parseInt(interveneSecond, 16));

            String weekKeyFre = bleData.substring(2 * WEEK_KEY_FRE_INDEX, 2 * (WEEK_KEY_FRE_INDEX + 1));
            setWeekKeyFre(Integer.parseInt(weekKeyFre, 16));

            String speedKeyFre = bleData.substring(2 * SPEED_KEY_FRE_INDEX, 2 * (SPEED_KEY_FRE_INDEX + 1));
            setSpeedKeyFre(Integer.parseInt(speedKeyFre, 16));

            String interveneKeyFre = bleData.substring(2 * INTERVENE_KEY_FRE_INDEX,2 * (INTERVENE_KEY_FRE_INDEX + 1));
            setInterveneKeyFre(Integer.parseInt(interveneKeyFre, 16));

            String speedKeyFre2 = bleData.substring(2 * SPEED_KEY_FRE2_INDEX, 2 * (SPEED_KEY_FRE2_INDEX + 1));
            setSpeedKeyFre2(Integer.parseInt(speedKeyFre2,16));

            String interveneKeyFre2 = bleData.substring(2 * INTERVENE_KEY_FRE2_INDEX, 2 * (INTERVENE_KEY_FRE2_INDEX + 1));
            setInterveneKeyFre2(Integer.parseInt(interveneKeyFre2,16 ));

            String weekAccMinute = bleData.substring(2 *WEEK_ACC_MINUTE_INDEX, 2 * (WEEK_ACC_MINUTE_INDEX + 2));


            String[] weekAccMinuteArray = reverseStringByte(weekAccMinute);
            StringBuilder weekAccMinuteArrayBuilder = new StringBuilder();
            for (String byteArray : weekAccMinuteArray) {
                weekAccMinuteArrayBuilder.append(byteArray);
            }
            setWeekAccMinute(Integer.parseInt(weekAccMinuteArrayBuilder.toString(), 16));


            String monitorCmd = bleData.substring(2 * MONITORDATA_CMD_INDEX, 2 * (MONITORDATA_CMD_INDEX + 1));
            setMonitorCmd(monitorCmd);

            setParseSuccess(true);
        } else {
            setParseSuccess(false);
        }
        return this;
    }


    public int getInterveneYear() {
        return interveneYear;
    }

    public void setInterveneYear(int interveneYear) {
        this.interveneYear = interveneYear;
    }

    public int getInterveneMonth() {
        return interveneMonth;
    }

    public void setInterveneMonth(int interveneMonth) {
        this.interveneMonth = interveneMonth;
    }

    public int getInterveneDay() {
        return interveneDay;
    }

    public void setInterveneDay(int interveneDay) {
        this.interveneDay = interveneDay;
    }

    public int getInterveneHour() {
        return interveneHour;
    }

    public void setInterveneHour(int interveneHour) {
        this.interveneHour = interveneHour;
    }

    public int getInterveneMinute() {
        return interveneMinute;
    }

    public void setInterveneMinute(int interveneMinute) {
        this.interveneMinute = interveneMinute;
    }

    public int getInterveneSecond() {
        return interveneSecond;
    }

    public void setInterveneSecond(int interveneSecond) {
        this.interveneSecond = interveneSecond;
    }

    public int getWeekKeyFre() {
        return weekKeyFre;
    }

    public void setWeekKeyFre(int weekKeyFre) {
        this.weekKeyFre = weekKeyFre;
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

    public int getWeekAccMinute() {
        return weekAccMinute;
    }

    public void setWeekAccMinute(int weekAccMinute) {
        this.weekAccMinute = weekAccMinute;
    }

    public String getMonitorCmd() {
        return monitorCmd;
    }

    public void setMonitorCmd(String monitorCmd) {
        this.monitorCmd = monitorCmd;
    }

    @Override
    public String toString() {
        return "干预反馈{" +"\n" +
                "  interveneYear=" + interveneYear + "\n" +
                ", interveneMonth=" + interveneMonth + "\n" +
                ", interveneDay=" + interveneDay + "\n" +
                ", interveneHour=" + interveneHour + "\n" +
                ", interveneMinute=" + interveneMinute + "\n" +
                ", interveneSecond=" + interveneSecond + "\n" +
                ", weekKeyFre=" + weekKeyFre + "\n" +
                ", speedKeyFre=" + speedKeyFre + "\n" +
                ", interveneKeyFre=" + interveneKeyFre + "\n" +
                ", speedKeyFre2=" + speedKeyFre2 + "\n" +
                ", interveneKeyFre2=" + interveneKeyFre2 + "\n" +
                ", weekAccMinute=" + weekAccMinute + "\n" +
                ", monitorCmd='" + monitorCmd + '\'' +
                '}';
    }
}
