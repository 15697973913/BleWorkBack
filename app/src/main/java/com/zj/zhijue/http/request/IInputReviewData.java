package com.zj.zhijue.http.request;

public interface IInputReviewData {
    String USERNAME = "username";
    String leftEyeDegree = "leftEyeDegree";
    String rightEyeDegree = "rightEyeDegree";
    String leftAstigmatismDegree = "leftAstigmatismDegree";//float	左眼散光度
    String rightAstigmatismDegree = "rightAstigmatismDegree";
    String astigmatismDegree = "astigmatismDegree";//float	双眼散光
    String rightColumnMirror = "rightColumnMirror"; //float	右眼柱镜
    String leftColumnMirror = "leftColumnMirror";//float	左眼柱镜
    String rightAxial = "rightAxial";//float	右眼轴向
    String leftAxial = "leftAxial";
    String nakedLeftEyeDegree = "nakedLeftEyeDegree";//是  string	左眼裸眼视力
    String nakedRightEyeDegree = "nakedRightEyeDegree"; // string	右眼裸眼视力
    String nakedBinoculusDegree = "nakedBinoculusDegree";  //string	双眼裸眼视力
    String correctLeftEyeDegree = "correctLeftEyeDegree";  //	string	左眼最佳矫正视力
    String correctRightEyeDegree = "correctRightEyeDegree";  ///string	右眼最佳矫正视力
    String correctBinoculusDegree = "correctBinoculusDegree"; //string	双眼最佳矫正视力
    String binoculusPromoteNumber = "binoculusPromoteNumber"; //int	双眼视力提升行数
    String reviewDate = "reviewDate"; //date	复查日期
    String remarks = "remarks";//string	备注
    String diopterState = "diopterState";
    String createBy = "createBy";
}
