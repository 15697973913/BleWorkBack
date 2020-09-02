package com.zj.zhijue.http.request;


/**
 * 获取软件或固件的最新的版本信息
 */
public interface IgetLastedSystemUpdate {
    String FILETYPE = "fileType";


    int FILETYPE_ANDROID = 1;// android
    int FILETYPE_IOS = 2;//ios
    int FILETYPE_FIRMWARE = 3;//硬件
}
