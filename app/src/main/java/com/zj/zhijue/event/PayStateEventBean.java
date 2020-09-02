package com.zj.zhijue.event;

public class PayStateEventBean {
    public boolean payState;
    public int payType;//0微信 ，1支付宝

    public PayStateEventBean(boolean payState, int payType) {
        this.payState = payState;
        this.payType = payType;
    }
}
