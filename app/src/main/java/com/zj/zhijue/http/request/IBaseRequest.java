package com.zj.zhijue.http.request;

public interface IBaseRequest {
    String BEARER = "Bearer ";
    String AUTHORIZATION = "Authorization";
    int PLATFORM_VALUE_ANDROID = 1; //0-pc,1-android,2-ios
    String VERSION_CODE = "versionCode";
    String VERSIONNAME = "versionName";
    String UTDID = "utdid";
    String SIGNATURE = "sign";
    String MEMBER_ID = "member_id";

    //ResponseBean Common Data
    String DATA = "data";
    String SUCCESS = "success";
    String ERROR = "error";

    String LOGIN_ERROR_WITH_USERNAME_PASSW = "004";//用户名或密码不正确
}
