package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 *  下发眼镜运行参数4
 */
public class SendGlassesRunParam5BleCmdBeaan extends BaseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xa5
     * 校验码	RxByte2	0x51
     * 命令	RxByte3	0x1a
     * 数据1	MinRunSpeed2	0
     * 数据2	txByte5	0
     * 数据3	txByte6	0
     * 数据4	txByte7	0
     * 数据5	txByte8	0
     * 数据6	txByte9	0
     * 数据7	txByte10	0
     * 数据8	txByte11	0
     * 数据9	txByte12	0
     * 数据10	txByte13	0
     * 数据11	txByte14	0
     * 数据12	txByte15	0
     * 数据13	txByte16	0
     * 数据14	txByte17	0
     * 数据15	txByte18	0
     * 数据16	txByte19	0
     * 校验码	RxByte20	0xaa
     */

    protected String[] sendGlassesRunParam4BleDataArray = {"a5", "51", "1a", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "aa"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "a5511a";


    /**
     * 下标从0开始
     */
    private final int MINRUNSPEED2_INDEX = 3;
    private final int RXBYTE5_INDEX = 4;
    private final int RXBYTE6_INDEX = 5;
    private final int RXBYTE7_INDEX = 6;
    private final int RXBYTE8_INDEX = 7;
    private final int RXBYTE9_INDEX = 8;
    private final int RXBYTE10_INDEX = 9;
    private final int RXBYTE11_INDEX = 10;
    private final int RXBYTE12_INDEX = 11;
    private final int RXBYTE13_INDEX = 12;
    private final int RXBYTE14_INDEX = 13;
    private final int RXBYTE15_INDEX = 14;
    private final int RXBYTE16_INDEX = 15;
    private final int RXBYTE17_INDEX = 16;
    private final int RXBYTE18_INDEX = 17;
    private final int RXBYTE19_INDEX = 18;


    private int MinRunSpeed2;
    private int txByte5;
    private int txByte6;
    private int txByte7;
    private int txByte8;
    private int txByte9;
    private int txByte10;
    private int txByte11;
    private int txByte12;
    private int txByte13;
    private int txByte14;
    private int txByte15;
    private int txByte16;
    private int txByte17;
    private int txByte18;
    private int txByte19;


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
        String rxMinRunSpeed2Hex = decimalism2Hex(getMinRunSpeed2(), 2);
        cmdDataArary.put(MINRUNSPEED2_INDEX, rxMinRunSpeed2Hex);

        String rxByte5Hex = decimalism2Hex(getTxByte5(), 2);
        cmdDataArary.put(RXBYTE5_INDEX, rxByte5Hex);

        String rxByte6Hex = decimalism2Hex(getTxByte6(), 2);
        cmdDataArary.put(RXBYTE6_INDEX, rxByte6Hex);

        String rxByte7Hex = decimalism2Hex(getTxByte7(), 2);
        cmdDataArary.put(RXBYTE7_INDEX, rxByte7Hex);

        String rxByte8Hex = decimalism2Hex(getTxByte8(), 2);
        cmdDataArary.put(RXBYTE8_INDEX, rxByte8Hex);

        String rxByte9Hex = decimalism2Hex(getTxByte9(), 2);
        cmdDataArary.put(RXBYTE9_INDEX, rxByte9Hex);

        String rxByte10Hex = decimalism2Hex(getTxByte10(), 2);
        cmdDataArary.put(RXBYTE10_INDEX, rxByte10Hex);

        String rxByte11Hex = decimalism2Hex(getTxByte11(), 2);
        cmdDataArary.put(RXBYTE11_INDEX, rxByte11Hex);

        String rxByte12Hex = decimalism2Hex(getTxByte12(), 2);
        cmdDataArary.put(RXBYTE12_INDEX, rxByte12Hex);

        String rxByte13Hex = decimalism2Hex(getTxByte13(), 2);
        cmdDataArary.put(RXBYTE13_INDEX, rxByte13Hex);

        String rxByte14Hex = decimalism2Hex(getTxByte14(), 2);
        cmdDataArary.put(RXBYTE14_INDEX, rxByte14Hex);

        String rxByte15Hex = decimalism2Hex(getTxByte15(), 2);
        cmdDataArary.put(RXBYTE15_INDEX, rxByte15Hex);

        String rxByte16Hex = decimalism2Hex(getTxByte16(), 2);
        cmdDataArary.put(RXBYTE16_INDEX, rxByte16Hex);

        String rxByte17Hex = decimalism2Hex(getTxByte17(), 2);
        cmdDataArary.put(RXBYTE17_INDEX, rxByte17Hex);

        String rxByte18Hex = decimalism2Hex(getTxByte18(), 2);
        cmdDataArary.put(RXBYTE18_INDEX, rxByte18Hex);

        String rxByte19Hex = decimalism2Hex(getTxByte19(), 2);
        cmdDataArary.put(RXBYTE19_INDEX, rxByte19Hex);

    }


    public int getMinRunSpeed2() {
        return MinRunSpeed2;
    }

    public void setMinRunSpeed2(int minRunSpeed2) {
        this.MinRunSpeed2 = minRunSpeed2;
    }

    public int getTxByte5() {
        return txByte5;
    }

    public void setTxByte5(int txByte5) {
        this.txByte5 = txByte5;
    }

    public int getTxByte6() {
        return txByte6;
    }

    public void setTxByte6(int txByte6) {
        this.txByte6 = txByte6;
    }

    public int getTxByte7() {
        return txByte7;
    }

    public void setTxByte7(int txByte7) {
        this.txByte7 = txByte7;
    }

    public int getTxByte8() {
        return txByte8;
    }

    public void setTxByte8(int txByte8) {
        this.txByte8 = txByte8;
    }

    public int getTxByte9() {
        return txByte9;
    }

    public void setTxByte9(int txByte9) {
        this.txByte9 = txByte9;
    }

    public int getTxByte10() {
        return txByte10;
    }

    public void setTxByte10(int txByte10) {
        this.txByte10 = txByte10;
    }

    public int getTxByte11() {
        return txByte11;
    }

    public void setTxByte11(int txByte11) {
        this.txByte11 = txByte11;
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

    public int getTxByte19() {
        return txByte19;
    }

    public void setTxByte19(int txByte19) {
        this.txByte19 = txByte19;
    }
}
