package com.android.common.baselibrary.blebean;

import android.util.SparseArray;

import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.BleHexUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;

/**
 * 发送给蓝牙设备的数据，通过 Bean 对象 转换成 Hex 数据
 */
public abstract class BaseCmdBean {


    protected SparseArray<String> cmdDataArary = new SparseArray();


    /**
     * 提供给外部调用的方法,构造发送给蓝牙设备 byte[]
     *
     * @return
     */
    public final byte[] buildCmdByteArray() {
        String sourcdData = buildSourceData();

        //暂时不知道为什么超出20字节，临时屏蔽
        sourcdData = sourcdData.length() > 40 ? sourcdData.substring(0, 40) : sourcdData;

        MLog.e(sourcdData.length() + " buildCmdByteArray ============== " + sourcdData + " -- ");

        //ToastUtil.showLong(sourcdData);
        //SdLogUtil.writeCommonLog("sourcdData = " + sourcdData);
        byte[] hexData = BleHexUtil.getRealSendData(sourcdData);
        return hexData;
    }

    /**
     * 按照协议构造要发送给蓝牙设备的字符串数据
     *
     * @return
     */
    protected abstract String buildSourceData();

    /**
     * 将 用户 要发送的 指令 放入到 cmdDataArary 集合中
     */
    protected abstract void putData2DataArray();

    /**
     * 十进制转换成指定长度的 16进制
     *
     * @param decimalismStr
     * @param length
     * @return
     */
    public final static String decimalism2Hex(String decimalismStr, int length) {
        if (CommonUtils.isEmpty(decimalismStr)) {
            decimalismStr = "0";
        }
        Integer deci = Integer.parseInt(decimalismStr);
        String hexStr = intToHex(deci & getMathPowInt(length));

        while (hexStr.length() < length) {
            hexStr = "0" + hexStr;
        }

        return hexStr;
    }

    /**
     * 十进制转换成指定长度的 16进制
     *
     * @param decimalism
     * @param length
     * @return
     */
    public final static String decimalism2Hex(int decimalism, int length) {
        String hexStr = intToHex(decimalism & getMathPowInt(length));

        while (hexStr.length() < length) {
            hexStr = "0" + hexStr;
        }

        return hexStr;
    }

    public final static String decimalismLong2Hex(String decimalism, int length) {
        String hexStr = Long.toHexString(Long.parseLong(decimalism) & getMathPowLong(length));
        while (hexStr.length() < length) {
            hexStr = "0" + hexStr;
        }
        return hexStr;
    }

    protected static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        while (n != 0) {
            s = s.append(b[n % 16]);
            n = n / 16;
        }
        a = s.reverse().toString();
        return a;
    }

    /**
     * 调整 字节字符串 的高低位的顺序
     * 例如传入 0123  返回 23 01
     *
     * @return
     */
    public static String[] reverseStringByte(String sourceByteStringArray) {
        if (sourceByteStringArray.length() % 2 == 0) {
            return new String[]{};
        }

        int count = sourceByteStringArray.length() / 2;
        String[] dataArray = new String[count];
        for (int i = 0; i < count; i++) {
            dataArray[i] = sourceByteStringArray.substring(2 * (count - i - 1), 2 * (count - i));
        }
        return dataArray;
    }

    public String getSourceData() {
        return buildSourceData();
    }

    public static int getMathPowInt(int length) {
        return (int) (Math.pow(16, length) - 1);
    }

    public static long getMathPowLong(int length) {
        return (long) (Math.pow(16, length) - 1);
    }

}
