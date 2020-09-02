package com.zj.common.http.retrofit.netsubscribe;


import com.android.common.baselibrary.jnative.SecUtil;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.CommonUtils;
import com.zj.zhijue.util.AppUtils;
import com.android.common.baselibrary.util.comutil.security.Md5Util;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netutils.HttpMethods;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IOauthToken;
import com.zj.zhijue.util.Config;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * 建议：把功能模块来分别存放不同的请求方法，比如登录注册类LoginSubscribe
 */

public class LoginSubscribe {
    public static long versionCode = 0;
    public static String versionName = null;

    public static RequestBody createNewRequestBody(HashMap<String, String> bodyMap) {
        return createNewRequestBody(bodyMap, false);
    }

    /**
     * 创建请求体
     *
     * @param bodyMap bodyMap
     * @param isZJFD  是否为直接枫帝的域名请求
     * @return RequestBody
     */
    public static RequestBody createNewRequestBody(HashMap<String, String> bodyMap, boolean isZJFD) {
        if (versionCode == 0) {
            versionCode = AppUtils.getVersionCode();
        }

        if (CommonUtils.isEmpty(versionName)) {
            versionName = AppUtils.getGitCommitVersionName(MyApplication.getInstance());
        }

        bodyMap.put(IBaseRequest.UTDID, AppUtils.getUtdId());
        bodyMap.put(IBaseRequest.VERSION_CODE, String.valueOf(versionCode));
        bodyMap.put(IBaseRequest.VERSIONNAME, versionName);

        JSONObject jsonObject = new JSONObject(bodyMap);
        try {
            String sign = Md5Util.getMD5String(SecUtil.getRSAsinature(jsonObject.toString()));
            jsonObject.put(IBaseRequest.SIGNATURE, sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //SdLogUtil.writeCommonLog("createNewRequestBody() jsonObject = " + jsonObject.toString());
        MLog.d("createNewRequestBody() jsonObject = " + jsonObject.toString());

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : bodyMap.keySet()) {
            builder.add(key, bodyMap.get(key) + "");
//            builder.addEncoded(key, bodyMap.get(key) + "");
        }

        //text/plain  application/json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        if (isZJFD) {
            requestBody = builder.build();
        }

        return requestBody;
    }


