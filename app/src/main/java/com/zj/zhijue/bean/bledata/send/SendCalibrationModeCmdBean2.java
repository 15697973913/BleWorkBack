package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 * APP发送眼镜校准模式
 */
public class SendCalibrationModeCmdBean2 extends BaseCmdBean {
    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xaa
     * 命令	RxByte3	0x46
     */
    protected String[] robotReceiveBleCmdDataStr = {"aa", "46", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "aa46";

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
