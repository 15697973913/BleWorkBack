package com.zj.zhijue.bean;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Keep;

/**
 * 登录
 */
@Keep
public class MemberLoginResponseBeanFirst extends BaseBean {

    /**
     * data : {"hasEyeData":true,"tokenInfo":{"access_token":"15989894692#423931bda30a4b8389da6a570bab36b3","token_type":"bearer","expires_in":359650619,"scope":"all"},"userInfo":{"id":"29d14772-5007-4ea5-b6cf-5a6bea85a938","guardianId":null,"areaCode":null,"loginName":"15989894692_P","password":"e10adc3949ba59abbe56e057f20f883e","nickname":"15989894692","name":"hezp","age":35,"sex":2,"bornDate":"1985-01-06 00:00:00","credentialsType":0,"credentialsCard":"362531198501062782","phone":"15989894692","diopterState":1,"leftEyeDegree":300,"rightEyeDegree":300,"interpupillary":25,"leftAstigmatismDegree":50,"rightAstigmatismDegree":60,"astigmatismDegree":55,"leftHandleDegree":325,"rightHandleDegree":330,"rightColumnMirror":null,"leftColumnMirror":null,"rightAxial":10,"leftAxial":10,"nakedLeftEyeDegree":350,"nakedRightEyeDegree":350,"nakedBinoculusDegree":350,"correctLeftEyeDegree":250,"correctRightEyeDegree":250,"correctBinoculusDegree":250,"isNewUser":1,"totalMoney":10000,"totalTime":"1388:53:20","usedTime":"0:2:43","totalScore":null,"usedScore":null,"status":null,"expirationTime":null,"trainTimeYear":null,"trainTimeMonth":null,"trainTimeDay":null,"trainTimeHour":null,"trainTimeMinute":null,"trainTimeSecond":null,"face":"api/fastdfs/download/5d6962ca-35df-4d14-a1b8-d34adca24a53.png"}}
     * cursor : null
     */

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * hasEyeData : true
         * tokenInfo : {"access_token":"15989894692#423931bda30a4b8389da6a570bab36b3","token_type":"bearer","expires_in":359650619,"scope":"all"}
         * userInfo : {"id":"29d14772-5007-4ea5-b6cf-5a6bea85a938","guardianId":null,"areaCode":null,"loginName":"15989894692_P","password":"e10adc3949ba59abbe56e057f20f883e","nickname":"15989894692","name":"hezp","age":35,"sex":2,"bornDate":"1985-01-06 00:00:00","credentialsType":0,"credentialsCard":"362531198501062782","phone":"15989894692","diopterState":1,"leftEyeDegree":300,"rightEyeDegree":300,"interpupillary":25,"leftAstigmatismDegree":50,"rightAstigmatismDegree":60,"astigmatismDegree":55,"leftHandleDegree":325,"rightHandleDegree":330,"rightColumnMirror":null,"leftColumnMirror":null,"rightAxial":10,"leftAxial":10,"nakedLeftEyeDegree":350,"nakedRightEyeDegree":350,"nakedBinoculusDegree":350,"correctLeftEyeDegree":250,"correctRightEyeDegree":250,"correctBinoculusDegree":250,"isNewUser":1,"totalMoney":10000,"totalTime":"1388:53:20","usedTime":"0:2:43","totalScore":null,"usedScore":null,"status":null,"expirationTime":null,"trainTimeYear":null,"trainTimeMonth":null,"trainTimeDay":null,"trainTimeHour":null,"trainTimeMinute":null,"trainTimeSecond":null,"face":"api/fastdfs/download/5d6962ca-35df-4d14-a1b8-d34adca24a53.png"}
         */

        private boolean hasEyeData;
        private TokenInfoBean tokenInfo;
        private UserInfoBean userInfo;

        public boolean isHasEyeData() {
            return hasEyeData;
        }

        public void setHasEyeData(boolean hasEyeData) {
            this.hasEyeData = hasEyeData;
        }

        public TokenInfoBean getTokenInfo() {
            return tokenInfo;
        }

