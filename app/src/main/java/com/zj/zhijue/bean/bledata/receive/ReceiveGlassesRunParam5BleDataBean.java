package com.zj.zhijue.bean.bledata.receive;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 *  接收眼镜上传的训练数据 参数5
 */
public class ReceiveGlassesRunParam5BleDataBean extends BaseParseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	TxByte1	0xa5
     * 校验码	TxByte2	0x51
     * 命令	TxByte3	0x1f
     * 数据1	TxByte4	trainingState   0 训练中   1 训练完成
     * 数据2	TxByte5	TrainingState2   0 训练中   1 训练完成
     * 数据3	TxByte6	AdjustSpeed
     * 数据4	TxByte7	MaxRunSpeed
     * 数据5	TxByte8	MinRunSpeed
     * 数据6	TxByte9	AdjustSpeed2
     * 数据7	TxByte10	MaxRunSpeed2
     * 数据8	TxByte11	MinRunSpeed2
     * 数据9	TxByte12	0
     * 数据10	TxByte13	0
     * 数据11	TxByte14	0
     * 数据12	TxByte15	0
     * 数据13	TxByte16	0
     * 数据14	TxByte17	0
     * 数据15	TxByte18	0
     * 数据16	TxByte19	MonitorData.CMD
     * 校验码	TxByte20	0xaa
     */

    private final int TRAININGSTATE_INDEX = 3;
    private final int TRAININGSTATE2_INDEX = 4;
    private final int ADJUSTSPEED_INDEX = 5;
    private final int MAXRUNSPEED_INDEX = 6;
    private final int MINRUNSPEED_INDEX = 7;
    private final int ADJUSTSPEED2_INDEX = 8;
    private final int MAXRUNSPEED2_INDEX = 9;
    private final int MINRUNSPEED2_INDEX = 10;
    private final int RXBYTE12_INDEX = 11;
    private final int RXBYTE13_INDEX = 12;
    private final int RXBYTE14_INDEX = 13;
    private final int RXBYTE15_INDEX = 14;
    private final int RXBYTE16_INDEX = 15;
    private final int RXBYTE17_INDEX = 16;
    private final int RXBYTE18_INDEX = 17;

    private int trainingState;
    private int trainingState2;
    private int AdjustSpeed;
    private int MaxRunSpeed;
    private int MinRunSpeed;
    private int AdjustSpeed2;
    private int MaxRunSpeed2;
    private int MinRunSpeed2;
    private int txByte12;
    private int txByte13;
    private int txByte14;
    private int txByte15;
    private int txByte16;
    private int txByte17;
    private int txByte18;


    public static final String USER_MONITOR_CMD = USER_DATA_PREFIX + "1f";


    private final int MONITOR_DATA_CMD_INDEX = 18;//一个字节


    private String monitorData_CMD;



    @Override
    protected BaseParseCmdBean parseBleDataStr2Bean(String bleData) {
        if (!CommonUtils.isEmpty(bleData)
                && bleData.toLowerCase().startsWith(USER_MONITOR_CMD)
                && bleData.toLowerCase().endsWith(USER_DATA_SUFIX)) {

            String trainState = bleData.substring( 2 * TRAININGSTATE_INDEX, 2 * (TRAININGSTATE_INDEX + 1));
            setTrainingState(Integer.parseInt(trainState, 16));

            String trainState2 = bleData.substring( 2 * TRAININGSTATE2_INDEX, 2 * (TRAININGSTATE2_INDEX + 1));
            setTrainingState2(Integer.parseInt(trainState2, 16));

            String adJustSpeed = bleData.substring( 2 * ADJUSTSPEED_INDEX, 2 * (ADJUSTSPEED_INDEX + 1));
            setAdjustSpeed(Integer.parseInt(adJustSpeed, 16));

            String maxRunSpeed = bleData.substring( 2 * MAXRUNSPEED_INDEX, 2 * (MAXRUNSPEED_INDEX + 1));
            setMaxRunSpeed(Integer.parseInt(maxRunSpeed, 16));

            String minRunSpeed = bleData.substring( 2 * MINRUNSPEED_INDEX, 2 * (MINRUNSPEED_INDEX + 1));
            setMinRunSpeed(Integer.parseInt(minRunSpeed, 16));

            String adJustSpeed2 = bleData.substring( 2 * ADJUSTSPEED2_INDEX, 2 * (ADJUSTSPEED2_INDEX + 1));
            setAdjustSpeed2(Integer.parseInt(adJustSpeed2, 16));

            String maxRunSpeed2 = bleData.substring( 2 * MAXRUNSPEED2_INDEX, 2 * (MAXRUNSPEED2_INDEX + 1));
            setMaxRunSpeed2(Integer.parseInt(maxRunSpeed2, 16));

            String minRunSpeed2 = bleData.substring( 2 * MINRUNSPEED2_INDEX, 2 * (MINRUNSPEED2_INDEX + 1));
            setMinRunSpeed2(Integer.parseInt(minRunSpeed2, 16));

            String rxByte12 = bleData.substring( 2 * RXBYTE12_INDEX, 2 * (RXBYTE12_INDEX + 1));
            setTxByte12(Integer.parseInt(rxByte12, 16));

            String rxByte13 = bleData.substring( 2 * RXBYTE13_INDEX, 2 * (RXBYTE13_INDEX + 1));
            setTxByte13(Integer.parseInt(rxByte13, 16));

            String rxByte14 = bleData.substring( 2 * RXBYTE14_INDEX, 2 * (RXBYTE14_INDEX + 1));
            setTxByte14(Integer.parseInt(rxByte14, 16));

            String rxByte15 = bleData.substring( 2 * RXBYTE15_INDEX, 2 * (RXBYTE15_INDEX + 1));
            setTxByte15(Integer.parseInt(rxByte15, 16));

            String rxByte16 = bleData.substring( 2 * RXBYTE16_INDEX, 2 * (RXBYTE16_INDEX + 1));
            setTxByte16(Integer.parseInt(rxByte16, 16));

            String rxByte17 = bleData.substring( 2 * RXBYTE17_INDEX, 2 * (RXBYTE17_INDEX + 1));
            setTxByte17(Integer.parseInt(rxByte17, 16));

            String rxByte18 = bleData.substring( 2 * RXBYTE18_INDEX, 2 * (RXBYTE18_INDEX + 1));
            setTxByte18(Integer.parseInt(rxByte18, 16));


            String monitorData_CMDHexStr = bleData.substring( 2 * MONITOR_DATA_CMD_INDEX, 2 * (MONITOR_DATA_CMD_INDEX + 1));
            setMonitorData_CMD(monitorData_CMDHexStr);

            setParseSuccess(true);
        } else {
            setParseSuccess(false);
        }
        return this;
    }

    public String getMonitorData_CMD() {
        return monitorData_CMD;
    }

    public void setMonitorData_CMD(String monitorData_CMD) {
        this.monitorData_CMD = monitorData_CMD;
    }


    public int getTrainingState() {
        return trainingState;
    }

    public void setTrainingState(int trainingState) {
        this.trainingState = trainingState;
    }

    public int getTrainingState2() {
        return trainingState2;
    }

    public void setTrainingState2(int trainingState2) {
        this.trainingState2 = trainingState2;
    }

    public int getAdjustSpeed() {
        return AdjustSpeed;
    }

    public void setAdjustSpeed(int adjustSpeed) {
        this.AdjustSpeed = adjustSpeed;
    }

    public int getMaxRunSpeed() {
        return MaxRunSpeed;
    }

    public void setMaxRunSpeed(int maxRunSpeed) {
        this.MaxRunSpeed = maxRunSpeed;
    }

    public int getMinRunSpeed() {
        return MinRunSpeed;
    }

    public void setMinRunSpeed(int minRunSpeed) {
        this.MinRunSpeed = minRunSpeed;
    }

    public int getAdjustSpeed2() {
        return AdjustSpeed2;
    }

    public void setAdjustSpeed2(int adjustSpeed2) {
        this.AdjustSpeed2 = adjustSpeed2;
    }

    public int getMaxRunSpeed2() {
        return MaxRunSpeed2;
    }

    public void setMaxRunSpeed2(int maxRunSpeed2) {
        this.MaxRunSpeed2 = maxRunSpeed2;
    }

    public int getMinRunSpeed2() {
        return MinRunSpeed2;
    }

    public void setMinRunSpeed2(int minRunSpeed2) {
        this.MinRunSpeed2 = minRunSpeed2;
    }

    public int getTxByte12() {
        return txByte12;
    }

    public void setTxByte12(int txByte12) {
        this.txByte12 = txByte12;
    }

    public int getTxByte13() {
        return txByte13;
    }

    public void setTxByte13(int txByte13) {
        this.txByte13 = txByte13;
    }

    public int getTxByte14() {
        return txByte14;
    }

    public void setTxByte14(int txByte14) {
        this.txByte14 = txByte14;
    }

    public int getTxByte15() {
        return txByte15;
    }

    public void setTxByte15(int txByte15) {
        this.txByte15 = txByte15;
    }

    public int getTxByte16() {
        return txByte16;
    }

    public void setTxByte16(int txByte16) {
        this.txByte16 = txByte16;
    }

    public int getTxByte17() {
        return txByte17;
    }

    public void setTxByte17(int txByte17) {
        this.txByte17 = txByte17;
    }

    public int getTxByte18() {
        return txByte18;
    }

    public void setTxByte18(int txByte18) {
        this.txByte18 = txByte18;
    }
}
