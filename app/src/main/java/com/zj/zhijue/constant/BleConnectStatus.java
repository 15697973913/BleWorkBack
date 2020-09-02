package com.zj.zhijue.constant;

import android.bluetooth.BluetoothGatt;

public enum BleConnectStatus {
    Connected(BluetoothGatt.STATE_CONNECTED),
    Connecting(BluetoothGatt.STATE_CONNECTING),
    Disconnected(BluetoothGatt.STATE_DISCONNECTED),
    ConnectedFail(BluetoothGatt.STATE_CONNECTED + BluetoothGatt.STATE_CONNECTING + BluetoothGatt.STATE_DISCONNECTED),
    NoDeviceConnected(-(BluetoothGatt.STATE_CONNECTED + BluetoothGatt.STATE_CONNECTING + BluetoothGatt.STATE_DISCONNECTED)),
    SearchListView(BluetoothGatt.STATE_CONNECTED + BluetoothGatt.STATE_CONNECTING + BluetoothGatt.STATE_DISCONNECTED + 1);

    private int status;

    BleConnectStatus(int stateConnected) {
        this.status = stateConnected;
    }
}
