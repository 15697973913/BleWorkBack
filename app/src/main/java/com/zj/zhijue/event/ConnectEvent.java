package com.zj.zhijue.event;

import com.vise.baseble.core.DeviceMirror;

public class ConnectEvent {
    private DeviceMirror deviceMirror;
    private boolean isDisconnected;
    private boolean isSuccess;
    private int deviceType; // 0 表示蓝牙眼镜， 1 表示台灯

    public ConnectEvent() {
    }

    public ConnectEvent(int deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public ConnectEvent setSuccess(boolean z) {
        this.isSuccess = z;
        return this;
    }

    public boolean isDisconnected() {
        return this.isDisconnected;
    }

    public ConnectEvent setDisconnected(boolean z) {
        this.isDisconnected = z;
        return this;
    }

    public DeviceMirror getDeviceMirror() {
        return this.deviceMirror;
    }

    public ConnectEvent setDeviceMirror(DeviceMirror deviceMirror) {
        this.deviceMirror = deviceMirror;
        return this;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
}
