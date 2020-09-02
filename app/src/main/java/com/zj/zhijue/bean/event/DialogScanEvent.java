package com.zj.zhijue.bean.event;

public class DialogScanEvent {
    private boolean startScan;

    public DialogScanEvent(boolean startScan) {
        this.startScan = startScan;
    }

    public boolean isStartScan() {
        return startScan;
    }

    public void setStartScan(boolean startScan) {
        this.startScan = startScan;
    }
}
