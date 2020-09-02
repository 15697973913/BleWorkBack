package com.zj.zhijue.http.request;



public interface IFileUpload extends IBaseRequest{
    String OWNERID = "ownerId";
    String USER_ID = "userId";
    String PLATFORMTYPE = "platformType";//1-pc,2-android,3-ios
    String CATEGORY = "category";//头像=1,眼镜图片=2,验视单=3,用户证件 =4,企业证件=5,异常日志=6
    String FROM_MODULE = "fromModule";
    String UPLOADER = "uploader";
    String MODULE = "module";//否	String	模块编号：login\base\member\device\factory


    String APP_VERSION_CODE = "appVersionCode";
    String APP_VERSION_NAME = "appVersionName";
    String SYS_VERSION_CODE = "sysVersionCode";
    String SYS_VERSION_NAME = "sysVersionName";
    String IMEI = "imei";
    String UTDID = "utdid";

    String PARAMS = "params";

    String OWNER_ID_DEFAULT_VALUE = "ANDROID_EXCEPTION";
    String UPLOADER_DEFAULT_VALUE = "ANDROID_EXCEPTION";

    String CATEGORY_PORTRAIT_TYPE = "0";//头像
    String CATEGORY_GLASSES_IMAGE_TYPE = "1";//眼镜图片
    String CATEGORY_EXAMINATION_TYPE = "2";//验视单
    String CATEGORY_PERSONAL_DOCUMENT_TYPE = "3";//用户证件
    String CATEGORY_COMPANY_DOCUMENT_TYPE = "4";//公司证件
    String CATEGORY_EXCEPTION_ZIP_FILE_TYPE = "5";//错误日志文件

}
