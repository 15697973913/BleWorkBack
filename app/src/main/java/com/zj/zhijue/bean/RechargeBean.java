package com.zj.zhijue.bean;

public class RechargeBean {
    private int rechargeMoney;//充值金额
    private float toTheAccountMoney;//到账金额
    private boolean isSelected;//是否选择

    public int getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(int rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public float getToTheAccountMoney() {
        return toTheAccountMoney;
    }

    public void setToTheAccountMoney(float toTheAccountMoney) {
        this.toTheAccountMoney = toTheAccountMoney;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
