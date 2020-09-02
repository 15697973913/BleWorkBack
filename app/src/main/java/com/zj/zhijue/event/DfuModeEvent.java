package com.zj.zhijue.event;

public class DfuModeEvent {
    private boolean dfuModeSuccess;

    public DfuModeEvent(boolean dfuModeSuccess) {
        this.dfuModeSuccess = dfuModeSuccess;
    }

    public boolean isDfuModeSuccess() {
        return dfuModeSuccess;
    }

    public void setDfuModeSuccess(boolean dfuModeSuccess) {
        this.dfuModeSuccess = dfuModeSuccess;
    }
}
