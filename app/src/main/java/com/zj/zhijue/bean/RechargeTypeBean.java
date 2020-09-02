package com.zj.zhijue.bean;

public class RechargeTypeBean {
    private int rechargeTypeResourceId;//充值资源图片方式
    private boolean isSelected;//是否选择

    public int getRechargeTypeResourceId() {
        return rechargeTypeResourceId;
    }

    public void setRechargeTypeResourceId(int rechargeTypeResourceId) {
        this.rechargeTypeResourceId = rechargeTypeResourceId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
