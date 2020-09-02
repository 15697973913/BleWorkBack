package com.zj.zhijue.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Date:2020/6/28
 * Time:15:35
 * Des:
 * Author:Sonne
 */
public class UseInfoBean {


    /**
     * status : success
     * message : 成功
     * data : {"id":"f7a97d57-92f6-4036-8bcb-fea13d5dbf86","guardian_id":null,"areaCode":"441602","loginName":"13510725010_P","password":null,"nickname":"13510725010","name":"李先生","age":29,"sex":1,"bornDate":"1991-05-03 00:00:00","credentialsType":null,"credentialsCard":null,"phone":"13510725010","diopterState":1,"leftEyeDegree":100,"rightEyeDegree":100,"interpupillary":62,"leftAstigmatismDegree":25,"rightAstigmatismDegree":25,"astigmatismDegree":0,"leftHandleDegree":112.5,"rightHandleDegree":112.5,"rightColumnMirror":null,"leftColumnMirror":null,"rightAxial":0,"leftAxial":0,"nakedLeftEyeDegree":4.5,"nakedRightEyeDegree":4.3,"nakedBinoculusDegree":4,"correctLeftEyeDegree":4.7,"correctRightEyeDegree":4.5,"correctBinoculusDegree":4.5,"isNewUser":0,"systemTime":null,"status":null,"expirationTime":null,"trainTimeYear":0,"trainTimeMonth":0,"trainTimeDay":0,"trainTimeHour":0,"trainTimeMinute":19,"trainTimeSecond":34,"face":null}
     * cursor : null
     */

    private String status;
    private String message;
    private UseBean data;
    private Object cursor;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UseBean getData() {
        return data;
    }

    public void setData(UseBean data) {
        this.data = data;
    }

    public Object getCursor() {
        return cursor;
    }

    public void setCursor(Object cursor) {
        this.cursor = cursor;
    }

    public static class UseBean implements Parcelable {
        /**
         * id : f7a97d57-92f6-4036-8bcb-fea13d5dbf86
         * guardian_id : null
         * areaCode : 441602
         * loginName : 13510725010_P
         * password : null
         * nickname : 13510725010
         * name : 李先生
         * age : 29
         * sex : 1
         * bornDate : 1991-05-03 00:00:00
         * credentialsType : null
         * credentialsCard : null
         * phone : 13510725010
         * diopterState : 1
         * leftEyeDegree : 100.0
         * rightEyeDegree : 100.0
         * interpupillary : 62.0
         * leftAstigmatismDegree : 25.0
         * rightAstigmatismDegree : 25.0
         * astigmatismDegree : 0.0
         * leftHandleDegree : 112.5
         * rightHandleDegree : 112.5
         * rightColumnMirror : null
         * leftColumnMirror : null
         * rightAxial : 0.0
         * leftAxial : 0.0
         * nakedLeftEyeDegree : 4.5
         * nakedRightEyeDegree : 4.3
         * nakedBinoculusDegree : 4.0
         * correctLeftEyeDegree : 4.7
         * correctRightEyeDegree : 4.5
         * correctBinoculusDegree : 4.5
         * isNewUser : 0
         * systemTime : null
         * status : null
         * expirationTime : null
         * trainTimeYear : 0
         * trainTimeMonth : 0
         * trainTimeDay : 0
         * trainTimeHour : 0
         * trainTimeMinute : 19
         * trainTimeSecond : 34
         * face : null
         */

        private String id;
        private String guardian_id;
        private String areaCode;
        private String loginName;
        private String password;
        private String nickname;
        private String name;
        private int age;
        private int sex;
        private String bornDate;
        private String credentialsType;
        private String credentialsCard;
        private String phone;
        private int diopterState;
        private double leftEyeDegree;
        private double rightEyeDegree;
        private double interpupillary;
        private double leftAstigmatismDegree;
        private double rightAstigmatismDegree;
        private double astigmatismDegree;
        private double leftHandleDegree;
        private double rightHandleDegree;
        private double rightColumnMirror;
        private double leftColumnMirror;
        private double rightAxial;
        private double leftAxial;
        private double nakedLeftEyeDegree;
        private double nakedRightEyeDegree;
        private double nakedBinoculusDegree;
        private double correctLeftEyeDegree;
        private double correctRightEyeDegree;
        private double correctBinoculusDegree;
        private int isNewUser;
        private String systemTime;
        private int status;
        private String expirationTime;
        private int trainTimeYear;
        private int trainTimeMonth;
        private int trainTimeDay;
        private int trainTimeHour;
        private int trainTimeMinute;
        private int trainTimeSecond;
        private String face;
        private String totalTime;
        private String usedTime;