        public void setTokenInfo(TokenInfoBean tokenInfo) {
            this.tokenInfo = tokenInfo;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class TokenInfoBean {
            /**
             * access_token : 15989894692#423931bda30a4b8389da6a570bab36b3
             * token_type : bearer
             * expires_in : 359650619
             * scope : all
             */

            private String access_token;
            private String token_type;
            private int expires_in;
            private String scope;

            public String getAccess_token() {
                return access_token;
            }

            public void setAccess_token(String access_token) {
                this.access_token = access_token;
            }

            public String getToken_type() {
                return token_type;
            }

            public void setToken_type(String token_type) {
                this.token_type = token_type;
            }

            public int getExpires_in() {
                return expires_in;
            }

            public void setExpires_in(int expires_in) {
                this.expires_in = expires_in;
            }

            public String getScope() {
                return scope;
            }

            public void setScope(String scope) {
                this.scope = scope;
            }
        }

        public static class UserInfoBean {
            /**
             * id : 29d14772-5007-4ea5-b6cf-5a6bea85a938
             * guardianId : null
             * areaCode : null
             * loginName : 15989894692_P
             * password : e10adc3949ba59abbe56e057f20f883e
             * nickname : 15989894692
             * name : hezp
             * age : 35
             * sex : 2
             * bornDate : 1985-01-06 00:00:00
             * credentialsType : 0
             * credentialsCard : 362531198501062782
             * phone : 15989894692
             * diopterState : 1
             * leftEyeDegree : 300
             * rightEyeDegree : 300
             * interpupillary : 25
             * leftAstigmatismDegree : 50
             * rightAstigmatismDegree : 60
             * astigmatismDegree : 55
             * leftHandleDegree : 325
             * rightHandleDegree : 330
             * rightColumnMirror : null
             * leftColumnMirror : null
             * rightAxial : 10
             * leftAxial : 10
             * nakedLeftEyeDegree : 350
             * nakedRightEyeDegree : 350
             * nakedBinoculusDegree : 350
             * correctLeftEyeDegree : 250
             * correctRightEyeDegree : 250
             * correctBinoculusDegree : 250
             * isNewUser : 1
             * totalMoney : 10000
             * totalTime : 1388:53:20
             * usedTime : 0:2:43
             * totalScore : null
             * usedScore : null
             * status : null
             * expirationTime : null
             * trainTimeYear : null
             * trainTimeMonth : null
             * trainTimeDay : null
             * trainTimeHour : null
             * trainTimeMinute : null
             * trainTimeSecond : null
             * face : api/fastdfs/download/5d6962ca-35df-4d14-a1b8-d34adca24a53.png
             */

            private String id;
            private String guardianId;
            private String areaCode;
            private String loginName;
            private String password;
            private String nickname;
            private String name;
            private int age;
            private int sex;
            private String bornDate;
            private int credentialsType;
            private String credentialsCard;
            private String phone;
            private int diopterState;
            private int leftEyeDegree;
            private int rightEyeDegree;
            private int interpupillary;
            private int leftAstigmatismDegree;
            private int rightAstigmatismDegree;
            private int astigmatismDegree;
            private int leftHandleDegree;
            private int rightHandleDegree;
            private String rightColumnMirror;
            private String leftColumnMirror;
            private int rightAxial;
            private int leftAxial;
            private int nakedLeftEyeDegree;
            private int nakedRightEyeDegree;
            private int nakedBinoculusDegree;
            private int correctLeftEyeDegree;
            private int correctRightEyeDegree;
            private int correctBinoculusDegree;
            private int isNewUser;
            private int totalMoney;
            private String totalTime;
            private String usedTime;
            private String totalScore;
            private String usedScore;
            @SerializedName("status")
            private String statusX;
            private String expirationTime;
            private String trainTimeYear;
            private String trainTimeMonth;
            private String trainTimeDay;
            private String trainTimeHour;
            private String trainTimeMinute;
            private String trainTimeSecond;
            private String face;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGuardianId() {
                return guardianId;
            }

            public void setGuardianId(String guardianId) {
                this.guardianId = guardianId;
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

            public int getCredentialsType() {
                return credentialsType;
            }

            public void setCredentialsType(int credentialsType) {
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

            public int getInterpupillary() {
                return interpupillary;
            }

            public void setInterpupillary(int interpupillary) {
                this.interpupillary = interpupillary;
            }

            public int getLeftAstigmatismDegree() {
                return leftAstigmatismDegree;
            }

            public void setLeftAstigmatismDegree(int leftAstigmatismDegree) {
                this.leftAstigmatismDegree = leftAstigmatismDegree;
            }

            public int getRightAstigmatismDegree() {
                return rightAstigmatismDegree;
            }

            public void setRightAstigmatismDegree(int rightAstigmatismDegree) {
                this.rightAstigmatismDegree = rightAstigmatismDegree;
            }

            public int getAstigmatismDegree() {
                return astigmatismDegree;
            }

            public void setAstigmatismDegree(int astigmatismDegree) {
                this.astigmatismDegree = astigmatismDegree;
            }

            public int getLeftHandleDegree() {
                return leftHandleDegree;
            }

            public void setLeftHandleDegree(int leftHandleDegree) {
                this.leftHandleDegree = leftHandleDegree;
            }

            public int getRightHandleDegree() {
                return rightHandleDegree;
            }

            public void setRightHandleDegree(int rightHandleDegree) {
                this.rightHandleDegree = rightHandleDegree;
            }

            public String getRightColumnMirror() {
                return rightColumnMirror;
            }

            public void setRightColumnMirror(String rightColumnMirror) {
                this.rightColumnMirror = rightColumnMirror;
            }

            public String getLeftColumnMirror() {
                return leftColumnMirror;
            }

            public void setLeftColumnMirror(String leftColumnMirror) {
                this.leftColumnMirror = leftColumnMirror;
            }

            public int getRightAxial() {
                return rightAxial;
            }

            public void setRightAxial(int rightAxial) {
                this.rightAxial = rightAxial;
            }

            public int getLeftAxial() {
                return leftAxial;
            }

            public void setLeftAxial(int leftAxial) {
                this.leftAxial = leftAxial;
            }

            public int getNakedLeftEyeDegree() {
                return nakedLeftEyeDegree;
            }

            public void setNakedLeftEyeDegree(int nakedLeftEyeDegree) {
                this.nakedLeftEyeDegree = nakedLeftEyeDegree;
            }

            public int getNakedRightEyeDegree() {
                return nakedRightEyeDegree;
            }

            public void setNakedRightEyeDegree(int nakedRightEyeDegree) {
                this.nakedRightEyeDegree = nakedRightEyeDegree;
            }

            public int getNakedBinoculusDegree() {
                return nakedBinoculusDegree;
            }

            public void setNakedBinoculusDegree(int nakedBinoculusDegree) {
                this.nakedBinoculusDegree = nakedBinoculusDegree;
            }

            public int getCorrectLeftEyeDegree() {
                return correctLeftEyeDegree;
            }

            public void setCorrectLeftEyeDegree(int correctLeftEyeDegree) {
                this.correctLeftEyeDegree = correctLeftEyeDegree;
            }

            public int getCorrectRightEyeDegree() {
                return correctRightEyeDegree;
            }

            public void setCorrectRightEyeDegree(int correctRightEyeDegree) {
                this.correctRightEyeDegree = correctRightEyeDegree;
            }

            public int getCorrectBinoculusDegree() {
                return correctBinoculusDegree;
            }

            public void setCorrectBinoculusDegree(int correctBinoculusDegree) {
                this.correctBinoculusDegree = correctBinoculusDegree;
            }

            public int getIsNewUser() {
                return isNewUser;
            }

            public void setIsNewUser(int isNewUser) {
                this.isNewUser = isNewUser;
            }

            public int getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(int totalMoney) {
                this.totalMoney = totalMoney;
            }

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

            public String getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(String totalScore) {
                this.totalScore = totalScore;
            }

            public String getUsedScore() {
                return usedScore;
            }

            public void setUsedScore(String usedScore) {
                this.usedScore = usedScore;
            }

            public String getStatusX() {
                return statusX;
            }

            public void setStatusX(String statusX) {
                this.statusX = statusX;
            }

            public String getExpirationTime() {
                return expirationTime;
            }

            public void setExpirationTime(String expirationTime) {
                this.expirationTime = expirationTime;
            }

            public String getTrainTimeYear() {
                return trainTimeYear;
            }

            public void setTrainTimeYear(String trainTimeYear) {
                this.trainTimeYear = trainTimeYear;
            }

            public String getTrainTimeMonth() {
                return trainTimeMonth;
            }

            public void setTrainTimeMonth(String trainTimeMonth) {
                this.trainTimeMonth = trainTimeMonth;
            }

            public String getTrainTimeDay() {
                return trainTimeDay;
            }

            public void setTrainTimeDay(String trainTimeDay) {
                this.trainTimeDay = trainTimeDay;
            }

            public String getTrainTimeHour() {
                return trainTimeHour;
            }

            public void setTrainTimeHour(String trainTimeHour) {
                this.trainTimeHour = trainTimeHour;
            }

            public String getTrainTimeMinute() {
                return trainTimeMinute;
            }

            public void setTrainTimeMinute(String trainTimeMinute) {
                this.trainTimeMinute = trainTimeMinute;
            }

            public String getTrainTimeSecond() {
                return trainTimeSecond;
            }

            public void setTrainTimeSecond(String trainTimeSecond) {
                this.trainTimeSecond = trainTimeSecond;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }
        }
    }
}
