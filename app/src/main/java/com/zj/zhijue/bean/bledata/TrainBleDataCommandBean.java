package com.zj.zhijue.bean.bledata;


import com.android.common.baselibrary.blebean.BaseCmdBean;

/**
 * APP 发送给 蓝牙设备的数据
 */
public class TrainBleDataCommandBean extends BaseCmdBean {
    private String userId;
    private String deviceId;
    private int trainMode;
    private int age;
    private int sex;
    private int leftEyeDegree;
    private int rightEyeDegree;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getTrainMode() {
        return trainMode;
    }

    public void setTrainMode(int trainMode) {
        this.trainMode = trainMode;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getLeftEyeDegree() {
        return leftEyeDegree;
    }

    public void setLeftEyeDegree(int leftEyeDegree) {
        this.leftEyeDegree = leftEyeDegree;
    }

    public int getRightEyeDegree() {
        return rightEyeDegree;
    }

    public void setRightEyeDegree(int rightEyeDegree) {
        this.rightEyeDegree = rightEyeDegree;
    }

    @Override
    protected String buildSourceData() {
        /**
         * 构造 Hex 数据
         */
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("a2");
        strBuild.append("0001");//id
        strBuild.append(decimalism2Hex(String.valueOf(age), 2));
        strBuild.append(decimalism2Hex(String.valueOf(trainMode), 2));
        strBuild.append(decimalism2Hex(String.valueOf(sex), 2));
        strBuild.append(decimalism2Hex(String.valueOf(leftEyeDegree), 4));
        strBuild.append(decimalism2Hex(String.valueOf(rightEyeDegree), 4));
        return strBuild.toString();
    }

    @Override
    protected void putData2DataArray() {

    }
}
