package com.zj.zhijue.bean.event;

import com.vise.baseble.model.BluetoothLeDevice;

public class DialogBleConnectEvent {
    private BluetoothLeDevice bluetoothLeDevice;

    public DialogBleConnectEvent(BluetoothLeDevice bluetoothLeDevice) {
        this.bluetoothLeDevice = bluetoothLeDevice;
    }

    public BluetoothLeDevice getBluetoothLeDevice() {
        return bluetoothLeDevice;
    }

    public void setBluetoothLeDevice(BluetoothLeDevice bluetoothLeDevice) {
        this.bluetoothLeDevice = bluetoothLeDevice;
    }
}
