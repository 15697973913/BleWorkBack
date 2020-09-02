package com.zj.zhijue.bean;

import androidx.annotation.Keep;

/**
 * 登录
 */
@Keep
public class MemberLoginResponseBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    /**
     * status	string	状态（详见状态字典）
     * data	Object	返回对象内容
     * id	string	用户ID
     * guardian_id	string	监护人ID
     * login_name	string	登录账号
     * nickname	string	昵称
     * name	string	姓名
     * age	string	年龄
     * sex	int	性别
     * born_date	date	出生年月
     * credentials_type	int	证件类型 0身份证、1护照
     * credentials_card	string	证件号
     * phone	string	手机号
     * diopter_state	int	屈光状态类型（0，近视；1，远视；2，老花；3，弱视；4,其他）
     * left_eye_degree	float	左眼屈光度数
     * right_eye_degree	float	右眼屈光度数
     * interpupillary	float	瞳距
     * left_astigmatism_degree	float	左眼散光度
     * right_astigmatism_degree	float	右眼散光度
     * astigmatism_degree	float	双眼散光度
     * left_handle_degree	float	左眼屈光度数（处理后）
     * right_handle_degree	float	右眼屈光度数（处理后）
     * right_eye_degree	float	右眼屈光度数
     * left_front_com_degree	float	左前补偿度数
     * right_front_com_degree	float	右前补偿度数
     * right_column_mirror	float	右柱镜
     * left_column_mirror	float	左柱镜
     * right_axial	float	右轴向
     * left_axial	float	左轴向
     * naked_left_eye_degree	string	左眼裸眼视力
     * naked_right_eye_degree	string	右眼裸眼视力
     * naked_binoculus_degree	string	双眼裸眼视力
     * correct_left_eye_degree	string	左眼最佳矫正视力
     * correct_right_eye_degree	string	右眼最佳矫正视力
     * correct_binoculus_degree	string	双眼最佳矫正视力
     * isnewuser	bool	是否新用户
     * total_money	float	总充值金额
     * used_money	float	已使用金额
     * total_time	float	总累计小时数
     * used_time	float	已使用小时数
     * total_score	float	累计总积分
     * used_score	float	已使用积分
     * status	int	状态0审核中 1审核通过 2审核不通过
     * expiration_time	date	到期时间
     * face	string	头像连接
     * message	string	请求提示信息
     * cursor	string	分页信息
     */

    @Keep
    public class DataBean {
        private String id; //用户ID 以前的废弃了
        private String memberId;//用户ID
        private String guardian_id;//监护人ID
        private String login_name;//登录账号
        private String nickname;//昵称
        private String name;//姓名
        private int age;
        private int sex;
        private String born_date;
        private int credentials_type;//	int	证件类型 0身份证、1护照
        private String credentials_card;//证件号
        private String areaCode;
        private String phone;
        private int diopter_state;//int	屈光状态类型（0，近视；1，远视；2，老花；3，弱视；4,其他）

        private boolean hasEyeData;//是否已经填写数据
        private String access_token;
        private String refresh_token;

        private float left_eye_degree;//	float	左眼屈光度数
        private float right_eye_degree;//	float	右眼屈光度数
        private float interpupillary;//	float	瞳距
        private float left_astigmatism_degree;//	float	左眼散光度
        private float right_astigmatism_degree;//	float	右眼散光度
        private float astigmatism_degree;//	float	双眼散光度
        private float left_handle_degree;//	float	左眼屈光度数（处理后）
        private float right_handle_degree;//	float	右眼屈光度数（处理后）
        private float left_front_com_degree;//	float	左前补偿度数
        private float right_front_com_degree;//	float	右前补偿度数
        private float right_column_mirror;//	float;	右柱镜
        private float left_column_mirror;//	float	左柱镜
        private float right_axial;//	float	右轴向
        private float left_axial;//	float	左轴向
        private String naked_left_eye_degree;//	string	左眼裸眼视力
        private String naked_right_eye_degree;//	string	右眼裸眼视力
        private String naked_binoculus_degree;//	string	双眼裸眼视力
        private String correct_left_eye_degree;//	string	左眼最佳矫正视力
        private String correct_right_eye_degree;//	string	右眼最佳矫正视力
        private String correct_binoculus_degree;//	string	双眼最佳矫正视力
        private boolean isnewuser;//	bool	是否新用户
        private float total_money;//	float	总充值金额
        private float used_money;//	float	已使用金额
        private float total_time;//	float	总累计小时数
        private float used_time;//float	已使用小时数
        private float total_score;//	float	累计总积分
        private float used_score;//	float	已使用积分
        private int status;//	int	状态0审核中 1审核通过 2审核不通过
        private String expiration_time;//	date	到期时间
        private String face;//	string	头像连接
        private float trainTimeYear;
        private float trainTimeMonth;
        private float trainTimeDay;
        private float trainTimeHour;
        private float trainTimeMinute;
        private float trainTimeSecond;

        private String message;//	string	请求提示信息
        private String cursor;//	string	分页信息

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public boolean isHasEyeData() {
            return hasEyeData;
        }

        public void setHasEyeData(boolean hasEyeData) {
            this.hasEyeData = hasEyeData;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getId() {
            return id == null || id.isEmpty() ? memberId : id;
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

        public String getLogin_name() {
            return login_name;
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
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

        public int getDiopter_state() {
            return diopter_state;
        }

        public void setDiopter_state(int diopter_state) {
            this.diopter_state = diopter_state;
        }

        public float getLeft_eye_degree() {
            return left_eye_degree;
        }

        public void setLeft_eye_degree(float left_eye_degree) {
            this.left_eye_degree = left_eye_degree;
        }

        public float getRight_eye_degree() {
            return right_eye_degree;
        }

        public void setRight_eye_degree(float right_eye_degree) {
            this.right_eye_degree = right_eye_degree;
        }

        public float getLeft_front_com_degree() {
            return left_front_com_degree;
        }

        public void setLeft_front_com_degree(float left_front_com_degree) {
            this.left_front_com_degree = left_front_com_degree;
        }

        public float getRight_front_com_degree() {
            return right_front_com_degree;
        }

        public void setRight_front_com_degree(float right_front_com_degree) {
            this.right_front_com_degree = right_front_com_degree;
        }

        public float getInterpupillary() {
            return interpupillary;
        }

        public void setInterpupillary(float interpupillary) {
            this.interpupillary = interpupillary;
        }

        public String getCorrect_left_eye_degree() {
            return correct_left_eye_degree;
        }

        public void setCorrect_left_eye_degree(String correct_left_eye_degree) {
            this.correct_left_eye_degree = correct_left_eye_degree;
        }

        public String getCorrect_right_eye_degree() {
            return correct_right_eye_degree;
        }

        public void setCorrect_right_eye_degree(String correct_right_eye_degree) {
            this.correct_right_eye_degree = correct_right_eye_degree;
        }

        public String getCorrect_binoculus_degree() {
            return correct_binoculus_degree;
        }

        public void setCorrect_binoculus_degree(String correct_binoculus_degree) {
            this.correct_binoculus_degree = correct_binoculus_degree;
        }

        public String getNaked_left_eye_degree() {
            return naked_left_eye_degree;
        }

        public void setNaked_left_eye_degree(String naked_left_eye_degree) {
            this.naked_left_eye_degree = naked_left_eye_degree;
        }

        public String getNaked_right_eye_degree() {
            return naked_right_eye_degree;
        }

        public void setNaked_right_eye_degree(String naked_right_eye_degree) {
            this.naked_right_eye_degree = naked_right_eye_degree;
        }

        public String getNaked_binoculus_degree() {
            return naked_binoculus_degree;
        }

        public void setNaked_binoculus_degree(String naked_binoculus_degree) {
            this.naked_binoculus_degree = naked_binoculus_degree;
        }


        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getBorn_date() {
            return born_date;
        }

        public void setBorn_date(String born_date) {
            this.born_date = born_date;
        }

        public String getCredentials_card() {
            return credentials_card;
        }

        public void setCredentials_card(String credentials_card) {
            this.credentials_card = credentials_card;
        }

        public int getCredentials_type() {
            return credentials_type;
        }

        public void setCredentials_type(int credentials_type) {
            this.credentials_type = credentials_type;
        }

        public String getExpiration_time() {
            return expiration_time;
        }

        public void setExpiration_time(String expiration_time) {
            this.expiration_time = expiration_time;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public float getLeft_handle_degree() {
            return left_handle_degree;
        }

        public void setLeft_handle_degree(float left_handle_degree) {
            this.left_handle_degree = left_handle_degree;
        }

        public float getRight_handle_degree() {
            return right_handle_degree;
        }

        public void setRight_handle_degree(float right_handle_degree) {
            this.right_handle_degree = right_handle_degree;
        }

        public float getLeft_astigmatism_degree() {
            return left_astigmatism_degree;
        }

        public void setLeft_astigmatism_degree(float left_astigmatism_degree) {
            this.left_astigmatism_degree = left_astigmatism_degree;
        }

        public float getRight_astigmatism_degree() {
            return right_astigmatism_degree;
        }

        public void setRight_astigmatism_degree(float right_astigmatism_degree) {
            this.right_astigmatism_degree = right_astigmatism_degree;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }


        public float getRight_axial() {
            return right_axial;
        }

        public void setRight_axial(float right_axial) {
            this.right_axial = right_axial;
        }

        public float getLeft_axial() {
            return left_axial;
        }

        public void setLeft_axial(float left_axial) {
            this.left_axial = left_axial;
        }

        public boolean isIsnewuser() {
            return isnewuser;
        }

        public void setIsnewuser(boolean isnewuser) {
            this.isnewuser = isnewuser;
        }

        public float getTotal_money() {
            return total_money;
        }

        public void setTotal_money(float total_money) {
            this.total_money = total_money;
        }

        public float getUsed_money() {
            return used_money;
        }

        public void setUsed_money(float used_money) {
            this.used_money = used_money;
        }

        public float getTotal_time() {
            return total_time;
        }

        public void setTotal_time(float total_time) {
            this.total_time = total_time;
        }

        public float getUsed_time() {
            return used_time;
        }

        public void setUsed_time(float used_time) {
            this.used_time = used_time;
        }

        public float getTotal_score() {
            return total_score;
        }

        public void setTotal_score(float total_score) {
            this.total_score = total_score;
        }

        public float getUsed_score() {
            return used_score;
        }

        public void setUsed_score(float used_score) {
            this.used_score = used_score;
        }

        public float getTrainTimeYear() {
            return trainTimeYear;
        }

        public void setTrainTimeYear(float trainTimeYear) {
            this.trainTimeYear = trainTimeYear;
        }

        public float getTrainTimeMonth() {
            return trainTimeMonth;
        }

        public void setTrainTimeMonth(float trainTimeMonth) {
            this.trainTimeMonth = trainTimeMonth;
        }

        public float getTrainTimeDay() {
            return trainTimeDay;
        }

        public void setTrainTimeDay(float trainTimeDay) {
            this.trainTimeDay = trainTimeDay;
        }

        public float getTrainTimeHour() {
            return trainTimeHour;
        }

        public void setTrainTimeHour(float trainTimeHour) {
            this.trainTimeHour = trainTimeHour;
        }

        public float getTrainTimeMinute() {
            return trainTimeMinute;
        }

        public void setTrainTimeMinute(float trainTimeMinute) {
            this.trainTimeMinute = trainTimeMinute;
        }

        public float getTrainTimeSecond() {
            return trainTimeSecond;
        }

        public void setTrainTimeSecond(float trainTimeSecond) {
            this.trainTimeSecond = trainTimeSecond;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' + "\n" +
                    ", guardian_id='" + guardian_id + '\'' + "\n" +
                    ", login_name='" + login_name + '\'' + "\n" +
                    ", nickname='" + nickname + '\'' + "\n" +
                    ", name='" + name + '\'' + "\n" +
                    ", age=" + age + "\n" +
                    ", sex=" + sex + "\n" +
                    ", born_date='" + born_date + '\'' + "\n" +
                    ", credentials_type=" + credentials_type + "\n" +
                    ", credentials_card='" + credentials_card + '\'' + "\n" +
                    ", phone='" + phone + '\'' + "\n" +
                    ", areaCode = " + areaCode + "\'" + "\n" +
                    ", diopter_state=" + diopter_state + "\n" +
                    ", left_eye_degree=" + left_eye_degree + "\n" +
                    ", right_eye_degree=" + right_eye_degree + "\n" +
                    ", interpupillary=" + interpupillary + "\n" +
                    ", left_astigmatism_degree=" + left_astigmatism_degree + "\n" +
                    ", right_astigmatism_degree=" + right_astigmatism_degree + "\n" +
                    ", astigmatism_degree=" + astigmatism_degree + "\n" +
                    ", left_handle_degree=" + left_handle_degree + "\n" +
                    ", right_handle_degree=" + right_handle_degree + "\n" +
                    ", left_front_com_degree=" + left_front_com_degree + "\n" +
                    ", right_front_com_degree=" + right_front_com_degree + "\n" +
                    ", right_column_mirror=" + right_column_mirror + "\n" +
                    ", left_column_mirror=" + left_column_mirror + "\n" +
                    ", right_axial=" + right_axial + "\n" +
                    ", left_axial=" + left_axial + "\n" +
                    ", naked_left_eye_degree='" + naked_left_eye_degree + '\'' + "\n" +
                    ", naked_right_eye_degree='" + naked_right_eye_degree + '\'' + "\n" +
                    ", naked_binoculus_degree='" + naked_binoculus_degree + '\'' + "\n" +
                    ", correct_left_eye_degree='" + correct_left_eye_degree + '\'' + "\n" +
                    ", correct_right_eye_degree='" + correct_right_eye_degree + '\'' + "\n" +
                    ", correct_binoculus_degree='" + correct_binoculus_degree + '\'' + "\n" +
                    ", isnewuser=" + isnewuser + "\n" +
                    ", total_money=" + total_money + "\n" +
                    ", used_money=" + used_money + "\n" +
                    ", total_time=" + total_time + "\n" +
                    ", used_time=" + used_time + "\n" +
                    ", total_score=" + total_score + "\n" +
                    ", used_score=" + used_score + "\n" +
                    ", status=" + status + "\n" +
                    ", expiration_time='" + expiration_time + '\'' + "\n" +
                    ", face='" + face + '\'' + "\n" +
                    ", trainTimeYear ='" + trainTimeYear + '\'' + "\n" +
                    ", trainTimeMonth='" + trainTimeMonth + '\'' + "\n" +
                    ", trainTimeDay='" + trainTimeDay + '\'' + "\n" +
                    ", trainTimeHour='" + trainTimeHour + '\'' + "\n" +
                    ", trainTimeMinute='" + trainTimeMinute + '\'' + "\n" +
                    ", trainTimeSecond='" + trainTimeSecond + '\'' + "\n" +
                    ", message='" + message + '\'' + "\n" +
                    ", cursor='" + cursor + '\'' + "\n" +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MemberLoginResponseBean{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
