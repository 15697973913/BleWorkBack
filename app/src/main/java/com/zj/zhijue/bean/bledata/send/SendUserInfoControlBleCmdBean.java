package com.zj.zhijue.bean.bledata.send;


import com.android.common.baselibrary.blebean.BaseCmdBean;

public class SendUserInfoControlBleCmdBean extends BaseCmdBean {
    /**
     定义	序号	内容
     校验码	RxByte1	0xaa
     命令	RxByte3	0x12
     数据1	RxByte4	ControlData.ControlRun  开始	1	暂停	2	继续	3	停止	4	干预	5	电源	6
     数据2	RxByte5	ControlData.ControlByte2
     数据3	RxByte6	ControlData.ControlByte3
     数据4	RxByte7	ControlData.ControlByte4
     数据5	RxByte8	ControlData.ControlByte5
     数据6	RxByte9	ControlData.ControlByte6
     数据7	RxByte10	ControlData.ControlByte7
     数据8	RxByte11	ControlData.ControlByte8
     数据9	RxByte12	ControlData.ControlByte9
     数据10	RxByte13	ControlData.ControlByte10
     数据11	RxByte14	ControlData.ControlByte11
     数据12	RxByte15	ControlData.ControlByte12
     数据13	RxByte16	ControlData.ControlByte13
     数据14	RxByte17	ControlData.ControlByte14
     数据15	RxByte18	ControlData.ControlByte15
     数据16	RxByte19	ControlData.ControlByte16
     校验码	RxByte20	0xaa
     */
    protected String[] controlDataBleCmdDataStr = {"aa", "", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00"};


    public static final String OPERATION_CODE = "12";
    public static final String CONTROL_DATA_PREFIX = "a551" + OPERATION_CODE;

    public static final int START_OPERATION_CODE = 1;
    public static final int PASUSE_OPERATION_CODE = 2;
    public static final int CONTINUE_OPERATION_CODE = 3;
    public static final int STOP_OPERATION_CODE = 4;
    public static final int INTEVENE_OPERATION_CODE = 5;//干预操作码
    public static final int POWER_OFF_OPERATION_CODE = 6;//关闭电源

    public static final int UNKNOW_OPERATION_CODE = 0;//未知状态

    private int controlCmd;

    /**
     * 下标索引从 0 开始
     */
    public static final int USER_CONTROL_IDNEX  = 3;

    @Override
    protected String buildSourceData() {
        putData2DataArray();
        for (int i = 0; i < cmdDataArary.size(); i++) {
            int key = cmdDataArary.keyAt(i);
            String value = cmdDataArary.get(key);
            controlDataBleCmdDataStr[key] = value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String byteStr : controlDataBleCmdDataStr) {
            stringBuilder.append(byteStr);
        }

        return stringBuilder.toString();
    }

    @Override
    protected void putData2DataArray() {
        String controlCmd = decimalism2Hex(getControlCmd(), 2);
        cmdDataArary.put(USER_CONTROL_IDNEX, controlCmd);
    }

    public int getControlCmd() {
        return controlCmd;
    }

    public void setControlCmd(int controlCmd) {
        this.controlCmd = controlCmd;
    }
}
