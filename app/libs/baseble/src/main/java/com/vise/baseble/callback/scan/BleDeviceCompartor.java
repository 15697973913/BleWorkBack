package com.vise.baseble.callback.scan;

import com.vise.baseble.model.BluetoothLeDevice;

import java.util.Comparator;

/**
 * 信号强度由高到底排序
 */
public class BleDeviceCompartor implements Comparator<BluetoothLeDevice> {
    @Override
    public int compare(BluetoothLeDevice o1, BluetoothLeDevice o2) {
        int rssi1 = o1.getRssi();
        int rssi2 = o2.getRssi();
        //Log.d("BleDeviceCompartor", " rssi1 = " + rssi1 + " rssi2= " + rssi2);
        return rssi2 - rssi1;
    }
}
