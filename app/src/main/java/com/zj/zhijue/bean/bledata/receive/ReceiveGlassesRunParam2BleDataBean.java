package com.zj.zhijue.bean.bledata.receive;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 *  接收眼镜上传的训练数据 参数2
 */
public class ReceiveGlassesRunParam2BleDataBean extends BaseParseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	TxByte1	0xa5
     * 校验码	TxByte2	0x51
     * 命令	TxByte3	0x1c
     * 数据1	TxByte4	BackWeekAccMinute[0]%256
     * 数据2	TxByte5	BackWeekAccMinute[0]/256
     * 数据3	TxByte6	BackWeekAccMinute[1]%256
     * 数据4	TxByte7	BackWeekAccMinute[1]/256
     * 数据5	TxByte8	BackWeekAccMinute[2]%256
     * 数据6	TxByte9	BackWeekAccMinute[2]/256
     * 数据7	TxByte10	BackWeekAccMinute[3]%256
     * 数据8	TxByte11	BackWeekAccMinute[3]/256
     * 数据9	TxByte12	PlusInterval%256
     * 数据10	TxByte13	PlusInterval/256
     * 数据11	TxByte14	MinusInterval%256
     * 数据12	TxByte15	MinusInterval/256
     * 数据13	TxByte16	PlusInc
     * 数据14	TxByte17	MinusInc
     * 数据15	TxByte18	IncPer
     * 数据16	TxByte19	MonitorData.CMD
     * 校验码	TxByte20	0xaa
     */

    public static final String USER_MONITOR_CMD = USER_DATA_PREFIX + "1c";

    private final int BACK_WEEK_ACC_MINUTE0_INDEX = 3;//两个字节
    private final int BACK_WEEK_ACC_MINUTE1_INDEX = 5;//两个字节
    private final int BACK_WEEK_ACC_MINUTE2_INDEX = 7;//两个字节
    private final int BACK_WEEK_ACC_MINUTE3_INDEX = 9;//两个字节
    private final int PLUS_INTERVAL_INDEX = 11;//两个字节
    private final int MINUS_INTERVAL_INDEX = 13;//两个字节
    private final int PLUS_INC_INDEX = 15;//一个字节
    private final int MINUS_INC_INDEX = 16;//一个字节
    private final int INC_PER_INDEX = 17;//一个字节
    private final int MONITOR_DATA_CMD_INDEX = 18;//一个字节

    private int backWeekAccMinute0;
    private int backWeekAccMinute1;
    private int backWeekAccMinute2;
    private int backWeekAccMinute3;

    private int plusInterval;
    private int minusInterval;
    private int plusInc;
    private int minusInc;
    private int incPer;
    private String monitorData_CMD;



    @Override
    protected BaseParseCmdBean parseBleDataStr2Bean(String bleData) {
        if (!CommonUtils.isEmpty(bleData)
                && bleData.toLowerCase().startsWith(USER_MONITOR_CMD)
                && bleData.toLowerCase().endsWith(USER_DATA_SUFIX)) {

            String backWeekAccMinute0HexStr = bleData.substring( 2 * BACK_WEEK_ACC_MINUTE0_INDEX, 2 * (BACK_WEEK_ACC_MINUTE0_INDEX + 2));
            String[] backWeekAccMinute0HexArray = reverseStringByte(backWeekAccMinute0HexStr);
            StringBuilder backWeekAccMinute0Builder = new StringBuilder();
            for (String byteArray : backWeekAccMinute0HexArray) {
                backWeekAccMinute0Builder.append(byteArray);
            }
            setBackWeekAccMinute0(Integer.parseInt(backWeekAccMinute0Builder.toString(), 16));

            String backWeekAccMinute1HexStr = bleData.substring( 2 * BACK_WEEK_ACC_MINUTE1_INDEX, 2 * (BACK_WEEK_ACC_MINUTE1_INDEX + 2));
            String[] backWeekAccMinute1HexArray = reverseStringByte(backWeekAccMinute1HexStr);
            StringBuilder backWeekAccMinute1Builder = new StringBuilder();
            for (String byteArray : backWeekAccMinute1HexArray) {
                backWeekAccMinute1Builder.append(byteArray);
            }
            setBackWeekAccMinute1(Integer.parseInt(backWeekAccMinute1Builder.toString(), 16));


            String backWeekAccMinute2HexStr = bleData.substring( 2 * BACK_WEEK_ACC_MINUTE2_INDEX, 2 * (BACK_WEEK_ACC_MINUTE2_INDEX + 2));
            String[] backWeekAccMinute2HexArray = reverseStringByte(backWeekAccMinute2HexStr);
            StringBuilder backWeekAccMinute2Builder = new StringBuilder();
            for (String byteArray : backWeekAccMinute2HexArray) {
                backWeekAccMinute2Builder.append(byteArray);
            }
            setBackWeekAccMinute2(Integer.parseInt(backWeekAccMinute2Builder.toString(), 16));


            String backWeekAccMinute3HexStr = bleData.substring( 2 * BACK_WEEK_ACC_MINUTE3_INDEX, 2 * (BACK_WEEK_ACC_MINUTE3_INDEX + 2));
            String[] backWeekAccMinute3HexArray = reverseStringByte(backWeekAccMinute3HexStr);
            StringBuilder backWeekAccMinute3Builder = new StringBuilder();
            for (String byteArray : backWeekAccMinute3HexArray) {
                backWeekAccMinute3Builder.append(byteArray);
            }
            setBackWeekAccMinute3(Integer.parseInt(backWeekAccMinute3Builder.toString(), 16));

            String plusIntervalHexStr = bleData.substring( 2 * PLUS_INTERVAL_INDEX, 2 * (PLUS_INTERVAL_INDEX + 2));
            String[] plusIntervalHexArray = reverseStringByte(plusIntervalHexStr);
            StringBuilder plusIntervalBuilder = new StringBuilder();
            for (String byteArray : plusIntervalHexArray) {
                plusIntervalBuilder.append(byteArray);
            }
            setPlusInterval(Integer.parseInt(plusIntervalBuilder.toString(), 16));

            String minusIntervalHexStr = bleData.substring( 2 * MINUS_INTERVAL_INDEX, 2 * (MINUS_INTERVAL_INDEX + 2));
            String[] minusIntervalHexArray = reverseStringByte(minusIntervalHexStr);
            StringBuilder minusIntervalBuilder = new StringBuilder();
            for (String byteArray : minusIntervalHexArray) {
                minusIntervalBuilder.append(byteArray);
            }
            setMinusInterval(Integer.parseInt(minusIntervalBuilder.toString(), 16));

            String plusIncHexStr = bleData.substring( 2 * PLUS_INC_INDEX, 2 * (PLUS_INC_INDEX + 1));
            setPlusInc(Integer.parseInt(plusIncHexStr, 16));

            String minusIncHexStr = bleData.substring( 2 * MINUS_INC_INDEX, 2 * (MINUS_INC_INDEX + 1));
            setMinusInc(Integer.parseInt(minusIncHexStr, 16));

            String incPerHexStr = bleData.substring( 2 * INC_PER_INDEX, 2 * (INC_PER_INDEX + 1));
            setIncPer(Integer.parseInt(incPerHexStr, 16));

            String monitorData_CMDHexStr = bleData.substring( 2 * MONITOR_DATA_CMD_INDEX, 2 * (MONITOR_DATA_CMD_INDEX + 1));
            setMonitorData_CMD(monitorData_CMDHexStr);

            setParseSuccess(true);
        } else {
            setParseSuccess(false);
        }
        return this;
    }


    public int getBackWeekAccMinute0() {
        return backWeekAccMinute0;
    }

    public void setBackWeekAccMinute0(int backWeekAccMinute0) {
        this.backWeekAccMinute0 = backWeekAccMinute0;
    }

    public int getBackWeekAccMinute1() {
        return backWeekAccMinute1;
    }

    public void setBackWeekAccMinute1(int backWeekAccMinute1) {
        this.backWeekAccMinute1 = backWeekAccMinute1;
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

    public String getMonitorData_CMD() {
        return monitorData_CMD;
    }

    public void setMonitorData_CMD(String monitorData_CMD) {
        this.monitorData_CMD = monitorData_CMD;
    }
}
