package com.zj.zhijue.bean;

/**
 * APP发送获取训练实时记录数据
 */
public class ReqTrainingDataBean2 {

    private int mearsureDistance;//训练测距
    private int trainModel;//训练模式 1：训练模式；2：矫正模式；3：安全模式
    private int operationType;//操作类型：0-启动,1-暂停,2-停止,3-干预，4-运行 对应APP4个操作按键
    private float batteryVoltage;//电量
    private float batteryPercent;//电量百分比
    private String trainingTime;//训练时间
    private String timeZone;//训练时区
    private int totalTraingSeconds;//累计训练秒数
    private int trainingSeconds;//本次训练秒数
    private String operationCmd;//操作命令
    private String mobileBluetoothTime;//蓝牙时间;
    private String mobileRealTimeId;//APP本地生成的ID，防止断网本地存储，后提交的时候，避免重复提交数据
    private String userCode;//用户标识：手机号

    public int getMearsureDistance() {
        return mearsureDistance;
    }

    public void setMearsureDistance(int mearsureDistance) {
        this.mearsureDistance = mearsureDistance;
    }

    public int getTrainModel() {
        return trainModel;
    }

    public void setTrainModel(int trainModel) {
        this.trainModel = trainModel;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public float getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(float batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public float getBatteryPercent() {
        return batteryPercent;
    }

    public void setBatteryPercent(float batteryPercent) {
        this.batteryPercent = batteryPercent;
    }

    public String getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(String trainingTime) {
        this.trainingTime = trainingTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getTotalTraingSeconds() {
        return totalTraingSeconds;
    }

    public void setTotalTraingSeconds(int totalTraingSeconds) {
        this.totalTraingSeconds = totalTraingSeconds;
    }

    public int getTrainingSeconds() {
        return trainingSeconds;
    }

    public void setTrainingSeconds(int trainingSeconds) {
        this.trainingSeconds = trainingSeconds;
    }

    public String getOperationCmd() {
        return operationCmd;
    }

    public void setOperationCmd(String operationCmd) {
        this.operationCmd = operationCmd;
    }

    public String getMobileBluetoothTime() {
        return mobileBluetoothTime;
    }

    public void setMobileBluetoothTime(String mobileBluetoothTime) {
        this.mobileBluetoothTime = mobileBluetoothTime;
    }

    public String getMobileRealTimeId() {
        return mobileRealTimeId;
    }

    public void setMobileRealTimeId(String mobileRealTimeId) {
        this.mobileRealTimeId = mobileRealTimeId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
