package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;

public class SendUserInfoBleCmdBean extends BaseCmdBean {

    /**
     定义	序号	内容
     校验码	RxByte1	0xaa
     命令	RxByte3	0x15
     数据1	RxByte4	CurrentUser.Code1
     数据2	RxByte5	CurrentUser.Code2
     数据3	RxByte6	CurrentUser.Code3
     数据4	RxByte7	CurrentUser.Code4
     数据5	RxByte8	CurrentUser.Code5
     数据6	RxByte9	CurrentUser.Age
     数据7	RxByte10	CurrentUser.Type 基础	1	老花	2	远视	3	弱视	4
     数据8	RxByte11	CurrentUser.LeftDegree%256
     数据9	RxByte12	CurrentUser.LeftDegree/256
     数据10	RxByte13	CurrentUser.RightDegree%256
     数据11	RxByte14	CurrentUser.RightDegree/256
     数据12	RxByte15	CurrentUser.RunMode		训练模式	1	矫正模式	2	安全模式	3
     数据13	RxByte16	CurrentUser.Lens 	镜片1	1	镜片2	2	镜片3	3
     数据14	RxByte17	CurrentUser.NewUser
     数据15	RxByte18	CurrentUser.UserData3
     数据16	RxByte19	CurrentUser.UserData4
     校验码	RxByte20	0xaa
     */
    protected String[] userDataBleCmdDataStr = {"aa", "61", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "aa"};

    public static final String USER_DATA_PREFIX = "aa61";

    /**
     * 下标索引从 0 开始
     */
    private final int USER_ID_INDEX_LOW = 2;//（用户ID5个字节)


    private final int USER_AGE_INDEX = 8;//年龄索引

    private final int USER_EYE_SIGHT_TYPE_INDEX = 9;//视力类型索引 近视1 老花2 远视3 弱视4

    private final int USER_LEFT_EYE_SIGHT_DEGREE_INDEX_LOW = 10;//用户左眼视力度数 低位
    private final int USER_LEFT_EYE_SIGHT_DEGREE_INDEX_HIGH = 11;//用户左眼视力度数 高位
    private final int USER_RIGHT_EYE_SIGHT_DEGREE_INDEX_LOW = 12;//用户右眼视力度数 低位
    private final int USER_RIGHT_EYE_SIGHT_DEGREE_INDEX_HIGH = 13;//用户右眼视力度数 高位

    private final int USER_TRAIN_MODE_IDNEX = 14;//用户训练模式 ，训练模式1， 矫正模式2， 安全模式3

    private final int CURRENT_USER_LENS_INDEX = 15;//
    private final int CURRENT_USER_NEW_USER_INDEX = 16;
    private final int CURRENT_USER_USER_DATA_3_INDEX = 17;
    private final int CURRENT_USER_USER_DATA_4_INDEX = 18;

    public static final int TRAIN_MODE = 1;//训练模式
    public static final int CORRECT_MODE = 2;//矫正模式
    public static final int SAFETY_MODE = 3;//安全模式

    private long userId;
    private int userAge;
    private int userEyeSightType;
    private int userLeftEyeSightDegree;
    private int userRightSightDegree;
    private int userTrainMode;

    private int userLens;
    private int newUser;


    @Override
    protected String buildSourceData() {
        putData2DataArray();
        for (int i = 0; i < cmdDataArary.size(); i++) {
            int key = cmdDataArary.keyAt(i);
            String value = cmdDataArary.get(key);
            userDataBleCmdDataStr[key] = value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String byteStr : userDataBleCmdDataStr) {
            stringBuilder.append(byteStr);
        }

        return stringBuilder.toString();
    }