        protected UseBean(Parcel in) {
            id = in.readString();
            guardian_id = in.readString();
            areaCode = in.readString();
            loginName = in.readString();
            password = in.readString();
            nickname = in.readString();
            name = in.readString();
            age = in.readInt();
            sex = in.readInt();
            bornDate = in.readString();
            credentialsType = in.readString();
            credentialsCard = in.readString();
            phone = in.readString();
            diopterState = in.readInt();
            leftEyeDegree = in.readDouble();
            rightEyeDegree = in.readDouble();
            interpupillary = in.readDouble();
            leftAstigmatismDegree = in.readDouble();
            rightAstigmatismDegree = in.readDouble();
            astigmatismDegree = in.readDouble();
            leftHandleDegree = in.readDouble();
            rightHandleDegree = in.readDouble();
            rightColumnMirror = in.readDouble();
            leftColumnMirror = in.readDouble();
            rightAxial = in.readDouble();
            leftAxial = in.readDouble();
            nakedLeftEyeDegree = in.readDouble();
            nakedRightEyeDegree = in.readDouble();
            nakedBinoculusDegree = in.readDouble();
            correctLeftEyeDegree = in.readDouble();
            correctRightEyeDegree = in.readDouble();
            correctBinoculusDegree = in.readDouble();
            isNewUser = in.readInt();
            systemTime = in.readString();
            status = in.readInt();
            expirationTime = in.readString();
            trainTimeYear = in.readInt();
            trainTimeMonth = in.readInt();
            trainTimeDay = in.readInt();
            trainTimeHour = in.readInt();
            trainTimeMinute = in.readInt();
            trainTimeSecond = in.readInt();
            face = in.readString();
            totalTime = in.readString();
            usedTime = in.readString();
        }

        public static final Creator<UseBean> CREATOR = new Creator<UseBean>() {
            @Override
            public UseBean createFromParcel(Parcel in) {
                return new UseBean(in);
            }

            @Override
            public UseBean[] newArray(int size) {
                return new UseBean[size];
            }
        };

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }

        public String getUsedTime() {
            return usedTime;
        }

