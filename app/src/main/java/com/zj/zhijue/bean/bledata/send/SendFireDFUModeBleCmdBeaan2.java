package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 *  进入烧录固件升级 DFU 模式
 */
public class SendFireDFUModeBleCmdBeaan2 extends BaseCmdBean {

    /**
     APP发送进入DFU模式
     定义	序号	内容
     校验码	RxByte1	0xaa
     命令	RxByte3	0x50
     数据1	RxByte4	空
     数据2	RxByte5	空
     数据3	RxByte6	空
     数据4	RxByte7	空
     数据5	RxByte8	空
     数据6	RxByte9	空
     数据7	RxByte10	空
     数据8	RxByte11	空
     数据9	RxByte12	空
     数据10	RxByte13	空
     数据11	RxByte14	空
     数据12	RxByte15	空
     数据13	RxByte16	空
     数据14	RxByte17	空
     数据15	RxByte18	空
     数据16	RxByte19	空
     校验码	RxByte20	0xaa
     */

    protected String[] sendGlassesIntoFireCodeModeBleDataArray = {"aa", "50", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "aa"};

    public static final String GLASSES_INTO_DFU__MODE = "aa50";



    @Override
    protected String buildSourceData() {
        putData2DataArray();
        for (int i = 0; i < cmdDataArary.size(); i++) {
            int key = cmdDataArary.keyAt(i);
            String value = cmdDataArary.get(key);
            sendGlassesIntoFireCodeModeBleDataArray[key] = value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String byteStr : sendGlassesIntoFireCodeModeBleDataArray) {
            stringBuilder.append(byteStr);
        }

        return stringBuilder.toString();
    }

    /**
     *  向 cmdDataArary 中 put 数据
     */
    @Override
    protected void putData2DataArray() {

    }

}
