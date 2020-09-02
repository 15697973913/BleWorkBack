package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 * APP接收到眼镜命令后，返回接收结果
 */
public class SendSuccessStatusCmdBean2 extends BaseCmdBean {
    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xaa
     * 命令	RxByte3	0x46
     */
    protected String[] robotReceiveBleCmdDataStr = {"aa", "40", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "aa40";

    /**
     * 响应命令字 (发送的时候命令 如 校准模式 46)
     */
    private final int SYSTEM_CMD_TYPE_INDEX = 2;
    /**
     * 响应命令字
     * 0x00:成功；
     * 0x01:失败
     */
    private final int SYSTEM_SUCCESS_STATUS_INDEX = 3;

    //蓝牙返回数据
    private String bleBackData;

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
    }

    public String getBleBackData() {
        return bleBackData;
    }

    public void setBleBackData(String bleBackData) {
        this.bleBackData = bleBackData;
    }
}
