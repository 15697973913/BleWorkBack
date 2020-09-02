package com.zj.common.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static OkHttpUtil mInstance = null;
    private OkHttpClient mOkHttpClient;

    public static boolean init() {
        if (mInstance == null) {
            mInstance = new OkHttpUtil();
        }
        return true;
    }

    public static OkHttpUtil instance() {
        return mInstance;
    }

    public String dsormGet(String url, String params) {
        Builder builder = getRequestBuilder(params);
        Response response = null;
        String result = null;
        if (builder == null) {
            return result;
        } else {
            try {
                Request request = builder.url(url + "?" + UrlUtils.encodeUrl(params)).build();
                response = this.mOkHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String dsormPost(String url, String params){
        Builder builder = getRequestBuilder(params);
        Response response = null;
        String result = null;
        if (builder == null) {
            return result;
        }
        try {
            Request request = builder.url(url).post(RequestBody.create(MEDIA_TYPE_JSON, params)).build();
            response = this.mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    }

    public static Object getResult(String strResult, TypeToken type, boolean isWriteLog) {
        Object result;
        try {
            result = JsonUtil.json2objectWithDataCheck(strResult, type);

            if (result == null) {
                return RuleResult.getDefaultError("parse result is null");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static Object getResult(Response response, TypeToken type, boolean isWriteLog) {
        try {
            return getResult(response.body().string(), type, isWriteLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static Object getResult(Response response, TypeToken type) {
        return getResult(response, (TypeToken) type, false);
    }

    private OkHttpUtil() {
        this.mOkHttpClient = null;
        this.mOkHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).addNetworkInterceptor(new StethoInterceptor()).build();
    }

    private Builder getRequestBuilder(String reqbody) {
        try {
            String deviceId = "";
            Builder builder = new Builder();
            //builder.addHeader("deviceId", deviceId);
            builder.addHeader("user-agent", "android");
            return builder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