        public void setUsedTime(String usedTime) {
            this.usedTime = usedTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGuardian_id() {
            return guardian_id;
        }

        public void setGuardian_id(String guardian_id) {
            this.guardian_id = guardian_id;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getBornDate() {
            return bornDate;
        }

        public void setBornDate(String bornDate) {
            this.bornDate = bornDate;
        }

        public String getCredentialsType() {
            return credentialsType;
        }

        public void setCredentialsType(String credentialsType) {
            this.credentialsType = credentialsType;
        }

        public String getCredentialsCard() {
            return credentialsCard;
        }

        public void setCredentialsCard(String credentialsCard) {
            this.credentialsCard = credentialsCard;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getDiopterState() {
            return diopterState;
        }

        public void setDiopterState(int diopterState) {
            this.diopterState = diopterState;
        }

        public double getLeftEyeDegree() {
            return leftEyeDegree;
        }

        public void setLeftEyeDegree(double leftEyeDegree) {
            this.leftEyeDegree = leftEyeDegree;
        }

        public double getRightEyeDegree() {
            return rightEyeDegree;
        }

        public void setRightEyeDegree(double rightEyeDegree) {
            this.rightEyeDegree = rightEyeDegree;
        }

        public double getInterpupillary() {
            return interpupillary;
        }

        public void setInterpupillary(double interpupillary) {
            this.interpupillary = interpupillary;
        }

        public double getLeftAstigmatismDegree() {
            return leftAstigmatismDegree;
        }

        public void setLeftAstigmatismDegree(double leftAstigmatismDegree) {
            this.leftAstigmatismDegree = leftAstigmatismDegree;
        }

        public double getRightAstigmatismDegree() {
            return rightAstigmatismDegree;
        }

        public void setRightAstigmatismDegree(double rightAstigmatismDegree) {
            this.rightAstigmatismDegree = rightAstigmatismDegree;
        }

        public double getAstigmatismDegree() {
            return astigmatismDegree;
        }

        public void setAstigmatismDegree(double astigmatismDegree) {
            this.astigmatismDegree = astigmatismDegree;
        }

        public double getLeftHandleDegree() {
            return leftHandleDegree;
        }

        public void setLeftHandleDegree(double leftHandleDegree) {
            this.leftHandleDegree = leftHandleDegree;
        }

        public double getRightHandleDegree() {
            return rightHandleDegree;
        }

        public void setRightHandleDegree(double rightHandleDegree) {
            this.rightHandleDegree = rightHandleDegree;
        }

        public double getRightColumnMirror() {
            return rightColumnMirror;
        }

        public void setRightColumnMirror(double rightColumnMirror) {
            this.rightColumnMirror = rightColumnMirror;
        }

        public double getLeftColumnMirror() {
            return leftColumnMirror;
        }

        public void setLeftColumnMirror(double leftColumnMirror) {
            this.leftColumnMirror = leftColumnMirror;
        }

        public double getRightAxial() {
            return rightAxial;
        }

        public void setRightAxial(double rightAxial) {
            this.rightAxial = rightAxial;
        }

        public double getLeftAxial() {
            return leftAxial;
        }

        public void setLeftAxial(double leftAxial) {
            this.leftAxial = leftAxial;
        }

        public double getNakedLeftEyeDegree() {
            return nakedLeftEyeDegree;
        }

        public void setNakedLeftEyeDegree(double nakedLeftEyeDegree) {
            this.nakedLeftEyeDegree = nakedLeftEyeDegree;
        }

        public double getNakedRightEyeDegree() {
            return nakedRightEyeDegree;
        }

        public void setNakedRightEyeDegree(double nakedRightEyeDegree) {
            this.nakedRightEyeDegree = nakedRightEyeDegree;
        }

        public double getNakedBinoculusDegree() {
            return nakedBinoculusDegree;
        }

        public void setNakedBinoculusDegree(double nakedBinoculusDegree) {
            this.nakedBinoculusDegree = nakedBinoculusDegree;
        }

        public double getCorrectLeftEyeDegree() {
            return correctLeftEyeDegree;
        }

        public void setCorrectLeftEyeDegree(double correctLeftEyeDegree) {
            this.correctLeftEyeDegree = correctLeftEyeDegree;
        }

        public double getCorrectRightEyeDegree() {
            return correctRightEyeDegree;
        }

        public void setCorrectRightEyeDegree(double correctRightEyeDegree) {
            this.correctRightEyeDegree = correctRightEyeDegree;
        }

        public double getCorrectBinoculusDegree() {
            return correctBinoculusDegree;
        }

        public void setCorrectBinoculusDegree(double correctBinoculusDegree) {
            this.correctBinoculusDegree = correctBinoculusDegree;
        }

        public int getIsNewUser() {
            return isNewUser;
        }

        public void setIsNewUser(int isNewUser) {
            this.isNewUser = isNewUser;
        }

        public String getSystemTime() {
            return systemTime;
        }

        public void setSystemTime(String systemTime) {
            this.systemTime = systemTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(String expirationTime) {
            this.expirationTime = expirationTime;
        }

        public int getTrainTimeYear() {
            return trainTimeYear;
        }

        public void setTrainTimeYear(int trainTimeYear) {
            this.trainTimeYear = trainTimeYear;
        }

        public int getTrainTimeMonth() {
            return trainTimeMonth;
        }

        public void setTrainTimeMonth(int trainTimeMonth) {
            this.trainTimeMonth = trainTimeMonth;
        }

        public int getTrainTimeDay() {
            return trainTimeDay;
        }

        public void setTrainTimeDay(int trainTimeDay) {
            this.trainTimeDay = trainTimeDay;
        }

        public int getTrainTimeHour() {
            return trainTimeHour;
        }

        public void setTrainTimeHour(int trainTimeHour) {
            this.trainTimeHour = trainTimeHour;
        }

        public int getTrainTimeMinute() {
            return trainTimeMinute;
        }

        public void setTrainTimeMinute(int trainTimeMinute) {
            this.trainTimeMinute = trainTimeMinute;
        }

        public int getTrainTimeSecond() {
            return trainTimeSecond;
        }

        public void setTrainTimeSecond(int trainTimeSecond) {
            this.trainTimeSecond = trainTimeSecond;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(guardian_id);
            dest.writeString(areaCode);
            dest.writeString(loginName);
            dest.writeString(password);
            dest.writeString(nickname);
            dest.writeString(name);
            dest.writeInt(age);
            dest.writeInt(sex);
            dest.writeString(bornDate);
            dest.writeString(credentialsType);
            dest.writeString(credentialsCard);
            dest.writeString(phone);
            dest.writeInt(diopterState);
            dest.writeDouble(leftEyeDegree);
            dest.writeDouble(rightEyeDegree);
            dest.writeDouble(interpupillary);
            dest.writeDouble(leftAstigmatismDegree);
            dest.writeDouble(rightAstigmatismDegree);
            dest.writeDouble(astigmatismDegree);
            dest.writeDouble(leftHandleDegree);
            dest.writeDouble(rightHandleDegree);
            dest.writeDouble(rightColumnMirror);
            dest.writeDouble(leftColumnMirror);
            dest.writeDouble(rightAxial);
            dest.writeDouble(leftAxial);
            dest.writeDouble(nakedLeftEyeDegree);
            dest.writeDouble(nakedRightEyeDegree);
            dest.writeDouble(nakedBinoculusDegree);
            dest.writeDouble(correctLeftEyeDegree);
            dest.writeDouble(correctRightEyeDegree);
            dest.writeDouble(correctBinoculusDegree);
            dest.writeInt(isNewUser);
            dest.writeString(systemTime);
            dest.writeInt(status);
            dest.writeString(expirationTime);
            dest.writeInt(trainTimeYear);
            dest.writeInt(trainTimeMonth);
            dest.writeInt(trainTimeDay);
            dest.writeInt(trainTimeHour);
            dest.writeInt(trainTimeMinute);
            dest.writeInt(trainTimeSecond);
            dest.writeString(face);
            dest.writeString(totalTime);
            dest.writeString(usedTime);
        }
    }
}