    public static RequestBody createNewRequestBodyWithObject(HashMap<String, Object> bodyMap) {
        if (versionCode == 0) {
            versionCode = AppUtils.getVersionCode();
        }

        if (CommonUtils.isEmpty(versionName)) {
            versionName = AppUtils.getGitCommitVersionName(MyApplication.getInstance());
        }
            bodyMap.put(IBaseRequest.UTDID, AppUtils.getUtdId());
            bodyMap.put(IBaseRequest.VERSION_CODE, String.valueOf(versionCode));
            bodyMap.put(IBaseRequest.VERSIONNAME, versionName);


        JSONObject jsonObject = new JSONObject(bodyMap);
            try {
                String sign = Md5Util.getMD5String(SecUtil.getRSAsinature(jsonObject.toString()));
                jsonObject.put(IBaseRequest.SIGNATURE, sign);
            } catch (Exception e) {
                e.printStackTrace();
            }
        //SdLogUtil.writeCommonLog("createNewRequestBody() jsonObject = " + jsonObject.toString());
        MLog.d("createNewRequestBody() jsonObject = " + jsonObject.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        return requestBody;
    }

    /**
     * 获取数据
     */
    public static void getData(int pageNumber, int userId, DisposableObserver<ResponseBody> subscriber) {
        Map<String, Integer> map = new HashMap<>();
        map.put("start", pageNumber);
        map.put("count", userId);
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getDataForMap(map);
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 获取时长提醒时间
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getDurationRemindTime(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getDurationRemindTime(headerMap, createNewRequestBody(bodyMap,true));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 0);
    }

    /**
     * 发送时长不足推送消息
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void sendTimeRemind(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().sendTimeRemind(headerMap, createNewRequestBody(bodyMap,true));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 0);
    }

    public static void loginAccount(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().loginAccountWithHeader(headerMap, createNewRequestBody(bodyMap,true));
        HttpMethods.getInstance().toSubscribeWithRetryTimes(observable, subscriber, 0);
    }


    public static void register(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().register(headerMap, createNewRequestBody(bodyMap,true));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 找回密码
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void findPasswd(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().findPasswd(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 修改密码
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void updatePasswd(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().updatePasswd(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static void postRegisterWithVerficationCode(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscirber) {

        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postRegiterInfoWithVerficationCodeWithJson(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscirber);
    }

    public static void bindGlassesInfo(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().bindGlassesInfo(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 更新视力信息
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void upateUserEyeInfo(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().updateEyeInfo(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 提交训练时间信息
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postUserTrainTimeInfo(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postUserTrainTimeInfo(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 提交签到信息
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postSignInInfo(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().postSignInInfo(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 获取充值服务信息
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postProductInfo(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getProductInfo(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 获取协议信息
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postConfigInfo(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getConfigInfo(headerMap, createNewRequestBody(bodyMap,true));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 下单
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void postCreateOrder(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().createOrder(headerMap, createNewRequestBody(bodyMap,true));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 获取签到信息
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void getSignInInfo(HashMap<String, String> headerMap, HashMap<String, String> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().getSignInInfo(headerMap, createNewRequestBody(bodyMap));
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static Response getNewToken() throws IOException {
        HashMap<String, String> header = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();


        String userName = Config.getConfig().getUserName();
        String passwd = Config.getConfig().getPasswd();
        if (!CommonUtils.isEmpty(userName)) {
            userName = SecUtil.decodeByAES(userName);
        }

        if (!CommonUtils.isEmpty(passwd)) {
            passwd = SecUtil.decodeByAES(passwd);
        }

//        passwd = Md5Util.getMD5String(SecUtil.getRSAsinatureWithLogin(passwd));
        bodyMap.put(IOauthToken.USERNAME, userName);
        bodyMap.put(IOauthToken.PASSWORD, passwd);

        bodyMap.put(IOauthToken.GRANTED_TYPE, IOauthToken.PASSWORD);
        bodyMap.put(IOauthToken.SCOPE, IOauthToken.ALL);
        bodyMap.put(IOauthToken.CLIENT_ID, IOauthToken.ZHIJUE);
        bodyMap.put(IOauthToken.CLIENT_SECRET, IOauthToken.TEST_CLIENT_SECRET);
        bodyMap.put(IBaseRequest.UTDID, AppUtils.getUtdId());
        bodyMap.put(IBaseRequest.VERSION_CODE, String.valueOf(versionCode));

        Response result = HttpMethods.getInstance().getHttpApi().getNewToken(header, bodyMap).execute();
        return result;
    }

    /**
     * 删除云端训练数据
     *
     * @param headerMap
     * @param subscriber
     */
    public static void deleteServerTrainInfoWithDebug(HashMap<String, Object> headerMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().deleteServerTrainInfoWithDebug(headerMap);
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 退出登录
     *
     * @param headerMap
     * @param bodyMap
     * @param subscriber
     */
    public static void logoutToken(HashMap<String, Object> headerMap, HashMap<String, Object> bodyMap, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().logoutToken(headerMap, bodyMap);
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }


    public static Response refreshToken() throws IOException {
        HashMap<String, String> header = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put(IOauthToken.GRANTED_TYPE, IOauthToken.REFRESH_TOKEN);
        bodyMap.put(IOauthToken.CLIENT_ID, IOauthToken.ZHIJUE);
        bodyMap.put(IOauthToken.CLIENT_SECRET, IOauthToken.TEST_CLIENT_SECRET);
        bodyMap.put(IOauthToken.REFRESH_TOKEN, Config.getConfig().getFreshToken());
        bodyMap.put(IBaseRequest.UTDID, AppUtils.getUtdId());
        bodyMap.put(IBaseRequest.VERSION_CODE, String.valueOf(versionCode));

        Response result = HttpMethods.getInstance().getHttpApi().getNewToken(header, bodyMap).execute();
        return result;
    }

    public static void getNewTokenByHttpClient() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(HttpMethods.BASE_URL + URLConstant.OATH_TOKEN_URL + "?" + getUrlSufix())
                .method("POST", getRequestBody())
                .build();
        // 同步请求
        Call synCall = client.newCall(request);
        okhttp3.Response response = synCall.execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                System.out.println(body.string());
            }
        } else System.err.println(response.message());
    }

    private static RequestBody getRequestBody() {
        HashMap<String, String> body = new HashMap<>();
        body.put(IOauthToken.USERNAME, IOauthToken.TEST_USER_NAME);
        body.put(IOauthToken.PASSWORD, IOauthToken.TEST_PASSWORD);

        JSONObject jsonObject = new JSONObject(body);
        //SdLogUtil.writeCommonLog("createNewRequestBody() jsonObject = " + jsonObject.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.toString());

        return requestBody;
    }

    private static String getUrlSufix() {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(IOauthToken.GRANTED_TYPE, IOauthToken.PASSWORD);
        queryMap.put(IOauthToken.SCOPE, IOauthToken.ALL);
        queryMap.put(IOauthToken.CLIENT_ID, IOauthToken.ZHIJUE);
        queryMap.put(IOauthToken.CLIENT_SECRET, IOauthToken.TEST_CLIENT_SECRET);
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = queryMap.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (i >= 1) {
                stringBuilder.append("&" + key + "=" + value);
            } else {
                stringBuilder.append(key + "=" + value);
            }
            i++;
        }
        return stringBuilder.toString();
    }

    /**
     * 上传自定义头像
     *
     * @param imagePath
     * @param subscriber
     */
    public static void uploadUserCustomPortraitImage(HashMap<String, String> paramMap, HashMap<String, RequestBody> partMap, String imagePath, DisposableObserver<ResponseBody> subscriber) {
        String fileNameByTimeStamp = System.currentTimeMillis() + ".jpg";
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);//multipart/form-data  image/jpeg

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileNameByTimeStamp, requestFile);


        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().uploadPortraitImage(paramMap, partMap, body);

        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 上传 APP 崩溃日志文件
     *
     * @param paramMap
     * @param partMap
     * @param zipfilePath
     * @param subscriber
     */
    public static void uploadAppLogZipFile(HashMap<String, Object> paramMap, HashMap<String, Object> partMap, String zipfilePath, DisposableObserver<ResponseBody> subscriber) {

        //String fileNameByTimeStamp = AppUtils.getUtdId() + "_" + System.currentTimeMillis() + ".zip";
        File file = new File(zipfilePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);//multipart/form-data  image/jpeg

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        Observable<ResponseBody> observable = HttpMethods.getInstance().getHttpApi().uploadAppLogZipFile(paramMap, createNewRequestBodyWithObject(partMap), body);

        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }


}
