package com.zj.zhijue.http;

import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.android.common.baselibrary.util.ThreadPoolUtils;

import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.zhijue.callback.RequestTokenCallBack;
import com.android.common.baselibrary.log.MLog;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/12/1.
 */

public class HttpUtil {

    public static String getRedirectUrl() {
        String requestUrl = URLConstant.GET_TOKEN_URL;
        URL url = null;
        String responseUrl = null;
        try {
            url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.getResponseCode();
            responseUrl = conn.getURL().toString();
            conn.disconnect();
            MLog.d("真实URL:" + responseUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseUrl;
    }

    public static void getToken(final RequestTokenCallBack requestTokenCallBack) {
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                String tokenUrl = getRedirectUrl();
                //SdLogUtil.writeCommonLog(tokenUrl);
                String realToken = cutToken(tokenUrl);
                requestTokenCallBack.getToken(realToken);
                MLog.d("tokenUrl = " + realToken);
                //SdLogUtil.writeCommonLog(realToken);
            }
        });
    }

    private static String cutToken(String realUrl) {
        if (!CommonUtils.isEmpty(realUrl)) {
            int startIndex = realUrl.indexOf("=");
            int endIndex = realUrl.indexOf("&");
            if (-1 != startIndex && endIndex > startIndex) {
                return realUrl.substring(startIndex + 1, endIndex);
            }
        }
        return null;
    }
}
