package com.zj.zhijue.bean.request;

public class HttpRequestUserTrainTimeInfoBean {
    private String memberId;//	是	string	用户ID
    private String bt;//	是	date	开始时间
    private String et;//	是	date	结束时间

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }
}
