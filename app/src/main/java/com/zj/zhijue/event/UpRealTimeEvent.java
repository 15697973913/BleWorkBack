package com.zj.zhijue.event;

public class UpRealTimeEvent {
   private boolean isSuccess;

    public UpRealTimeEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
