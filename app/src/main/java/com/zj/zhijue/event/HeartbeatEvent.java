package com.zj.zhijue.event;

/**
 * 用于心跳包的EventBus
 */
public class HeartbeatEvent {
    public boolean isSuccess;

    public HeartbeatEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
