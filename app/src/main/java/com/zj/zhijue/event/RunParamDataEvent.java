package com.zj.zhijue.event;


public class RunParamDataEvent {

    private byte[] data;

    private String beanJson;

    public RunParamDataEvent() {
    }

    public byte[] getData() {
        return this.data;
    }

    public RunParamDataEvent setData(byte[] bArr) {
        this.data = bArr;
        return this;
    }

    public String getBeanJson() {
        return beanJson;
    }

    public void setBeanJson(String beanJson) {
        this.beanJson = beanJson;
    }
}
