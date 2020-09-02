package com.zj.zhijue.bean.bledata.receive;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 *  接收眼镜上传的训练数据 参数1
 */
public class ReceiveGlassesRunParam1BleDataBean extends BaseParseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	TxByte1	0xa5
     * 校验码	TxByte2	0x51
     * 命令	TxByte3	0x1b
     * 数据1	TxByte4	CurrentUser.Code1
     * 数据2	TxByte5	CurrentUser.Code2
     * 数据3	TxByte6	CurrentUser.Code3
     * 数据4	TxByte7	CurrentUser.Code4
     * 数据5	TxByte8	CurrentUser.Code5
     * 数据6	TxByte9	MinMinusInterval%256
     * 数据7	TxByte10	MinMinusInterval/256
     * 数据8	TxByte11	MinPlusInterval%256
     * 数据9	TxByte12	MinPlusInterval/256
     * 数据10	TxByte13	CommonNumber
     * 数据11	TxByte14	InterveneAccMinute%256
     * 数据12	TxByte15	InterveneAccMinute/256
     * 数据13	TxByte16	WeekKeyFre
     * 数据14	TxByte17	WeekAccMinute%256
     * 数据15	TxByte18	WeekAccMinute/256
     * 数据16	TxByte19	MonitorData.CMD
     * 校验码	TxByte20	0xaa
     */

    public static final String USER_MONITOR_CMD = USER_DATA_PREFIX + "1b";

    private final int CURRENTUSER_CODE_INDEX = 3;//5个字节
    private final int MIN_MINUS_INTERVAL_INDEX = 8;//两个字节
    private final int MIN_PLUS_INTERVAL_INDEX = 10;//两个字节
    private final int COMMON_NUMBER_INDEX = 12;//一个字节
    private final int INTERVENE_ACC_MINUTE_INDEX = 13;//两个字节
    private final int WEEK_KEY_FRE_INDEX = 15;//一个字节
    private final int WEEK_ACC_MINUTE_INDEX = 16;//两个字节
    private final int OPERATION_CMD_INDEX = 18;//操作码

    private long currentUserCode;
    private int minMinusInterval;
    private int minPlusInterval;
    private int commonNumber;
    private int interveneAccMinute;
    private int weekKeyFre;
    private int weekAccMinute;
    private String monitorData_CMD;



    @Override
    protected BaseParseCmdBean parseBleDataStr2Bean(String bleData) {
        if (!CommonUtils.isEmpty(bleData)
                && bleData.toLowerCase().startsWith(USER_MONITOR_CMD)
                && bleData.toLowerCase().endsWith(USER_DATA_SUFIX)) {

            String currentUserCodeHexStr = bleData.substring( 2 * CURRENTUSER_CODE_INDEX, 2 * (CURRENTUSER_CODE_INDEX + 5));

            String[] currentUserCodeArray = reverseStringByte(currentUserCodeHexStr);
            StringBuilder currentUserCodeBuilder = new StringBuilder();
            for (String byteArray : currentUserCodeArray) {
                currentUserCodeBuilder.append(byteArray);
            }

            setCurrentUserCode(Long.parseLong(currentUserCodeBuilder.toString(), 16));

            String minMinusIntervalHexStr = bleData.substring(2 * MIN_MINUS_INTERVAL_INDEX , 2 * (MIN_MINUS_INTERVAL_INDEX + 2));
            String[] minMinusIntervalHexArray = reverseStringByte(minMinusIntervalHexStr);
            StringBuilder minMinusIntervalBuilder = new StringBuilder();
            for (String byteArray : minMinusIntervalHexArray) {
                minMinusIntervalBuilder.append(byteArray);
            }
            setMinMinusInterval(Integer.parseInt(minMinusIntervalBuilder.toString(), 16));

            String minPlusIntervalHexStr = bleData.substring(2 * MIN_PLUS_INTERVAL_INDEX , 2 * (MIN_PLUS_INTERVAL_INDEX + 2));
            String[] minPlusIntervalHexArray = reverseStringByte(minPlusIntervalHexStr);
            StringBuilder minPlusIntervalBuilder = new StringBuilder();
            for (String byteArray : minPlusIntervalHexArray) {
                minPlusIntervalBuilder.append(byteArray);
            }
            setMinPlusInterval(Integer.parseInt(minPlusIntervalBuilder.toString(), 16));

            String commonNumberHexStr = bleData.substring( 2 * COMMON_NUMBER_INDEX, 2 * (COMMON_NUMBER_INDEX + 1));
            setCommonNumber(Integer.parseInt(commonNumberHexStr, 16));

            String interveneAccMinuteHexStr = bleData.substring(2 * INTERVENE_ACC_MINUTE_INDEX , 2 * (INTERVENE_ACC_MINUTE_INDEX + 2));
            String[] interveneAccMinuteHexArray = reverseStringByte(interveneAccMinuteHexStr);
            StringBuilder interveneAccMinuteBuilder = new StringBuilder();
            for (String byteArray : interveneAccMinuteHexArray) {
                interveneAccMinuteBuilder.append(byteArray);
            }
            setInterveneAccMinute(Integer.parseInt(interveneAccMinuteBuilder.toString(), 16));

            String weekKeyFreHexStr = bleData.substring( 2 * WEEK_KEY_FRE_INDEX, 2 * (WEEK_KEY_FRE_INDEX + 1));
            setWeekKeyFre(Integer.parseInt(weekKeyFreHexStr, 16));

            String weekAccMinuteHexStr = bleData.substring(2 * WEEK_ACC_MINUTE_INDEX , 2 * (WEEK_ACC_MINUTE_INDEX + 2));
            String[] weekAccMinuteHexArray = reverseStringByte(weekAccMinuteHexStr);
            StringBuilder weekAccMinuteBuilder = new StringBuilder();
            for (String byteArray : weekAccMinuteHexArray) {
                weekAccMinuteBuilder.append(byteArray);
            }
            setWeekAccMinute(Integer.parseInt(weekAccMinuteBuilder.toString(), 16));

            String monitorDataCMDHexStr = bleData.substring( 2 * OPERATION_CMD_INDEX, 2 * (OPERATION_CMD_INDEX + 1));
            setMonitorData_CMD(monitorDataCMDHexStr);

            setParseSuccess(true);
        } else {
            setParseSuccess(false);
        }
        return this;
    }

    public long getCurrentUserCode() {
        return currentUserCode;
    }

    public void setCurrentUserCode(long currentUserCode) {
        this.currentUserCode = currentUserCode;
    }

    public int getMinMinusInterval() {
        return minMinusInterval;
    }

    public void setMinMinusInterval(int minMinusInterval) {
        this.minMinusInterval = minMinusInterval;
    }

    public int getMinPlusInterval() {
        return minPlusInterval;
    }

    public void setMinPlusInterval(int minPlusInterval) {
        this.minPlusInterval = minPlusInterval;
    }

    public int getCommonNumber() {
        return commonNumber;
    }

    public void setCommonNumber(int commonNumber) {
        this.commonNumber = commonNumber;
    }

    public int getInterveneAccMinute() {
        return interveneAccMinute;
    }

    public void setInterveneAccMinute(int interveneAccMinute) {
        this.interveneAccMinute = interveneAccMinute;
    }

    public int getWeekKeyFre() {
        return weekKeyFre;
    }

    public void setWeekKeyFre(int weekKeyFre) {
        this.weekKeyFre = weekKeyFre;
    }

    public int getWeekAccMinute() {
        return weekAccMinute;
    }

    public void setWeekAccMinute(int weekAccMinute) {
        this.weekAccMinute = weekAccMinute;
    }

    public String getMonitorData_CMD() {
        return monitorData_CMD;
    }

    public void setMonitorData_CMD(String monitorData_CMD) {
        this.monitorData_CMD = monitorData_CMD;
    }
}
