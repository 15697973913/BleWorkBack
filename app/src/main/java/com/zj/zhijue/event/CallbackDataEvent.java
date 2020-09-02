package com.zj.zhijue.event;

import com.vise.baseble.core.BluetoothGattChannel;
import com.vise.baseble.model.BluetoothLeDevice;

public class CallbackDataEvent {
    private BluetoothGattChannel bluetoothGattChannel;
    private BluetoothLeDevice bluetoothLeDevice;
    private byte[] data;
    private boolean isSuccess;
    private int deviceType; // 0 表示蓝牙眼镜， 1 表示台灯

    public CallbackDataEvent() {
    }

    public CallbackDataEvent(int deviceType) {
        this.deviceType = deviceType;
    }

    public CallbackDataEvent setSuccess(boolean z) {
        this.isSuccess = z;
        return this;
    }

    public byte[] getData() {
        return this.data;
    }

    public CallbackDataEvent setData(byte[] bArr) {
        this.data = bArr;
        return this;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public BluetoothLeDevice getBluetoothLeDevice() {
        return this.bluetoothLeDevice;
    }

    public CallbackDataEvent setBluetoothLeDevice(BluetoothLeDevice bluetoothLeDevice) {
        this.bluetoothLeDevice = bluetoothLeDevice;
        return this;
    }

    public BluetoothGattChannel getBluetoothGattChannel() {
        return this.bluetoothGattChannel;
    }

    public CallbackDataEvent setBluetoothGattChannel(BluetoothGattChannel bluetoothGattChannel) {
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
