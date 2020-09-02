package com.zj.zhijue.greendao.greendaobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

@Entity(nameInDb = "userinfodb_tab")
public class UserInfoDBBean {

    @Property(nameInDb = "localid")
    private String localid;

    @Unique
    @Property(nameInDb = "serverId")
    private String serverId;

    @Property(nameInDb = "accountrole")
    private String accountRole;//账号角色，普通用户/管理员

    @Property(nameInDb = "guardian_id")
    private String guardian_id;//监护人ID

    @Property(nameInDb = "authorType") //使用者，监护人
    private int authorType;

    @Property(nameInDb = "login_name")
    private String login_name;//登录账号

    @Property(nameInDb = "nickname")
    private String nickname;//昵称

    @Property(nameInDb = "name")
    private String name;//姓名

    @Property(nameInDb = "diopter_state")
    private int diopter_state;//	屈光状态类型（0，近视；1，远视；2，老花；3，弱视；4,其他）   基础	1	老花	2	远视	3	弱视	4

    @Property(nameInDb = "left_eye_degree")
    private float left_eye_degree;//左眼屈光度数

    @Property(nameInDb = "right_eye_degree")
    private float right_eye_degree;//右眼屈光度数

    @Property(nameInDb = "left_front_com_degree")
    private float left_front_com_degree;//左前补偿度数

    @Property(nameInDb = "right_front_com_degree")
    private float right_front_com_degree;//右前补偿度数

    @Property(nameInDb = "interpupillary")
    private float interpupillary;//瞳距

    @Property(nameInDb = "device_id")
    private String device_id;//设备id

    @Property(nameInDb = "correct_left_eye_degree")
    private String correct_left_eye_degree;//string	左眼最佳矫正视力

    @Property(nameInDb = "correct_right_eye_degree")
    private String correct_right_eye_degree;//string 右眼最佳矫正视力

    @Property(nameInDb = "correct_binoculus_degree")
    private String correct_binoculus_degree;//string 双眼最佳矫正视力

    @Property(nameInDb = "naked_left_eye_degree")
    private String naked_left_eye_degree;//string 左眼裸眼视力

    @Property(nameInDb = "naked_right_eye_degree")
    private String naked_right_eye_degree;//string 右眼裸眼视力

    @Property(nameInDb = "naked_binoculus_degree")
    private String naked_binoculus_degree;//string	双眼裸眼视力

    @Property(nameInDb = "age")
    private int age;

    @Property(nameInDb = "born_date")
    private String born_date;

    @Property(nameInDb = "credentials_card")
    private String credentials_card;

    @Property(nameInDb = "credentials_type")
    private int credentials_type;//认证类型

    @Property(nameInDb = "expiration_time")
    private String expiration_time;//到期时间+

    @Property(nameInDb = "left_front_coefficient")
    private String left_front_coefficient;

    @Property(nameInDb = "phone")
    private String phone;

    @Property(nameInDb = "areaCode")
    private String areaCode;

    @Property(nameInDb = "right_after_coefficient")
    private String right_after_coefficient;

    @Property(nameInDb = "right_front_coefficient")
    private String right_front_coefficient;

    @Property(nameInDb = "sex")
    private int sex;

    @Property(nameInDb = "status")
    private int status;//	int	状态0审核中 1审核通过 2审核不通过

    @Property(nameInDb = "province")
    private String province;

    @Property(nameInDb = "city")
    private String city;

    @Property(nameInDb = "area")
    private String area;

    @Property(nameInDb = "common_address")
    private String common_address;

    @Property(nameInDb = "binding_time")
    private String binding_time;

    @Property(nameInDb = "left_eye_train_degree")
    private double left_eye_train_degree;

    @Property(nameInDb = "right_eye_train_degree")
    private  double right_eye_train_degree;

    @Property(nameInDb = "left_astigmatism_degree")
    private float left_astigmatism_degree;//";//	是	float	左眼散光度

    @Property(nameInDb = "right_astigmatism_degree")
    private float right_astigmatism_degree;//

    @Property(nameInDb = "portrait_image_url")
    private String portrait_image_url;

    @Property(nameInDb = "right_axial")
    private float right_axial;//	float	右轴向

