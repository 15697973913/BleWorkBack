package com.zj.zhijue.http.request;

public interface IOauthToken {
    String GRANTED_TYPE = "grant_type";
    String SCOPE = "scope";
    String CLIENT_ID = "client_id";
    String CLIENT_SECRET = "client_secret";

    String PASSWORD = "password";
    String REFRESH_TOKEN = "refresh_token";
    String ALL = "all";
    String ZHIJUE = "zhijue-ad";

    String USERNAME = "username";

    String ACCESS_TOKEN = "access_token";

    /**
     * 测试数据
     */
    String TEST_USER_NAME = "liangjinglu";
    String TEST_PASSWORD = "93607ffb6b59324815a9375f750cb82984d2fdf2020947efa7eaf058";
    String TEST_CLIENT_SECRET = "123456";

    /**
     * Token 无效或错误的状态码
     */
    String NO_TOKEN = "006";//("006","无令牌,无权限访问"),
    String ERROR_TOKEN = "007";//("007","无效令牌,请先获取授权"),
    String INVALID_GRANT = "invalid_grant";
    String UNAUTHORIZED = "unauthorized";
}
