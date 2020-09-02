package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 *  下发眼镜运行参数1
 */
public class SendGlassesRunParam1BleCmdBeaan extends BaseCmdBean {

    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xa5
     * 校验码	RxByte2	0x51
     * 命令	RxByte3	0x16
     * 数据1	RxByte4	MinMinusInterval%256
     * 数据2	RxByte5	MinMinusInterval/256
     * 数据3	RxByte6	MinPlusInterval%256
     * 数据4	RxByte7	MinPlusInterval/256
     * 数据5	RxByte8	CommonNumber
     * 数据6	RxByte9	InterveneAccMinute%256
     * 数据7	RxByte10	InterveneAccMinute/256
     * 数据8	RxByte11	WeekKeyFre
     * 数据9	RxByte12	WeekAccMinute%256
     * 数据10	RxByte13	WeekAccMinute/256
     * 数据11	RxByte14	BackWeekAccMinute[0]%256
     * 数据12	RxByte15	BackWeekAccMinute[0]/256
     * 数据13	RxByte16	BackWeekAccMinute[1]%256
     * 数据14	RxByte17	BackWeekAccMinute[1]/256
     * 数据15	RxByte18	BackWeekAccMinute[2]%256
     * 数据16	RxByte19	0
     * 校验码	RxByte20	0xaa
     */

    protected String[] sendGlassesRunParam1BleDataArray = {"a5", "51", "16", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "aa"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "a55116";


    /**
     * 下标从0开始
     */
    private final int MIN_MINUS_INTERVAL_INDEX = 3;//两个字节

    private final int MIN_PLUS_INTERVAL_INDEX = 5;//两个字节

    private final int COMMON_NUMBER_INDEX = 7;//

    private final int INTERVENE_ACC_MINUTE_INDEX = 8;//两个字节

    private final int WEEK_KEY_FRE_INDEX = 10;//一个字节

    private final int WEEK_ACC_MINUTE_INDEX = 11;//两个字节

    private final int BACK_WEEK_ACC_MINUTE_0_INDEX = 13;//两个字节

    private final int BACK_WEEK_ACC_MINUTE_1_INDEX = 15;//两个字节

    private final int BACK_WEEK_ACC_MINUTE_2_INDEX = 17;//一个字节

    private int minMinusInterval;
    private int minPlusInterval;
    private int commonNumber;
    private int interveneAccMinute;
    private int weekKeyFre;
    private int weekAccMinute;
    private int backWeekAccMinute0;
    private int backWeekAccMinute1;
    private int backWeekAccMinute2;

    @Override
    protected String buildSourceData() {
        putData2DataArray();
        for (int i = 0; i < cmdDataArary.size(); i++) {
            int key = cmdDataArary.keyAt(i);
            String value = cmdDataArary.get(key);
            sendGlassesRunParam1BleDataArray[key] = value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String byteStr : sendGlassesRunParam1BleDataArray) {
            stringBuilder.append(byteStr);
        }

        return stringBuilder.toString();
    }

    /**
     *  向 cmdDataArary 中 put 数据
     */
    @Override
    protected void putData2DataArray() {

        String minMinusHexStr = decimalism2Hex(getMinMinusInterval(), 4);
        String[] minMinusHexArray = reverseStringByte(minMinusHexStr);
        for (int i = 0; i < minMinusHexArray.length; i++) {
            cmdDataArary.put(MIN_MINUS_INTERVAL_INDEX + i, minMinusHexArray[i]);
        }


        String minPlusIntervalHexStr = decimalism2Hex(getMinPlusInterval(), 4);
        String[] minPlusIntervalHexArray = reverseStringByte(minPlusIntervalHexStr);
        for (int i = 0; i < minPlusIntervalHexArray.length; i++) {
            cmdDataArary.put(MIN_PLUS_INTERVAL_INDEX + i, minPlusIntervalHexArray[i]);
        }

        String commonNumber = decimalism2Hex(getCommonNumber(), 2);
        cmdDataArary.put(COMMON_NUMBER_INDEX, commonNumber);

        String interveneAccMinuteHexStr = decimalism2Hex(getInterveneAccMinute(), 4);
        String[] interveneAccMinuteHexArray = reverseStringByte(interveneAccMinuteHexStr);
        for (int i = 0; i < interveneAccMinuteHexArray.length; i++) {
            cmdDataArary.put(INTERVENE_ACC_MINUTE_INDEX + i, interveneAccMinuteHexArray[i]);
        }

        String weekKeyFre = decimalism2Hex(getWeekKeyFre(), 2);
        cmdDataArary.put(WEEK_KEY_FRE_INDEX, weekKeyFre);

        String weekAccMinuteHexStr = decimalism2Hex(getWeekAccMinute(), 4);
        String[] weekAccMinuteHexArray = reverseStringByte(weekAccMinuteHexStr);
        for (int i = 0; i < weekAccMinuteHexArray.length; i++) {
            cmdDataArary.put(WEEK_ACC_MINUTE_INDEX + i, weekAccMinuteHexArray[i]);
        }

        String backWeekAccMinute0HexStr = decimalism2Hex(getBackWeekAccMinute0(), 4);
        String[] backWeekAccMinute0HexArray = reverseStringByte(backWeekAccMinute0HexStr);
        for (int i = 0; i < backWeekAccMinute0HexArray.length; i++) {
            cmdDataArary.put(BACK_WEEK_ACC_MINUTE_0_INDEX + i, backWeekAccMinute0HexArray[i]);
        }

        String backWeekAccMinute1HexStr = decimalism2Hex(getBackWeekAccMinute1(), 4);
        String[] backWeekAccMinute1HexArray = reverseStringByte(backWeekAccMinute1HexStr);
        for (int i = 0; i < backWeekAccMinute1HexArray.length; i++) {
            cmdDataArary.put(BACK_WEEK_ACC_MINUTE_1_INDEX + i, backWeekAccMinute1HexArray[i]);
        }

        String backWeekAccMinute2HexStr = decimalism2Hex(getBackWeekAccMinute2(), 2);
        cmdDataArary.put(BACK_WEEK_ACC_MINUTE_2_INDEX, backWeekAccMinute2HexStr);

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
}
