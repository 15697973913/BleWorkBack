package com.zj.zhijue.bean.bledata.receive;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 * 接收眼镜发送的 用户ID
 */
public class ReceiveGlassesUserIdBleDataBean extends BaseParseCmdBean {
    /**
     * 定义	序号	内容
     * 校验码	TxByte1	0xa5
     * 校验码	TxByte2	0x51
     * 命令	TxByte3	0x24
     * 数据1	TxByte4	CurrentUser.Code1
     * 数据2	TxByte5	CurrentUser.Code2
     * 数据3	TxByte6	CurrentUser.Code3
     * 数据4	TxByte7	CurrentUser.Code4
     * 数据5	TxByte8	CurrentUser.Code5
     * 数据6	TxByte9	空
     * 数据7	TxByte10	空
     * 数据8	TxByte11	空
     * 数据9	TxByte12	空
     * 数据10	TxByte13	空
     * 数据11	TxByte14	空
     * 数据12	TxByte15	空
     * 数据13	TxByte16	空
     * 数据14	TxByte17	空
     * 数据15	TxByte18	空
     * 数据16	TxByte19	MonitorData.CMD
     * 校验码	TxByte20	0xaa
     */
    public static final String USER_MONITOR_CMD = USER_DATA_PREFIX + "24";

    private String operationCmd;
    private final int USER_ID_INDEX = 3;

    private final int OPERATION_CMD_INDEX = 18;//操作码
    private long userId;

    @Override
    protected BaseParseCmdBean parseBleDataStr2Bean(String bleData) {
        if (!CommonUtils.isEmpty(bleData)
                && bleData.toLowerCase().startsWith(USER_MONITOR_CMD)
                && bleData.toLowerCase().endsWith(USER_DATA_SUFIX)) {

            String currentUserCodeHexStr = bleData.substring( 2 * USER_ID_INDEX, 2 * (USER_ID_INDEX + 5));

            String[] currentUserCodeArray = reverseStringByte(currentUserCodeHexStr);
            StringBuilder currentUserCodeArrayBuilder = new StringBuilder();
            for (String byteArray : currentUserCodeArray) {
                currentUserCodeArrayBuilder.append(byteArray);
            }

            setUserId(Long.parseLong(currentUserCodeArrayBuilder.toString(), 16));

            String operationCmdData = bleData.substring(2 * OPERATION_CMD_INDEX, 2 * (OPERATION_CMD_INDEX + 1));

            setOperationCmd(operationCmdData);
            setParseSuccess(true);
        } else {
            setParseSuccess(false);
        }
        return this;
    }

    public String getOperationCmd() {
        return operationCmd;
    }

    public void setOperationCmd(String operationCmd) {
        this.operationCmd = operationCmd;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "用户ID{" + "\n" +
                ", userId=" + userId + "\n" +
                " operationCmd='" + operationCmd + '\'' + "\n" +
                '}';
    }
}
