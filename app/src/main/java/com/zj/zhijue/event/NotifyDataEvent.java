package com.zj.zhijue.event;

import com.vise.baseble.core.BluetoothGattChannel;
import com.vise.baseble.model.BluetoothLeDevice;

public class NotifyDataEvent {
    private BluetoothGattChannel bluetoothGattChannel;
    private BluetoothLeDevice bluetoothLeDevice;
    private byte[] data;
    private int deviceType; // 0 表示蓝牙眼镜， 1 表示台灯

    public NotifyDataEvent() {
    }

    public NotifyDataEvent(int deviceType) {
        this.deviceType = deviceType;
    }

    public byte[] getData() {
        return this.data;
    }

    public NotifyDataEvent setData(byte[] bArr) {
        this.data = bArr;
        return this;
    }

    public BluetoothLeDevice getBluetoothLeDevice() {
        return this.bluetoothLeDevice;
    }

    public NotifyDataEvent setBluetoothLeDevice(BluetoothLeDevice bluetoothLeDevice) {
        this.bluetoothLeDevice = bluetoothLeDevice;
        return this;
    }

    public BluetoothGattChannel getBluetoothGattChannel() {
        return this.bluetoothGattChannel;
    }

    public NotifyDataEvent setBluetoothGattChannel(BluetoothGattChannel bluetoothGattChannel) {
        this.bluetoothGattChannel = bluetoothGattChannel;
        return this;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
}
