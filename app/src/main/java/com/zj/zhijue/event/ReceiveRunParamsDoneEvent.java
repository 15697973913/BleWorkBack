package com.zj.zhijue.event;

public class ReceiveRunParamsDoneEvent {
    private boolean receviewRunParamsDone;//运行参数接收完毕

    public ReceiveRunParamsDoneEvent(boolean receviewRunParamsDone) {
        this.receviewRunParamsDone = receviewRunParamsDone;
    }

    public boolean isReceviewRunParamsDone() {
        return receviewRunParamsDone;
    }

    public void setReceviewRunParamsDone(boolean receviewRunParamsDone) {
        this.receviewRunParamsDone = receviewRunParamsDone;
    }
}
