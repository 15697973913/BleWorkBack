package com.zj.zhijue.event;

public class DfuModeEvent2 {
    private boolean dfuModeSuccess;

    public DfuModeEvent2(boolean dfuModeSuccess) {
        this.dfuModeSuccess = dfuModeSuccess;
    }

    public boolean isDfuModeSuccess() {
        return dfuModeSuccess;
    }

    public void setDfuModeSuccess(boolean dfuModeSuccess) {
        this.dfuModeSuccess = dfuModeSuccess;
    }
}