    @Property(nameInDb = "left_axial")
    private float left_axial;//	float	左轴向

    @Property(nameInDb = "isnewuser")
    private boolean isnewuser;//	bool	是否新用户

    @Property(nameInDb = "total_money")
    private float total_money;//	float	总充值金额

    @Property(nameInDb = "used_money")
    private float used_money;//	float	已使用金额

    @Property(nameInDb = "total_time")
    private float total_time;//	float	总累计小时数

    @Property(nameInDb = "used_time")
    private float used_time;//float	已使用小时数

    @Property(nameInDb = "total_score")
    private float total_score;//	float	累计总积分

    @Property(nameInDb = "used_score")
    private float used_score;//	float	已使用积分

    @Property(nameInDb = "trainTimeYear")
    private float trainTimeYear;

    @Property(nameInDb = "trainTimeMonth")
    private float trainTimeMonth;

    @Property(nameInDb = "trainTimeDay")
    private float trainTimeDay;

    @Property(nameInDb = "trainTimeHour")
    private float trainTimeHour;

    @Property(nameInDb = "trainTimeMinute")
    private float trainTimeMinute;

    @Property(nameInDb = "trainTimeSecond")
    private float trainTimeSecond;

    @Property(nameInDb = "reservedStr0")
    private String reservedStr0;

    @Property(nameInDb = "reservedStr1")
    private String reservedStr1;

    @Property(nameInDb = "reservedStr2")
    private String reservedStr2;

    @Property(nameInDb = "reservedStr3")
    private String reservedStr3;

    @Property(nameInDb = "reservedInt0")
    private int reservedInt0;

    @Property(nameInDb = "reservedInt1")
    private int reservedInt1;

    @Property(nameInDb = "reservedLong0")
    private long reservedLong0;

    @Property(nameInDb = "reservedLong1")
    private long reservedLong1;

    @Property(nameInDb = "reservedDouble0")
    private double reservedDouble0;

    @Property(nameInDb = "reservedDouble1")
    private double reservedDouble1;


