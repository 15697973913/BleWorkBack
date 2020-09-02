package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 * APP发送眼镜运行模式
 * 0x00：预留
 * 0x01：训练模式
 * 0x0:2：矫正模式；
 * 0x03：安全模式；
 * 其他：预留
 */
public class SendRunningModeBean2 extends BaseCmdBean {
    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xaa
     * 命令	RxByte3	0x64
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
    protected String[] robotReceiveBleCmdDataStr = {"aa", "64", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "aa64";

    /**
     * 控制命令
     * 0x00：预留
     * 0x01：训练模式
     * 0x0:2：矫正模式；
     * 0x03：安全模式；
     */
    private final int SYSTEM_CONTROL_CMD_INDEX = 2;

    private int cmd;

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
         * 控制命令
         */
        String cmdHexStr = decimalism2Hex(getCmd(), 2);
        String[] cmdHexArray = reverseStringByte(cmdHexStr);

        for (int i = 0; i < cmdHexArray.length; i++) {
            cmdDataArary.put(SYSTEM_CONTROL_CMD_INDEX + i, cmdHexArray[i]);
        }

    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

}
