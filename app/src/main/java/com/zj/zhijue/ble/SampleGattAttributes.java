package com.zj.zhijue.ble;




import com.vise.baseble.model.resolver.GattAttributeResolver;

import java.util.HashMap;

public class SampleGattAttributes {
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    private static HashMap attributes = new HashMap();

    static {
        attributes.put(GattAttributeResolver.HEART_RATE, "Heart Rate Service");
        attributes.put(GattAttributeResolver.DEVICE_INFORMATION, "Device Information Service");
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put(GattAttributeResolver.MANUFACTURER_NAME_STRING, "Manufacturer Name String");
    }

    public static String lookup(String str, String str2) {
        str = (String) attributes.get(str);
        return str == null ? str2 : str;
    }
}
