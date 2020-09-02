package com.zj.zhijue.http.request;

public interface IAddSignIn extends IBaseRequest {
    /**
     * member_id	是	string	用户编号
     login_name	否	string	姓名
     nick_name	否	string	昵称
     name	否	string	真实姓名
     */
    String MEMBER_ID = "member_id";
    String LOGIN_NAME = "login_name";
    String NICK_NAME = "nick_name";
    String NAME = "name";

    //Response

    int SIGNIN_SUCCESS = 2;//签到成功
    int SIGNIN_REPEAT = 1;//重复签到
}
