package com.zj.zhijue.bean.bledata.send;

import com.android.common.baselibrary.blebean.BaseCmdBean;
import com.android.common.baselibrary.log.MLog;

/**
 * 更新用户信息
 */
public class SendUpdateUserInfoBean2 extends BaseCmdBean {
    /**
     * 定义	序号	内容
     * 校验码	RxByte1	0xaa
     * 命令	RxByte3	0x61
     * 数据1	RxByte4	MachineData.MaxRunNumber
     * 数据2	RxByte5	MachineData.StratSpeed
     * 数据3	RxByte6	MachineData.SetSpeedInc
     * 数据4	RxByte7	MachineData.StopSpeed
     * 数据5	RxByte8	MachineData.CommonSpeed
     * 数据6	RxByte9	MachineData.MachineData6
     * 数据7	RxByte10	MachineData.MachineData7
     * 数据8	RxByte11	MachineData.MachineData8
     * 数据9	RxByte12	MachineData.MachineData9
     * 数据10	RxByte13	MachineData.SystemYearL
     * 数据11	RxByte14	MachineData.SystemYearH
     * 数据12	RxByte15	MachineData.SystemMonth
     * 数据13	RxByte16	MachineData.SystemDay
     * 数据14	RxByte17	MachineData.SystemHour
     * 数据15	RxByte18	MachineData.SystemMinute
     * 数据16	RxByte19	MachineData.SystemSecond
     * 校验码	RxByte20	0xaa
     */
    protected String[] robotReceiveBleCmdDataStr = {"aa", "61", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00",
            "00", "00", "00", "00", "00"};

    public static final String GLASSES_MACHINE_DATA_PREFIX = "aa61";

    /**
     * 用户id
     */
    private final int SYSTEM_USERID_INDEX = 2;
    /**
     * 性别
     */
    private final int SYSTEM_SEX_INDEX = 7;
    /**
     * 年龄
     */
    private final int SYSTEM_AGE_INDEX = 8;
    /**
     * 身高
     */
    private final int SYSTEM_STATURE_INDEX = 9;
    /**
     * 体重
     */
    private final int SYSTEM_KG_INDEX = 10;

    private long userID;
    private int sex;
    private int age;
    private int stature;
    private int kg;

    @Override
    protected String buildSourceData() {
        putData2DataArray();
        for (int i = 0; i < cmdDataArary.size(); i++) {
            int key = cmdDataArary.keyAt(i);
            String value = cmdDataArary.get(key);
            robotReceiveBleCmdDataStr[key] = value;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String byteStr : robotReceiveBleCmdDataStr) {
            stringBuilder.append(byteStr);
        }

        return stringBuilder.toString();
    }

    /**
     * 向 cmdDataArary 中 put 数据
     */
    @Override
    protected void putData2DataArray() {

        /**
         * 用户ID
         */
        String soureUserID = decimalismLong2Hex(String.valueOf(getUserID()), 11);
        soureUserID = soureUserID.substring(1);
        String[] soureUserID2Array = reverseStringByte(soureUserID);
        for (int i = 0; i < soureUserID2Array.length; i++) {
            cmdDataArary.put(SYSTEM_USERID_INDEX + i, soureUserID2Array[i]);
        }

        /**
         * 性别
         */
        String userSex = decimalism2Hex(getSex(), 2);
        cmdDataArary.put(SYSTEM_SEX_INDEX, userSex);

        /**
         * 年龄
         */
        String userAge = decimalism2Hex(getAge(), 2);
        cmdDataArary.put(SYSTEM_AGE_INDEX, userAge);

        /**
         * 身高
         */
        String userStature = decimalism2Hex(getStature(), 2);
        cmdDataArary.put(SYSTEM_STATURE_INDEX, userStature);

        /**
         * 体重
         */
        String userKG = decimalism2Hex(getKg(), 2);
        cmdDataArary.put(SYSTEM_KG_INDEX, userKG);

    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
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

    public int getStature() {
        return stature;
    }

    public void setStature(int stature) {
        this.stature = stature;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }
}
