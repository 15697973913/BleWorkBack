package com.zj.zhijue.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 注册时，填写个人视力信息
 */
public class BindPersonalInfoBean implements Parcelable {
    private String userName;
    //private int gender;
    private String idNo;
    private int eyeStatus;//近视，远视，老花，弱视
    private float leftEyeSight;//左眼度数
    private float rightEyeSight;//右眼度数
    private float leftEyeAstigmatism;//左眼散光
    private float rightEyeAstigmatism;//右眼散光
    private float leftEyeAxialDirection;//左眼轴向
    private float rightEyeAxialDirection;//右眼轴向
    private float interpuillaryDistance;//瞳距
    private String glassesSN;//眼镜序列号
    /**
     * 裸眼视力
     */
    private String nakedRightEyeDegree;
    private String nakedLeftEyeDegree;
    private String nakedBinoculusDegree;

    /**
     * 最佳视力
     */
    private String correctRightEyeDegree;
    private String correctLeftEyeDegree;
    private String correctBinoculusDegree;

    private int sex;
    private int age;

    public BindPersonalInfoBean() {
    }


    protected BindPersonalInfoBean(Parcel in) {
        userName = in.readString();
        idNo = in.readString();
        eyeStatus = in.readInt();
        leftEyeSight = in.readFloat();
        rightEyeSight = in.readFloat();
        leftEyeAstigmatism = in.readFloat();
        rightEyeAstigmatism = in.readFloat();
        leftEyeAxialDirection = in.readFloat();
        rightEyeAxialDirection = in.readFloat();
        interpuillaryDistance = in.readFloat();
        glassesSN = in.readString();
        nakedRightEyeDegree = in.readString();
        nakedLeftEyeDegree = in.readString();
        nakedBinoculusDegree = in.readString();
        correctRightEyeDegree = in.readString();
        correctLeftEyeDegree = in.readString();
        correctBinoculusDegree = in.readString();
        sex = in.readInt();
        age = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(idNo);
        dest.writeInt(eyeStatus);
        dest.writeFloat(leftEyeSight);
        dest.writeFloat(rightEyeSight);
        dest.writeFloat(leftEyeAstigmatism);
        dest.writeFloat(rightEyeAstigmatism);
        dest.writeFloat(leftEyeAxialDirection);
        dest.writeFloat(rightEyeAxialDirection);
        dest.writeFloat(interpuillaryDistance);
        dest.writeString(glassesSN);
        dest.writeString(nakedRightEyeDegree);
        dest.writeString(nakedLeftEyeDegree);
        dest.writeString(nakedBinoculusDegree);
        dest.writeString(correctRightEyeDegree);
        dest.writeString(correctLeftEyeDegree);
        dest.writeString(correctBinoculusDegree);
        dest.writeInt(sex);
        dest.writeInt(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BindPersonalInfoBean> CREATOR = new Creator<BindPersonalInfoBean>() {
        @Override
        public BindPersonalInfoBean createFromParcel(Parcel in) {
            return new BindPersonalInfoBean(in);
        }

        @Override
        public BindPersonalInfoBean[] newArray(int size) {
            return new BindPersonalInfoBean[size];
        }
    };

    public String getNakedRightEyeDegree() {
        return nakedRightEyeDegree;
    }

    public void setNakedRightEyeDegree(String nakedRightEyeDegree) {
        this.nakedRightEyeDegree = nakedRightEyeDegree;
    }

    public String getNakedLeftEyeDegree() {
        return nakedLeftEyeDegree;
    }

    public void setNakedLeftEyeDegree(String nakedLeftEyeDegree) {
        this.nakedLeftEyeDegree = nakedLeftEyeDegree;
    }

    public String getNakedBinoculusDegree() {
        return nakedBinoculusDegree;
    }

    public void setNakedBinoculusDegree(String nakedBinoculusDegree) {
        this.nakedBinoculusDegree = nakedBinoculusDegree;
    }

    public String getCorrectRightEyeDegree() {
        return correctRightEyeDegree;
    }

    public void setCorrectRightEyeDegree(String correctRightEyeDegree) {
        this.correctRightEyeDegree = correctRightEyeDegree;
    }

    public String getCorrectLeftEyeDegree() {
        return correctLeftEyeDegree;
    }

    public void setCorrectLeftEyeDegree(String correctLeftEyeDegree) {
        this.correctLeftEyeDegree = correctLeftEyeDegree;
    }

    public String getCorrectBinoculusDegree() {
        return correctBinoculusDegree;
    }

    public void setCorrectBinoculusDegree(String correctBinoculusDegree) {
        this.correctBinoculusDegree = correctBinoculusDegree;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

 /*   public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }*/

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public int getEyeStatus() {
        return eyeStatus;
    }

    public void setEyeStatus(int eyeStatus) {
        this.eyeStatus = eyeStatus;
    }

    public float getLeftEyeSight() {
        return leftEyeSight;
    }

    public void setLeftEyeSight(float leftEyeSight) {
        this.leftEyeSight = leftEyeSight;
    }

    public float getRightEyeSight() {
        return rightEyeSight;
    }

    public void setRightEyeSight(float rightEyeSight) {
        this.rightEyeSight = rightEyeSight;
    }

    public float getLeftEyeAstigmatism() {
        return leftEyeAstigmatism;
    }

    public void setLeftEyeAstigmatism(float leftEyeAstigmatism) {
        this.leftEyeAstigmatism = leftEyeAstigmatism;
    }

    public float getRightEyeAstigmatism() {
        return rightEyeAstigmatism;
    }

    public void setRightEyeAstigmatism(float rightEyeAstigmatism) {
        this.rightEyeAstigmatism = rightEyeAstigmatism;
    }

    public float getLeftEyeAxialDirection() {
        return leftEyeAxialDirection;
    }

    public void setLeftEyeAxialDirection(float leftEyeAxialDirection) {
        this.leftEyeAxialDirection = leftEyeAxialDirection;
    }

    public float getRightEyeAxialDirection() {
        return rightEyeAxialDirection;
    }

    public void setRightEyeAxialDirection(float rightEyeAxialDirection) {
        this.rightEyeAxialDirection = rightEyeAxialDirection;
    }

    public float getInterpuillaryDistance() {
        return interpuillaryDistance;
    }

    public void setInterpuillaryDistance(float interpuillaryDistance) {
        this.interpuillaryDistance = interpuillaryDistance;
    }

    public String getGlassesSN() {
        return glassesSN;
    }

    public void setGlassesSN(String glassesSN) {
        this.glassesSN = glassesSN;
    }

    @Override
    public String toString() {
        return "BindPersonalInfoBean{" +
                "userName='" + userName + '\'' +
                //", gender=" + gender +
                ", idNo='" + idNo + '\'' +
                ", eyeStatus=" + eyeStatus +
                ", leftEyeSight=" + leftEyeSight +
                ", rightEyeSight=" + rightEyeSight +
                ", leftEyeAstigmatism=" + leftEyeAstigmatism +
                ", rightEyeAstigmatism=" + rightEyeAstigmatism +
                ", leftEyeAxialDirection=" + leftEyeAxialDirection +
                ", rightEyeAxialDirection=" + rightEyeAxialDirection +
                ", interpuillaryDistance=" + interpuillaryDistance +
                '}';
    }



}
