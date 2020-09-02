package com.zj.common.http.retrofit.netapi;

public class URLConstant {
    //存放全部的URL
//    public final static String BASE_URL = "http://118.126.91.32:9400/";//http://120.76.103.207:9000/";//"http://192.168.10.52/";//本地 http://192.168.10.75:9700/  服务器 http://192.168.10.237:8096/
    public final static String BASE_URL_ZJFD = "http://zj.zhuojiacn.net/";
    public final static String BASE_URL_FIRST_PARTY = "http://47.115.78.29:9300/";
    public final static String BASE_URL = "http://47.115.78.29:9300/";

    public final static String URLNAME_ZJFD = "zjfd";//智觉枫帝
    public final static String URLNAME_FIRST_PARTY = "first_party";//甲方的

    public final static String RETAIL_URL = "";
    public final static String HTTP_PREFIX = "http://";
    public static String GET_TOKEN_URL = BASE_URL + "/authorize?response_type=token&client_id=GlassesMvcServer&redirect_uri=" + BASE_URL + "api/access_token";
    public static String SEND_VERFICATION_CODE_URL = BASE_URL + "api/Customer/SendVerficationCode";
    public static String POST_CRASH_LOG_FILE_URL = "";
    public final static boolean supportMulti = false;
    public final static String OATH_TOKEN_URL = supportMulti ? "/oauth/oauth/token" : "/oauth/token";
    public final static String OATH_LOGOUT_TOKEN_URL = supportMulti ? "/oauth/oauth/logout" : "api/login/getExit";
    public final static String MEMBER_PAHT = supportMulti ? "/member" : "";
    public final static String UPLOAD_PATH = supportMulti ? "/fastdfs" : "";

}
