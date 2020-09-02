package com.zj.zhijue.event;

import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;

public class ScanEvent {
    private BluetoothLeDevice bluetoothLeDevice;
    private BluetoothLeDeviceStore bluetoothLeDeviceStore;
    private boolean isScanFinish;
    private boolean isScanTimeout;
    private boolean isSuccess;
    private int deviceType; // 0 表示蓝牙眼镜， 1 表示台灯

    public ScanEvent() {
    }

    public ScanEvent(int deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isScanTimeout() {
        return this.isScanTimeout;
    }

    public ScanEvent setScanTimeout(boolean z) {
        this.isScanTimeout = z;
        return this;
    }

    public boolean isScanFinish() {
        return this.isScanFinish;
    }

    public ScanEvent setScanFinish(boolean z) {
        this.isScanFinish = z;
        return this;
    }

    public BluetoothLeDeviceStore getBluetoothLeDeviceStore() {
        return this.bluetoothLeDeviceStore;
    }

    public ScanEvent setBluetoothLeDeviceStore(BluetoothLeDeviceStore bluetoothLeDeviceStore) {
        this.bluetoothLeDeviceStore = bluetoothLeDeviceStore;
        return this;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public ScanEvent setSuccess(boolean z) {
        this.isSuccess = z;
        return this;
    }

    public BluetoothLeDevice getBluetoothLeDevice() {
        return this.bluetoothLeDevice;
    }

    public ScanEvent setBluetoothLeDevice(BluetoothLeDevice bluetoothLeDevice) {
        this.bluetoothLeDevice = bluetoothLeDevice;
        return this;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
}