    @Generated(hash = 146950611)
    public UserInfoDBBean(String localid, String serverId, String accountRole,
            String guardian_id, int authorType, String login_name, String nickname,
            String name, int diopter_state, float left_eye_degree, float right_eye_degree,
            float left_front_com_degree, float right_front_com_degree, float interpupillary,
            String device_id, String correct_left_eye_degree, String correct_right_eye_degree,
            String correct_binoculus_degree, String naked_left_eye_degree,
            String naked_right_eye_degree, String naked_binoculus_degree, int age,
            String born_date, String credentials_card, int credentials_type,
            String expiration_time, String left_front_coefficient, String phone,
            String areaCode, String right_after_coefficient, String right_front_coefficient,
            int sex, int status, String province, String city, String area,
            String common_address, String binding_time, double left_eye_train_degree,
            double right_eye_train_degree, float left_astigmatism_degree,
            float right_astigmatism_degree, String portrait_image_url, float right_axial,
            float left_axial, boolean isnewuser, float total_money, float used_money,
            float total_time, float used_time, float total_score, float used_score,
            float trainTimeYear, float trainTimeMonth, float trainTimeDay,
            float trainTimeHour, float trainTimeMinute, float trainTimeSecond,
            String reservedStr0, String reservedStr1, String reservedStr2,
            String reservedStr3, int reservedInt0, int reservedInt1, long reservedLong0,
            long reservedLong1, double reservedDouble0, double reservedDouble1) {
        this.localid = localid;
        this.serverId = serverId;
        this.accountRole = accountRole;
        this.guardian_id = guardian_id;
        this.authorType = authorType;
        this.login_name = login_name;
        this.nickname = nickname;
        this.name = name;
        this.diopter_state = diopter_state;
        this.left_eye_degree = left_eye_degree;
        this.right_eye_degree = right_eye_degree;
        this.left_front_com_degree = left_front_com_degree;
        this.right_front_com_degree = right_front_com_degree;
        this.interpupillary = interpupillary;
        this.device_id = device_id;
        this.correct_left_eye_degree = correct_left_eye_degree;
        this.correct_right_eye_degree = correct_right_eye_degree;
        this.correct_binoculus_degree = correct_binoculus_degree;
        this.naked_left_eye_degree = naked_left_eye_degree;
        this.naked_right_eye_degree = naked_right_eye_degree;
        this.naked_binoculus_degree = naked_binoculus_degree;
        this.age = age;
        this.born_date = born_date;
        this.credentials_card = credentials_card;
        this.credentials_type = credentials_type;
        this.expiration_time = expiration_time;
        this.left_front_coefficient = left_front_coefficient;
        this.phone = phone;
        this.areaCode = areaCode;
        this.right_after_coefficient = right_after_coefficient;
        this.right_front_coefficient = right_front_coefficient;
        this.sex = sex;
        this.status = status;
        this.province = province;
        this.city = city;
        this.area = area;
        this.common_address = common_address;
        this.binding_time = binding_time;
        this.left_eye_train_degree = left_eye_train_degree;
        this.right_eye_train_degree = right_eye_train_degree;
        this.left_astigmatism_degree = left_astigmatism_degree;
        this.right_astigmatism_degree = right_astigmatism_degree;
        this.portrait_image_url = portrait_image_url;
        this.right_axial = right_axial;
        this.left_axial = left_axial;
        this.isnewuser = isnewuser;
        this.total_money = total_money;
        this.used_money = used_money;
        this.total_time = total_time;
        this.used_time = used_time;
        this.total_score = total_score;
        this.used_score = used_score;
        this.trainTimeYear = trainTimeYear;
        this.trainTimeMonth = trainTimeMonth;
        this.trainTimeDay = trainTimeDay;
        this.trainTimeHour = trainTimeHour;
        this.trainTimeMinute = trainTimeMinute;
        this.trainTimeSecond = trainTimeSecond;
        this.reservedStr0 = reservedStr0;
        this.reservedStr1 = reservedStr1;
        this.reservedStr2 = reservedStr2;
        this.reservedStr3 = reservedStr3;
        this.reservedInt0 = reservedInt0;
        this.reservedInt1 = reservedInt1;
        this.reservedLong0 = reservedLong0;
        this.reservedLong1 = reservedLong1;
        this.reservedDouble0 = reservedDouble0;
        this.reservedDouble1 = reservedDouble1;
    }

    @Generated(hash = 118957667)
    public UserInfoDBBean() {
    }

