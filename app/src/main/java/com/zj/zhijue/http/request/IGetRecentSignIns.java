package com.zj.zhijue.http.request;

public interface IGetRecentSignIns extends IBaseRequest {
    /**
     * member_id	是	string	用户编号
     signDate	是	datetime	签到日期
     */
    String MEMBER_ID = "member_id";
    String SIGNDATE = "signDate";

}
