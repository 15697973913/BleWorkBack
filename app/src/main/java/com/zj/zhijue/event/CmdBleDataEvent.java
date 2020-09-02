package com.zj.zhijue.event;

public class CmdBleDataEvent {
    private byte[] bleCmdData;

    public CmdBleDataEvent(byte[] bleCmdData) {
        this.bleCmdData = bleCmdData;
    }

    public byte[] getBleCmdData() {
        return bleCmdData;
    }

    public void setBleCmdData(byte[] bleCmdData) {
        this.bleCmdData = bleCmdData;
    }
}
