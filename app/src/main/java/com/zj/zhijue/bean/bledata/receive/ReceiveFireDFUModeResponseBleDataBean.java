package com.zj.zhijue.bean.bledata.receive;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 *   DFU 模式响应信息
 */
public class ReceiveFireDFUModeResponseBleDataBean extends BaseParseCmdBean {

    /**
     眼镜返回包
     定义	序号	内容
     校验码	RxByte1	0xa5
     校验码	RxByte2	0x51
     命令	RxByte3	0x30
     响应命令字	RxByte4	0x50
     响应结果	RxByte5	"0x00:成功；
     0x01:失败"
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

    private static final String RESPONSE_FIRE_CODE_CMD = "50";

    public static final String USER_MONITOR_CMD = USER_DATA_PREFIX + "30" + RESPONSE_FIRE_CODE_CMD;


    private final int RESPONSE_CODE_INDEX = 4;//一个字节

    private boolean jumpIntoFireDFUModeSuccess;



    @Override
    protected BaseParseCmdBean parseBleDataStr2Bean(String bleData) {
        if (!CommonUtils.isEmpty(bleData)
                && bleData.toLowerCase().startsWith(USER_MONITOR_CMD)
                && bleData.toLowerCase().endsWith(USER_DATA_SUFIX)) {
            String weekKeyFreHexStr = bleData.substring( 2 * RESPONSE_CODE_INDEX, 2 * (RESPONSE_CODE_INDEX + 1));
            setJumpIntoFireDFUModeSuccess(Integer.parseInt(weekKeyFreHexStr, 16) == 0);
            setParseSuccess(true);
        } else {
            setParseSuccess(false);
        }
        return this;
    }

    public boolean isJumpIntoFireDFUModeSuccess() {
        return jumpIntoFireDFUModeSuccess;
    }

    public void setJumpIntoFireDFUModeSuccess(boolean jumpIntoFireDFUModeSuccess) {
        this.jumpIntoFireDFUModeSuccess = jumpIntoFireDFUModeSuccess;
    }
}
