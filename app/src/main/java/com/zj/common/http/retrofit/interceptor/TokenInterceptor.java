package com.zj.common.http.retrofit.interceptor;

import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.vise.utils.assist.StringUtil;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.bean.BaseBean;
import com.zj.zhijue.bean.UseInfoBean;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.http.request.IOauthToken;
import com.zj.zhijue.http.request.ISendVerficationCode;
import com.zj.zhijue.http.response.OauthBean;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;


public class TokenInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private final String TAG = TokenInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String path = request.url().encodedPath();
        //MLog.d("intercept path = " + path + ";  thread.id = " + Thread.currentThread().getId());
        if (isMembers(path)) { //凡是接口中包含Members都添加到拦截器，如果需求不同，这里需要改动。
            //加拦截器,旧的token是token的key,这个要问清楚后台
            String accessToken = Config.getConfig().getAccessToken();
         /*   if (CommonUtils.isEmpty(accessToken)) {
                accessToken = Config.getConfig().getFreshToken();
            }*/
            if (!CommonUtils.isEmpty(accessToken)) {
                request = request.newBuilder().addHeader(ISendVerficationCode.AUTHORIZATION, ISendVerficationCode.BEARER + accessToken).build();
            }
        }
        /**
         *判断刷新token连接，直接返回，不走下面代码，避免死循环。
         */
        if (request.url().toString().contains(URLConstant.OATH_TOKEN_URL) && request.method() == "POST") {
            return chain.proceed(request);
        }
        //拦截了响应体
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        if (!HttpHeaders.hasBody(response)) {
            //END HTTP
        } else if (bodyEncoded(response.headers())) {
            //HTTP (encoded body omitted)
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    return response;
                }
            }
            if (!isPlaintext(buffer)) {
                return response;
            }
            if (contentLength != 0) {
                //获取到response的body的string字符串
                String result = buffer.clone().readString(charset);
                MLog.d(TAG + " path=[" + path + "] " + result);

                //SdLogUtil.writeCommonLog(TAG + " result = " + result);
                //当状态码返回的是400或者401,即代表过期
                if (response.code() == 400 || response.code() == 401) {
                    if (!path.contains(URLConstant.OATH_TOKEN_URL)) {//代表不是刷新token
                        //获取新的token
                        //String membership_uuid = "";//SharedPreUtil.getString(Global.mContext, "获取的id", "");
                        // String refresh_token = SharedPreUtil.getString(Global.mContext, "获取的token", "");
                        if (!CommonUtils.isEmpty(Config.getConfig().getFreshToken())) {
                            /**
                             * 先尝试使用 RefreshToken 刷新 AccessToken，如果失败则清空 RefreshToken值，继续往下执行，使用用户名和密码获取新Token
                             */
                            retrofit2.Response<ResponseBody> refreshTokenExecuteResponse = LoginSubscribe.refreshToken();
                            if (refreshTokenExecuteResponse.isSuccessful()) {
                                ResponseBody refreshResponseBody = refreshTokenExecuteResponse.body();
                                OauthBean refreshOauthBean = (OauthBean) JsonUtil.json2objectWithDataCheck(new String(refreshResponseBody.bytes()), new TypeReference<OauthBean>() {
                                });

                                Request newRequest = request.newBuilder()
                                        .removeHeader(ISendVerficationCode.AUTHORIZATION)   //移除旧的token
                                        .header(ISendVerficationCode.AUTHORIZATION, ISendVerficationCode.BEARER + refreshOauthBean.getAccess_token())
                                        .build();

                                Config.getConfig().saveAccessToken(refreshOauthBean.getAccess_token());
                                Config.getConfig().saveFreshToken(refreshOauthBean.getRefresh_token());

                                return chain.proceed(newRequest);//重新发起请求，此时是新的token

                            } else {
                                Config.getConfig().saveFreshToken(null);
                            }
                        }

                        String userName = Config.getConfig().getUserName();
                        String passwd = Config.getConfig().getPasswd();

                        if (!CommonUtils.isEmpty(userName) && !CommonUtils.isEmpty(passwd)) {
                            retrofit2.Response<ResponseBody> executeResponse = LoginSubscribe.getNewToken();
                            //MLog.d("executeResponse.code() = " + executeResponse.code());
                            //SdLogUtil.writeCommonLog("executeResponse.code() = " + executeResponse.code());
                            if (executeResponse.isSuccessful()) {
                                ResponseBody responsebodyResult = executeResponse.body();
                                OauthBean oauthBean = (OauthBean) JsonUtil.json2objectWithDataCheck(new String(responsebodyResult.bytes()), new TypeReference<OauthBean>() {
                                });
                                MLog.d("iOauthToken = " + oauthBean.toString());
                                //SdLogUtil.writeCommonLog("executeResponse.code() = " + executeResponse.code());
                                Request newRequest = request.newBuilder()
                                        .removeHeader(ISendVerficationCode.AUTHORIZATION)   //移除旧的token
                                        .header(ISendVerficationCode.AUTHORIZATION, ISendVerficationCode.BEARER + oauthBean.getAccess_token())
                                        .build();

                                Config.getConfig().saveAccessToken(oauthBean.getAccess_token());
                                Config.getConfig().saveFreshToken(oauthBean.getRefresh_token());

                                return chain.proceed(newRequest);//重新发起请求，此时是新的token

                            } else {
                                ResponseBody responsebodyResult = executeResponse.errorBody();
                                OauthBean oauthBean = null;
                                if (null != responseBody.bytes()) {
                                    String errorResponse = new String(responsebodyResult.bytes());
                                    MLog.d("errorResponse = " + errorResponse);
                                    oauthBean = (OauthBean) JsonUtil.json2objectWithDataCheck(errorResponse, new TypeReference<OauthBean>() {
                                    });
                                }
                                if (null != oauthBean) {
                                    if (!CommonUtils.isEmpty(oauthBean.getError())) {
                                        if (oauthBean.getError().equals(IOauthToken.INVALID_GRANT)) {
                                            Config.getConfig().savePasswd(null);//用户名和密码错误后，清空本地密码
                                            ToastUtil.showShort(R.string.username_or_passwd_error_text);
                                        } else if (oauthBean.getError().equals(IOauthToken.UNAUTHORIZED)) {
                                            ToastUtil.showShort(R.string.interface_error_text);
                                        } else {
                                            ToastUtil.showShort(oauthBean.getError_description());
                                        }
                                    }
                                }
                                Config.getConfig().saveFreshToken(null);
                                Config.getConfig().saveAccessToken(null);
                                BleDeviceManager.getInstance().stopScan();
                                BleDeviceManager.getInstance().stopScanByMac();
                                BleDeviceManager.getInstance().disconnectGlassesBleDevice(true);
                                GlassesBleDataModel.getInstance().clearModelData();
                                TrainModel.getInstance().clearTrainModelData();
                                //要求用户直接登录
                                BaseActivity.GotoLoginActivity();

                            }
                        }
                    } else {
                        return response;
                    }
                } else {
                    //此时没有过期
                    //MLog.d("intercept: token ------------");
                }
            }
        }
        return response;
    }

    //要求用户直接登录


    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    //项目中所有包含members的接口
    public boolean isMembers(String path) {
        if (!path.contains(URLConstant.OATH_TOKEN_URL)) {
            return true;
        }
        return false;
    }


}