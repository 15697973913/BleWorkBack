package com.zj.zhijue.http.request;

/**
 * 获取 Html 代码
 */
public interface IGetHtmlCode extends IBaseRequest {
    String CATEGORYID = "categoryId";


    int CATEGORY_ABOUT_US = 1;//关于我们
    int PRODUCT_INTRODUCE = 2;//产品介绍
    int INTERVENE_KEY_INTRODUCE = 3;//干预键介绍
    int USER_AGREEMENT = 4;//用户注册协议


}
