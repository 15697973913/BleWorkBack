package com.zj.zhijue.event;

public class UpRealLocalTimeEvent {
   private int time;

    public UpRealLocalTimeEvent(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
