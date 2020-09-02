package com.zj.zhijue.http.request;


public interface IMemberUpdateEyeInfo extends IBaseRequest {
    String MEMBERID = "memberId";//string	客户ID（用户）
    String LOGIN_NAME = "login_name";//string 当前登录账户

    String NAME = "name";//姓名
    String CREDENTIALS_CARD = "credentials_card";//身份证号

    String LEFT_EYE_DEGREE = "left_eye_degree";//string	左眼屈光度
    String RIGHT_EYE_DEGREE = "right_eye_degree";//string 右眼屈光度
    String CORRECT_LEFT_EYE_DEGREE = "correct_left_eye_degree";//string	左眼最佳矫正视力
    String CORRECT_RIGHT_EYE_DEGREE = "correct_right_eye_degree";//string 右眼最佳矫正视力
    String CORRECT_BINOCULUS_DEGREE = "correct_binoculus_degree";//string 双眼最佳矫正视力
    String CONTRAST_LEFT_EYE_DEGREE = "contrast_left_eye_degree";//string 左眼对比度视力
    String CONTRAST_RIGHT_EYE_DEGREE = "contrast_right_eye_degree";//string 右眼对比度视力
    String CONTRAST_BINOCULUS_DEGREE = "contrast_binoculus_degree";//string 双眼对比度视力
    String NAKED_LEFT_EYE_DEGREE = "naked_left_eye_degree";//string 左眼裸眼视力
    String NAKED_RIGHT_EYE_DEGREE = "naked_right_eye_degree";//string 右眼裸眼视力
    String NAKED_BINOCULUS_DEGREE = "naked_binoculus_degree";//string	双眼裸眼视力
    String LEFT_ASTIGMATISM_DEGREE = "left_astigmatism_degree";//	是	float	左眼散光度
    String RIGHT_ASTIGMATISM_DEGREE = "right_astigmatism_degree";//	是	float	右眼散光度

    String INTERPUPILLARY = "interpupillary";//瞳距


    //Response
    int UPDATE_SUCCESS = 2;

}
