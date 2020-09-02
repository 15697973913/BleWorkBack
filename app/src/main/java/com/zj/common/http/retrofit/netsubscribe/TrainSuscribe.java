package com.zj.common.http.retrofit.netsubscribe;

import com.android.common.baselibrary.jnative.SecUtil;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.comutil.security.Md5Util;
import com.zj.common.http.retrofit.netutils.HttpMethods;
import com.zj.zhijue.http.request.IBaseRequest;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class TrainSuscribe {

    public static RequestBody createNewRequestBody(HashMap<String, String> bodyMap) {
        return LoginSubscribe.createNewRequestBody(bodyMap);
    }

    public static RequestBody createNewRequestBodyWithObject(HashMap<String, Object> bodyMap) {
        return LoginSubscribe.createNewRequestBodyWithObject(bodyMap);
    }

    public static RequestBody createNewRequestBodyWithObject2(HashMap<String, Object> bodyMap) {
        JSONObject jsonObject = new JSONObject(bodyMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        return requestBody;
    }

    /**
     * 上报训练数据(开始，干预，运行，停止，暂停)
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postTrainInfo(HashMap<String, String> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postUserTrainInfo(headerMap, createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }


    /**
     * 获取最新的训练数据给蓝牙设备
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getNewTrainInfo2BleDevice(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getNewUserTrainInfo(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 获取训练时间信息
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getUserTrainTimeInfo(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getUserTrainTimeInfo(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 获取视力复查数据
     */
    public static void getReviewData(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getReviewSightDataInfo(headerMap, createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 提交设备信息
     */
    public static void postDeivceInfo(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postDeviceInfo(headerMap, createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 提交用户反馈
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postFeedBack(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postFeedback(headerMap, createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }


    /**
     * 获取用户的反馈信息列表
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getFeedBackList(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getFeedbackList(headerMap, bodyMap);
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 获取常见问题
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getQuestionList(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getQuestionList(headerMap, bodyMap);
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 获取 APP 版本信息  或 固件的最新版本信息
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getApkVersionInfo(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getApkVersionInfo(headerMap, createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 获取眼镜初始化运行参数
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getGlassDeviceInitParam(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getGlassDeviceInitParam(headerMap, bodyMap);
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }


    /**
     * 上报干预反馈数据
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postInterveneFeedBackData(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postInterveneFeedBackData(headerMap, createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 上报实时反馈数据
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postCommonFeedBackData(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postCommonFeedBackData(headerMap, createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 上报运行参数数据
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postRunParamData(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postRunParamData(headerMap, createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 获取运行参数数据
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void queryRunParamData(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().queryRunParamData(headerMap, createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 获取 Html 代码
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getHtmlCode(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getHtmlCode(headerMap,  bodyMap);
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 绑定设备（如果当前设备没有被绑定，则绑定）
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void bindDevice(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().bindDevice(headerMap,  createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 提交录入的复查数据
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postInputReviewData(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postInputReviewData(headerMap,  createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 提交录入的其他数据
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postInputOtherData(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postInputOtherData(headerMap,  createNewRequestBodyWithObject(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }


    /**
     * 获取消息列表
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getMessageList(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getMessageList(headerMap, bodyMap);
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 获取我的团队列表
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getTeamList(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getTeamList(headerMap, bodyMap);
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 获取充值及消费列表
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getAddAndUseLogs(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getAddAndUseLogs(headerMap,createNewRequestBodyWithObject2(bodyMap));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 根据手机号获取用户信息
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getUseInfo(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getUseInfo(headerMap,bodyMap);
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

    /**
     * 获取app版本信息
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getAppVersion(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getAppVersion(headerMap, bodyMap);
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }


    /**
     * 根据userid获取用户信息
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getPersonalInfo(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getPersonalInfo(headerMap,bodyMap);
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 2);
    }

}