    public String getLocalid() {
        return this.localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

    public String getServerId() {
        return this.serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getAccountRole() {
        return this.accountRole;
    }

    public void setAccountRole(String accountRole) {
        this.accountRole = accountRole;
    }

    public String getGuardian_id() {
        return this.guardian_id;
    }

    public void setGuardian_id(String guardian_id) {
        this.guardian_id = guardian_id;
    }

    public int getAuthorType() {
        return this.authorType;
    }

    public void setAuthorType(int authorType) {
        this.authorType = authorType;
    }

    public String getLogin_name() {
        return this.login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiopter_state() {
        return this.diopter_state;
    }

    public void setDiopter_state(int diopter_state) {
        this.diopter_state = diopter_state;
    }

    public float getLeft_eye_degree() {
        return this.left_eye_degree;
    }

    public void setLeft_eye_degree(float left_eye_degree) {
        this.left_eye_degree = left_eye_degree;
    }

    public float getRight_eye_degree() {
        return this.right_eye_degree;
    }

    public void setRight_eye_degree(float right_eye_degree) {
        this.right_eye_degree = right_eye_degree;
    }

    public float getLeft_front_com_degree() {
        return this.left_front_com_degree;
    }

    public void setLeft_front_com_degree(float left_front_com_degree) {
        this.left_front_com_degree = left_front_com_degree;
    }

    public float getRight_front_com_degree() {
        return this.right_front_com_degree;
    }

    public void setRight_front_com_degree(float right_front_com_degree) {
        this.right_front_com_degree = right_front_com_degree;
    }

    public float getInterpupillary() {
        return this.interpupillary;
    }

    public void setInterpupillary(float interpupillary) {
        this.interpupillary = interpupillary;
    }

    public String getDevice_id() {
        return this.device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCorrect_left_eye_degree() {
        return this.correct_left_eye_degree;
    }

    public void setCorrect_left_eye_degree(String correct_left_eye_degree) {
        this.correct_left_eye_degree = correct_left_eye_degree;
    }

    public String getCorrect_right_eye_degree() {
        return this.correct_right_eye_degree;
    }

    public void setCorrect_right_eye_degree(String correct_right_eye_degree) {
        this.correct_right_eye_degree = correct_right_eye_degree;
    }

    public String getCorrect_binoculus_degree() {
        return this.correct_binoculus_degree;
    }

    public void setCorrect_binoculus_degree(String correct_binoculus_degree) {
        this.correct_binoculus_degree = correct_binoculus_degree;
    }

    public String getNaked_left_eye_degree() {
        return this.naked_left_eye_degree;
    }

    public void setNaked_left_eye_degree(String naked_left_eye_degree) {
        this.naked_left_eye_degree = naked_left_eye_degree;
    }

    public String getNaked_right_eye_degree() {
        return this.naked_right_eye_degree;
    }

    public void setNaked_right_eye_degree(String naked_right_eye_degree) {
        this.naked_right_eye_degree = naked_right_eye_degree;
    }

    public String getNaked_binoculus_degree() {
        return this.naked_binoculus_degree;
    }

    public void setNaked_binoculus_degree(String naked_binoculus_degree) {
        this.naked_binoculus_degree = naked_binoculus_degree;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBorn_date() {
        return this.born_date;
    }

    public void setBorn_date(String born_date) {
        this.born_date = born_date;
    }

    public String getCredentials_card() {
        return this.credentials_card;
    }

    public void setCredentials_card(String credentials_card) {
        this.credentials_card = credentials_card;
    }

    public int getCredentials_type() {
        return this.credentials_type;
    }

    public void setCredentials_type(int credentials_type) {
        this.credentials_type = credentials_type;
    }

    public String getExpiration_time() {
        return this.expiration_time;
    }

    public void setExpiration_time(String expiration_time) {
        this.expiration_time = expiration_time;
    }

    public String getLeft_front_coefficient() {
        return this.left_front_coefficient;
    }

    public void setLeft_front_coefficient(String left_front_coefficient) {
        this.left_front_coefficient = left_front_coefficient;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getRight_after_coefficient() {
        return this.right_after_coefficient;
    }

    public void setRight_after_coefficient(String right_after_coefficient) {
        this.right_after_coefficient = right_after_coefficient;
    }

    public String getRight_front_coefficient() {
        return this.right_front_coefficient;
    }

    public void setRight_front_coefficient(String right_front_coefficient) {
        this.right_front_coefficient = right_front_coefficient;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCommon_address() {
        return this.common_address;
    }

    public void setCommon_address(String common_address) {
        this.common_address = common_address;
    }

    public String getBinding_time() {
        return this.binding_time;
    }

    public void setBinding_time(String binding_time) {
        this.binding_time = binding_time;
    }

    public double getLeft_eye_train_degree() {
        return this.left_eye_train_degree;
    }

    public void setLeft_eye_train_degree(double left_eye_train_degree) {
        this.left_eye_train_degree = left_eye_train_degree;
    }

    public double getRight_eye_train_degree() {
        return this.right_eye_train_degree;
    }

    public void setRight_eye_train_degree(double right_eye_train_degree) {
        this.right_eye_train_degree = right_eye_train_degree;
    }

    public float getLeft_astigmatism_degree() {
        return this.left_astigmatism_degree;
    }

    public void setLeft_astigmatism_degree(float left_astigmatism_degree) {
        this.left_astigmatism_degree = left_astigmatism_degree;
    }

    public float getRight_astigmatism_degree() {
        return this.right_astigmatism_degree;
    }

    public void setRight_astigmatism_degree(float right_astigmatism_degree) {
        this.right_astigmatism_degree = right_astigmatism_degree;
    }

    public String getPortrait_image_url() {
        return this.portrait_image_url;
    }

    public void setPortrait_image_url(String portrait_image_url) {
        this.portrait_image_url = portrait_image_url;
    }

    public float getRight_axial() {
        return this.right_axial;
    }

    public void setRight_axial(float right_axial) {
        this.right_axial = right_axial;
    }

    public float getLeft_axial() {
        return this.left_axial;
    }

    public void setLeft_axial(float left_axial) {
        this.left_axial = left_axial;
    }

    public boolean getIsnewuser() {
        return this.isnewuser;
    }

    public void setIsnewuser(boolean isnewuser) {
        this.isnewuser = isnewuser;
    }

    public float getTotal_money() {
        return this.total_money;
    }

    public void setTotal_money(float total_money) {
        this.total_money = total_money;
    }

    public float getUsed_money() {
        return this.used_money;
    }

    public void setUsed_money(float used_money) {
        this.used_money = used_money;
    }

    public float getTotal_time() {
        return this.total_time;
    }

    public void setTotal_time(float total_time) {
        this.total_time = total_time;
    }

    public float getUsed_time() {
        return this.used_time;
    }

    public void setUsed_time(float used_time) {
        this.used_time = used_time;
    }

    public float getTotal_score() {
        return this.total_score;
    }

    public void setTotal_score(float total_score) {
        this.total_score = total_score;
    }

    public float getUsed_score() {
        return this.used_score;
    }

    public void setUsed_score(float used_score) {
        this.used_score = used_score;
    }

    public float getTrainTimeYear() {
        return this.trainTimeYear;
    }

    public void setTrainTimeYear(float trainTimeYear) {
        this.trainTimeYear = trainTimeYear;
    }

    public float getTrainTimeMonth() {
        return this.trainTimeMonth;
    }

    public void setTrainTimeMonth(float trainTimeMonth) {
        this.trainTimeMonth = trainTimeMonth;
    }

    public float getTrainTimeDay() {
        return this.trainTimeDay;
    }

    public void setTrainTimeDay(float trainTimeDay) {
        this.trainTimeDay = trainTimeDay;
    }

    public float getTrainTimeHour() {
        return this.trainTimeHour;
    }

    public void setTrainTimeHour(float trainTimeHour) {
        this.trainTimeHour = trainTimeHour;
    }

    public float getTrainTimeMinute() {
        return this.trainTimeMinute;
    }

    public void setTrainTimeMinute(float trainTimeMinute) {
        this.trainTimeMinute = trainTimeMinute;
    }

    public float getTrainTimeSecond() {
        return this.trainTimeSecond;
    }

    public void setTrainTimeSecond(float trainTimeSecond) {
        this.trainTimeSecond = trainTimeSecond;
    }

    public String getReservedStr0() {
        return this.reservedStr0;
    }

    public void setReservedStr0(String reservedStr0) {
        this.reservedStr0 = reservedStr0;
    }

    public String getReservedStr1() {
        return this.reservedStr1;
    }

    public void setReservedStr1(String reservedStr1) {
        this.reservedStr1 = reservedStr1;
    }

    public String getReservedStr2() {
        return this.reservedStr2;
    }

    public void setReservedStr2(String reservedStr2) {
        this.reservedStr2 = reservedStr2;
    }

    public String getReservedStr3() {
        return this.reservedStr3;
    }

    public void setReservedStr3(String reservedStr3) {
        this.reservedStr3 = reservedStr3;
    }

    public int getReservedInt0() {
        return this.reservedInt0;
    }

    public void setReservedInt0(int reservedInt0) {
        this.reservedInt0 = reservedInt0;
    }

    public int getReservedInt1() {
        return this.reservedInt1;
    }

    public void setReservedInt1(int reservedInt1) {
        this.reservedInt1 = reservedInt1;
    }

    public long getReservedLong0() {
        return this.reservedLong0;
    }

    public void setReservedLong0(long reservedLong0) {
        this.reservedLong0 = reservedLong0;
    }

    public long getReservedLong1() {
        return this.reservedLong1;
    }

    public void setReservedLong1(long reservedLong1) {
        this.reservedLong1 = reservedLong1;
    }

    public double getReservedDouble0() {
        return this.reservedDouble0;
    }

    public void setReservedDouble0(double reservedDouble0) {
        this.reservedDouble0 = reservedDouble0;
    }

    public double getReservedDouble1() {
        return this.reservedDouble1;
    }

    public void setReservedDouble1(double reservedDouble1) {
        this.reservedDouble1 = reservedDouble1;
    }


}
