package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;

@Keep
public class HttpResponseGlassInitDataBackBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResponseGlassInitDataBackBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }

    @Keep
    public class DataBean {
        private String memberId;
        private int maxRunNumber;//": 800.00,
        private int startSpeed;//": 700.00,
        private int stopSpeed;//": 600.00,
        private int speedInc;//": 200.00,
        private int commonSpeed;
        private int machineData6;
        private int machineData7;
        private int machineData8;
        private int machineData9;
        private String systemTime;//": "2019-08-23 18:23:32"
        private int isNewUser;

        private int diopterState;//1:近视；2:老花；3:远视；4:弱视
        private float leftEyeDegree;//	左眼测量度数
        private float rightEyeDegree;//	右眼测量度数
        private float interpupillary;//	瞳距
        private float leftAstigmatismDegree;//	左眼散光度数
        private float rightAstigmatismDegree;//	右眼散光度数
        private float leftHandleDegree;//	左眼真实度数(APP上报给眼镜硬件)
        private float rightHandleDegree;//	右眼真实度数（APP上报给眼镜硬件）
        private float leftTrainingPlusRegion;//	左眼训练正区间
        private float leftTrainingMinusRegion;//	左眼训练负区间
        private float leftTrainingPlusRegionStep;//	左眼训练正区间步长
        private float leftTrainingMinusRegionStep;//	左眼训练负区间步长
        private float leftTrainingPlusRegionMaxStep;//	左眼训练正区间最大步长
        private float leftTrainingMinusRegionMaxStep;//	左眼训练负区间最大步长
        private int leftTrainingMaxTimes;//	左眼训练最大周期数
        private int leftTrainingMaxSpeed;//	左眼训练最大速度
        private int leftTrainingStartSpeed;//	左眼训练开始速度
        private int leftTrainingSpeedInc;//	左眼训练速度增量
        private int leftTrainingStepParam;//	左眼训练步长系数
        private float rightTrainingPlusRegion;//	右眼训练正区间
        private float rightTrainingMinusRegion;//	右眼训练负区间
        private float rightTrainingPlusRegionStep;//	右眼训练正区间步长
        private float rightTrainingMinusRegionStep;//	右眼训练负区间步长
        private float rightTrainingPlusRegionMaxStep;//	右眼训练正区间最大步长
        private float rightTrainingMinusRegionMaxStep;//	右眼训练负区间最大步长
        private int rightTrainingMaxTimes;//	右眼训练最大周期数
        private int rightTrainingMaxSpeed;//	右眼训练最大速度
        private int rightTrainingStartSpeed;//	右眼训练开始速度
        private int rightTrainingSpeedInc;//	右眼训练速度增量
        private int rightTrainingStepParam;//	右眼训练步长系数
        private String message;//请求提示信息
        private String cursor;//分页信息

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public int getMaxRunNumber() {
            return maxRunNumber;
        }

        public void setMaxRunNumber(int maxRunNumber) {
            this.maxRunNumber = maxRunNumber;
        }

        public int getStartSpeed() {
            return startSpeed;
        }

        public void setStartSpeed(int startSpeed) {
            this.startSpeed = startSpeed;
        }

        public int getStopSpeed() {
            return stopSpeed;
        }

        public void setStopSpeed(int stopSpeed) {
            this.stopSpeed = stopSpeed;
        }

        public int getSpeedInc() {
            return speedInc;
        }

        public void setSpeedInc(int speedInc) {
            this.speedInc = speedInc;
        }

        public int getCommonSpeed() {
            return commonSpeed;
        }

        public void setCommonSpeed(int commonSpeed) {
            this.commonSpeed = commonSpeed;
        }

        public int getMachineData6() {
            return machineData6;
        }

        public void setMachineData6(int machineData6) {
            this.machineData6 = machineData6;
        }

        public int getMachineData7() {
            return machineData7;
        }

        public void setMachineData7(int machineData7) {
            this.machineData7 = machineData7;
        }

        public int getMachineData8() {
            return machineData8;
        }

        public void setMachineData8(int machineData8) {
            this.machineData8 = machineData8;
        }

        public int getMachineData9() {
            return machineData9;
        }

        public void setMachineData9(int machineData9) {
            this.machineData9 = machineData9;
        }

        public String getSystemTime() {
            return systemTime;
        }

        public void setSystemTime(String systemTime) {
            this.systemTime = systemTime;
        }

        public int getIsNewUser() {
            return isNewUser;
        }

        public void setIsNewUser(int isNewUser) {
            this.isNewUser = isNewUser;
        }

        public int getDiopterState() {
            return diopterState;
        }

        public void setDiopterState(int diopterState) {
            this.diopterState = diopterState;
        }

        public float getLeftEyeDegree() {
            return leftEyeDegree;
        }

        public void setLeftEyeDegree(float leftEyeDegree) {
            this.leftEyeDegree = leftEyeDegree;
        }

        public float getRightEyeDegree() {
            return rightEyeDegree;
        }

        public void setRightEyeDegree(float rightEyeDegree) {
            this.rightEyeDegree = rightEyeDegree;
        }

        public float getInterpupillary() {
            return interpupillary;
        }

        public void setInterpupillary(float interpupillary) {
            this.interpupillary = interpupillary;
        }

        public float getLeftAstigmatismDegree() {
            return leftAstigmatismDegree;
        }

        public void setLeftAstigmatismDegree(float leftAstigmatismDegree) {
            this.leftAstigmatismDegree = leftAstigmatismDegree;
        }

        public float getRightAstigmatismDegree() {
            return rightAstigmatismDegree;
        }

        public void setRightAstigmatismDegree(float rightAstigmatismDegree) {
            this.rightAstigmatismDegree = rightAstigmatismDegree;
        }

        public float getLeftHandleDegree() {
            return leftHandleDegree;
        }

        public void setLeftHandleDegree(float leftHandleDegree) {
            this.leftHandleDegree = leftHandleDegree;
        }

        public float getRightHandleDegree() {
            return rightHandleDegree;
        }

        public void setRightHandleDegree(float rightHandleDegree) {
            this.rightHandleDegree = rightHandleDegree;
        }

        public float getLeftTrainingPlusRegion() {
            return leftTrainingPlusRegion;
        }

        public void setLeftTrainingPlusRegion(float leftTrainingPlusRegion) {
            this.leftTrainingPlusRegion = leftTrainingPlusRegion;
        }

        public float getLeftTrainingMinusRegion() {
            return leftTrainingMinusRegion;
        }

        public void setLeftTrainingMinusRegion(float leftTrainingMinusRegion) {
            this.leftTrainingMinusRegion = leftTrainingMinusRegion;
        }

        public float getLeftTrainingPlusRegionStep() {
            return leftTrainingPlusRegionStep;
        }

        public void setLeftTrainingPlusRegionStep(float leftTrainingPlusRegionStep) {
            this.leftTrainingPlusRegionStep = leftTrainingPlusRegionStep;
        }

        public float getLeftTrainingMinusRegionStep() {
            return leftTrainingMinusRegionStep;
        }

        public void setLeftTrainingMinusRegionStep(float leftTrainingMinusRegionStep) {
            this.leftTrainingMinusRegionStep = leftTrainingMinusRegionStep;
        }

        public float getLeftTrainingPlusRegionMaxStep() {
            return leftTrainingPlusRegionMaxStep;
        }

        public void setLeftTrainingPlusRegionMaxStep(float leftTrainingPlusRegionMaxStep) {
            this.leftTrainingPlusRegionMaxStep = leftTrainingPlusRegionMaxStep;
        }

        public float getLeftTrainingMinusRegionMaxStep() {
            return leftTrainingMinusRegionMaxStep;
        }

        public void setLeftTrainingMinusRegionMaxStep(float leftTrainingMinusRegionMaxStep) {
            this.leftTrainingMinusRegionMaxStep = leftTrainingMinusRegionMaxStep;
        }

        public int getLeftTrainingMaxTimes() {
            return leftTrainingMaxTimes;
        }

        public void setLeftTrainingMaxTimes(int leftTrainingMaxTimes) {
            this.leftTrainingMaxTimes = leftTrainingMaxTimes;
        }

        public int getLeftTrainingMaxSpeed() {
            return leftTrainingMaxSpeed;
        }

        public void setLeftTrainingMaxSpeed(int leftTrainingMaxSpeed) {
            this.leftTrainingMaxSpeed = leftTrainingMaxSpeed;
        }

        public int getLeftTrainingStartSpeed() {
            return leftTrainingStartSpeed;
        }

        public void setLeftTrainingStartSpeed(int leftTrainingStartSpeed) {
            this.leftTrainingStartSpeed = leftTrainingStartSpeed;
        }

        public int getLeftTrainingSpeedInc() {
            return leftTrainingSpeedInc;
        }

        public void setLeftTrainingSpeedInc(int leftTrainingSpeedInc) {
            this.leftTrainingSpeedInc = leftTrainingSpeedInc;
        }

        public int getLeftTrainingStepParam() {
            return leftTrainingStepParam;
        }

        public void setLeftTrainingStepParam(int leftTrainingStepParam) {
            this.leftTrainingStepParam = leftTrainingStepParam;
        }

        public float getRightTrainingPlusRegion() {
            return rightTrainingPlusRegion;
        }

        public void setRightTrainingPlusRegion(float rightTrainingPlusRegion) {
            this.rightTrainingPlusRegion = rightTrainingPlusRegion;
        }

        public float getRightTrainingMinusRegion() {
            return rightTrainingMinusRegion;
        }

        public void setRightTrainingMinusRegion(float rightTrainingMinusRegion) {
            this.rightTrainingMinusRegion = rightTrainingMinusRegion;
        }

        public float getRightTrainingPlusRegionStep() {
            return rightTrainingPlusRegionStep;
        }

        public void setRightTrainingPlusRegionStep(float rightTrainingPlusRegionStep) {
            this.rightTrainingPlusRegionStep = rightTrainingPlusRegionStep;
        }

        public float getRightTrainingMinusRegionStep() {
            return rightTrainingMinusRegionStep;
        }

        public void setRightTrainingMinusRegionStep(float rightTrainingMinusRegionStep) {
            this.rightTrainingMinusRegionStep = rightTrainingMinusRegionStep;
        }

        public float getRightTrainingPlusRegionMaxStep() {
            return rightTrainingPlusRegionMaxStep;
        }

        public void setRightTrainingPlusRegionMaxStep(float rightTrainingPlusRegionMaxStep) {
            this.rightTrainingPlusRegionMaxStep = rightTrainingPlusRegionMaxStep;
        }

        public float getRightTrainingMinusRegionMaxStep() {
            return rightTrainingMinusRegionMaxStep;
        }

        public void setRightTrainingMinusRegionMaxStep(float rightTrainingMinusRegionMaxStep) {
            this.rightTrainingMinusRegionMaxStep = rightTrainingMinusRegionMaxStep;
        }

        public int getRightTrainingMaxTimes() {
            return rightTrainingMaxTimes;
        }

        public void setRightTrainingMaxTimes(int rightTrainingMaxTimes) {
            this.rightTrainingMaxTimes = rightTrainingMaxTimes;
        }

        public int getRightTrainingMaxSpeed() {
            return rightTrainingMaxSpeed;
        }

        public void setRightTrainingMaxSpeed(int rightTrainingMaxSpeed) {
            this.rightTrainingMaxSpeed = rightTrainingMaxSpeed;
        }

        public int getRightTrainingStartSpeed() {
            return rightTrainingStartSpeed;
        }

        public void setRightTrainingStartSpeed(int rightTrainingStartSpeed) {
            this.rightTrainingStartSpeed = rightTrainingStartSpeed;
        }

        public int getRightTrainingSpeedInc() {
            return rightTrainingSpeedInc;
        }

        public void setRightTrainingSpeedInc(int rightTrainingSpeedInc) {
            this.rightTrainingSpeedInc = rightTrainingSpeedInc;
        }

        public int getRightTrainingStepParam() {
            return rightTrainingStepParam;
        }

        public void setRightTrainingStepParam(int rightTrainingStepParam) {
            this.rightTrainingStepParam = rightTrainingStepParam;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCursor() {
            return cursor;
        }

        public void setCursor(String cursor) {
            this.cursor = cursor;
        }
    }


}
