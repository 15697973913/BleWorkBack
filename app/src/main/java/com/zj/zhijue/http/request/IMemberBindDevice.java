package com.zj.zhijue.http.request;

/**
 * Created by Administrator on 2018/12/1.
 */

public interface IMemberBindDevice extends IBaseRequest {
    String MEMBERID = "memberId";//string	客户ID（用户）
    String NAME = "name";//string	姓名
    String PHONE = "phone";//
    String LOGIN_NAME = "login_name";//
    String DEVICEID = "deviceId";//string	关联设备编号ID
    String BIRTHDAY = "birthday";//生日
    String PROVICE = "province";//省份编号
    String CITY = "city";//市编号
    String AREACODE = " areaCode";//地区编号
    String ADDRESS = "address";//地址
    String AUTHTYPE = "bindType";//int	绑定认证类型，0-本人，1-监护人
    String CREDENTIALSTYPE = "credentialsType";//int	身份证类型 0身份证、1护照
    String CREDENTIALSCARD = "credentialsCard";//	string	证件号
    String DIOPTER_STATE = "diopterState";//int	屈光类型（0，近视；1，远视；2，老花；3，弱视；4,其他）
    String INTERPUPILLARY = "interpupillary";//float	瞳距
    String LEFT_EYE_DEGREE = "leftEyeDegree";//float	左眼屈光度
    String RIGHT_EYE_DEGREE = "rightEyeDegree";//float	右眼屈光度
    String GENDER_KEY = "gender";//性别
    String GLASSES_SN = "sn";//眼镜序列号

    /**
     * astigmatism_degree	否	float	双眼散光
     * right_column_mirror	是	float	右眼柱镜
     * left_column_mirror	是	float	左眼柱镜
     */

    String ASTIGMATISM_DEGREE = "astigmatismDegree";//否 float	双眼散光
    String RIGHT_COLUMN_MIRROR = "rightColumnMirror";//是	float	右眼柱镜
    String LEFT_COLUMN_MIRROR = "leftColumnMirror";//是	float	左眼柱镜

    /**
     * 裸眼视力
     */
    String LEFT_OD_DEGREE = "nakedRightEyeDegree";//
    String LEFT_OS_DEGREE = "nakedLeftEyeDegree";//
    String LEFT_OU_DEGREE = "nakedBinoculusDegree";//

    /**
     * 最佳视力
     */
    String RIGHT_OD_DEGREE = "correctRightEyeDegree";
    String RIGHT_OS_DEGREE = "correctLeftEyeDegree";
    String RIGHT_OU_DEGREE = "correctBinoculusDegree";

    /**
     * 对比度视力
     */
    String OD_CONTRAST_EYE_DEGREE = "contrastRightEyeDegree";
    String OS_CONTRAST_EYE_DEGREE = "contrastLeftEyeDegree";
    String OU_CONTRAST_EYE_DEGREE = "contrastBinoculusDegree";

    String LEFT_ASTIGMATISM_DEGREE = "leftAstigmatismDegree";//	是	float	左眼散光度
    String RIGHT_ASTIGMATISM_DEGREE = "rightAstigmatismDegree";//	是	float	右眼散光度

    String RIGHT_AXIAL = "rightAxial";//	//是	float	右眼轴向
    String LEFT_AXIAL = "leftAxial";//是	float	左眼轴向

    String LEFT_HANDLE_DEGREE = "left_handle_degree";//	float	左眼屈光度数（处理后）
    String  RIGHT_HANDLE_DEGREE = "left_handle_degree";//	float	右眼屈光度数（处理后）


    /**
     * Response
     *
     * 	绑定状态（0-绑定失败，1-用户不存在，2-绑定成功）
     */
    int RESPONSE_STATUS_FAILUE = 0;
    int RESPONSE_USER_NO_EXIST = 1;
    int RESPONSE_STATUS_SUCCESS = 2;
}