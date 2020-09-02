package com.zj.zhijue.event;


/***
 * 同步蓝牙数据事件 (每次连接上蓝牙眼镜)
 */
public class SynBleDataEvent {
    private boolean startSyn;//开始同步数据
    private boolean getIdAndStatusSuccess;//是否已经成功获取用户ID 和 状态
    private boolean theSameUser;//是否同一用户（眼镜中保存的用户ID 与当前使用 APP 连接眼镜的用户是否一致）
    private boolean isRunning;
    private long userId;
    private int glassesStatus;
    private boolean synSuccess;//是否同步数据成功

    public SynBleDataEvent(boolean startSyn, boolean getIdAndStatusSuccess, boolean theSameUser, long userId, int glassesStatus) {
        this.startSyn = startSyn;
        this.getIdAndStatusSuccess = getIdAndStatusSuccess;
        this.theSameUser = theSameUser;
        this.userId = userId;
        this.glassesStatus = glassesStatus;
    }

    public SynBleDataEvent(boolean startSyn) {
        this.startSyn = startSyn;
    }

    public SynBleDataEvent(boolean getIdAndStatusSuccess, boolean theSameUser) {
        this.getIdAndStatusSuccess = getIdAndStatusSuccess;
        this.theSameUser = theSameUser;
    }

    public SynBleDataEvent(boolean getIdAndStatusSuccess, boolean theSameUser, boolean isRunning) {
        this.getIdAndStatusSuccess = getIdAndStatusSuccess;
        this.theSameUser = theSameUser;
        this.isRunning = isRunning;
    }

    public SynBleDataEvent() {

    }

    public boolean isSynSuccess() {
        return synSuccess;
    }

    public void setSynSuccess(boolean synSuccess) {
        this.synSuccess = synSuccess;
    }

    public boolean isGetIdAndStatusSuccess() {
        return getIdAndStatusSuccess;
    }

    public void setGetIdAndStatusSuccess(boolean getIdAndStatusSuccess) {
        this.getIdAndStatusSuccess = getIdAndStatusSuccess;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getGlassesStatus() {
        return glassesStatus;
    }

    public void setGlassesStatus(int glassesStatus) {
        this.glassesStatus = glassesStatus;
    }

    public boolean isTheSameUser() {
        return theSameUser;
    }

    public void setTheSameUser(boolean theSameUser) {
        this.theSameUser = theSameUser;
    }

    public boolean isStartSyn() {
        return startSyn;
    }

    public void setStartSyn(boolean startSyn) {
        this.startSyn = startSyn;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