    @Override
    protected void putData2DataArray() {
        /**
         * 用户ID
         */
        String soureUserID = decimalismLong2Hex(String.valueOf(getUserId()), 10);
        String[] userIdArray = reverseStringByte(soureUserID);

        for(int i =0; i < userIdArray.length; i++) {
            cmdDataArary.put(USER_ID_INDEX_LOW + i, userIdArray[i]);
        }

        /**
         * 年龄
         */
        String userAge = decimalism2Hex(getUserAge(), 2);
        cmdDataArary.put(USER_AGE_INDEX, userAge);

        /**
         * 视力类型
         */
        String userEyeSightType = decimalism2Hex(getUserEyeSightType(), 2);
        cmdDataArary.put(USER_EYE_SIGHT_TYPE_INDEX, userEyeSightType);

        /**
         * 用户左眼视力度数
         */
        String userLeftEyeSightDegree = decimalism2Hex(getUserLeftEyeSightDegree(), 4);
        String[] userLeftEyeSightDegreeArray = reverseStringByte(userLeftEyeSightDegree);
        cmdDataArary.put(USER_LEFT_EYE_SIGHT_DEGREE_INDEX_LOW, userLeftEyeSightDegreeArray[0]);
        cmdDataArary.put(USER_LEFT_EYE_SIGHT_DEGREE_INDEX_HIGH, userLeftEyeSightDegreeArray[1]);

        /**
         * 用户右眼视力度数
         */
        String userRightEyeSightDegree = decimalism2Hex(getUserRightSightDegree(), 4);
        String[] userRightEyeSightDegreeArray = reverseStringByte(userRightEyeSightDegree);
        cmdDataArary.put(USER_RIGHT_EYE_SIGHT_DEGREE_INDEX_LOW, userRightEyeSightDegreeArray[0]);
        cmdDataArary.put(USER_RIGHT_EYE_SIGHT_DEGREE_INDEX_HIGH, userRightEyeSightDegreeArray[1]);

        /**
         * 训练模式
         */
        String userTrainMode = decimalism2Hex(getUserTrainMode(), 2);
        cmdDataArary.put(USER_TRAIN_MODE_IDNEX, userTrainMode);

        String userLens = decimalism2Hex(getUserLens(), 2);
        cmdDataArary.put(CURRENT_USER_LENS_INDEX, userLens);

        /**
         * 是否新用户（第一次使用）
         */
        String newUser = decimalism2Hex(getNewUser(), 2);
        cmdDataArary.put(CURRENT_USER_NEW_USER_INDEX, newUser);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getUserEyeSightType() {
        return userEyeSightType;
    }

    public void setUserEyeSightType(int userEyeSightType) {
        this.userEyeSightType = userEyeSightType;
    }

    public int getUserLeftEyeSightDegree() {
        return userLeftEyeSightDegree;
    }

    public void setUserLeftEyeSightDegree(int userLeftEyeSightDegree) {
        this.userLeftEyeSightDegree = userLeftEyeSightDegree;
    }

    public int getUserRightSightDegree() {
        return userRightSightDegree;
    }

    public void setUserRightSightDegree(int userRightSightDegree) {
        this.userRightSightDegree = userRightSightDegree;
    }

    public int getUserTrainMode() {
        return userTrainMode;
    }

    public void setUserTrainMode(int userTrainMode) {
        this.userTrainMode = userTrainMode;
    }

    public int getUserLens() {
        return userLens;
    }

    public void setUserLens(int userLens) {
        this.userLens = userLens;
    }

    public int getNewUser() {
        return newUser;
    }

    public void setNewUser(int newUser) {
        this.newUser = newUser;
    }

    @Override
    public String toString() {
        return "SendUserInfoBleCmdBean{" + "\n" +
                "userId=" + userId + "\n" +
                ", userAge=" + userAge + "\n" +
                ", userEyeSightType=" + userEyeSightType + "\n" +
                ", userLeftEyeSightDegree=" + userLeftEyeSightDegree + "\n" +
                ", userRightSightDegree=" + userRightSightDegree + "\n" +
                ", userTrainMode=" + userTrainMode + "\n" +
                ", userLens=" + userLens + "\n" +
                ", newUser=" + newUser + "\n" +
                '}';
    }
}
