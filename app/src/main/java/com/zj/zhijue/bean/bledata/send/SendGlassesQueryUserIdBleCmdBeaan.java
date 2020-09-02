package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 *  发送指令查询蓝牙眼镜当前用户的 ID
 */
public class SendGlassesQueryUserIdBleCmdBeaan extends BaseCmdBean {
    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xaa
     * 命令	    RxByte3	0x23
     * 数据1	RxByte4	空
     * 数据2	RxByte5	空
     * 数据3	RxByte6	空
     * 数据4	RxByte7	空
     * 数据5	RxByte8	空
     * 数据6	RxByte9	空
     * 数据7	RxByte10	空
     * 数据8	RxByte11	空
     * 数据9	RxByte12	空
     * 数据10	RxByte13	空
     * 数据11	RxByte14	空
     * 数据12	RxByte15	空
     * 数据13	RxByte16	空
     * 数据14	RxByte17	空
     * 数据15	RxByte18	空
     * 数据16	RxByte19	MonitorData.CMD
     * 校验码	RxByte20	0xaa
     *
     */


    protected String[] sendGlassesRunParam1BleDataArray = {"a5", "51", "23", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "aa"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "a55123";


    /**
     * 下标从0开始
     */

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

       /* String minMinusHexStr = decimalism2Hex(getMinMinusInterval(), 4);
        String[] minMinusHexArray = reverseStringByte(minMinusHexStr);
        for (int i = 0; i < minMinusHexArray.length; i++) {
            cmdDataArary.put(MIN_MINUS_INTERVAL_INDEX + i, minMinusHexArray[i]);
        }*/

    }

}
