package com.zj.zhijue.event;

public class DeviceElectricityEvent {
   private int electricity;

    public DeviceElectricityEvent(int electricity) {
        this.electricity = electricity;
    }

    public int getElectricity() {
        return electricity;
    }

    public void setElectricity(int electricity) {
        this.electricity = electricity;
    }
}
