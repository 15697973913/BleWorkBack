package com.android.common.baselibrary.blebean;

/**
 * 解析蓝牙设备发送给 APP 的数据，并构造指定的 Bean 对象
 */
public abstract class BaseParseCmdBean {


    public static final String USER_DATA_PREFIX = "aa";

    public static final String USER_DATA_SUFIX = "aa";

    protected boolean parseSuccess = false;

    public static String bytesToString(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return "";
        }

        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];

            sb.append(hexChars[i * 2]);
            sb.append(hexChars[i * 2 + 1]);
            //sb.append(' ');
        }
        return sb.toString();
    }

    /**
     * 每个字节，空格分隔
     * @param bytes
     * @return
     */
    public static String bytesToStringWithSpace(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return "";
        }

        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];

            sb.append(hexChars[i * 2]);
            sb.append(hexChars[i * 2 + 1]);
            sb.append(' ');
        }
        return sb.toString();
    }

    /**
     * 提供给外部调用的方法
     * @param bleData
     * @return
     */
    public BaseParseCmdBean parseBleDataByteArray2Bean(byte[] bleData) {

        return parseBleDataStr2Bean(bytesToString(bleData));
    }

    /**
     * 子类需要实现具体的解析字段的逻辑，
     * @param bleData
     * @return
     */
    protected abstract BaseParseCmdBean parseBleDataStr2Bean(String bleData);

    protected static String[] reverseStringByte(String sourceByteStringArray) {
        return BaseCmdBean.reverseStringByte(sourceByteStringArray);
    }

    public boolean isParseSuccess() {
        return parseSuccess;
    }

    public void setParseSuccess(boolean parseSuccess) {
        this.parseSuccess = parseSuccess;
    }
}
