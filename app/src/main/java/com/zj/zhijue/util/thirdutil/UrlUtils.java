package com.zj.zhijue.util.thirdutil;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.android.common.baselibrary.util.comutil.CommonUtils;


import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;


public class UrlUtils {
    @NonNull
    public static Parameters getParamsFromUrl(String url) {
        Parameters parameters = null;
        if (url != null && url.indexOf("?") > -1) {
            String tempUrl = url.substring(url.indexOf("?") + 1);
            int indexSharp = tempUrl.indexOf("#");
            parameters = indexSharp > -1 ? splitUrlQuery(tempUrl.substring(0, indexSharp)) : splitUrlQuery(tempUrl);
        }
        if (parameters == null) {
            return new Parameters();
        }
        return parameters;
    }

    public static Parameters splitUrlQuery(String query) {
        Parameters parameters = new Parameters();
        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    try {
                        parameters.addParameter(URLDecoder.decode(param[0], "UTF-8"), URLDecoder.decode(param[1], "UTF-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return parameters;
    }

    public static String buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (query.isEmpty()) {
            return strUrl;
        }
        if (url.getQuery().isEmpty()) {
            if (strUrl.endsWith("?")) {
                return strUrl + query;
            }
            return strUrl + "?" + query;
        } else if (strUrl.endsWith("&")) {
            return strUrl + query;
        } else {
            return strUrl + "&" + query;
        }
    }



    public static boolean isSuperUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.startsWith("http://m.fanli.com/super") || url.startsWith("http://m.51fanli.com/super")) {
            return true;
        }
        return false;
    }

    public static String getLcFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Parameters params = getParamsFromUrl(url);
        if (params != null) {
            String lc = params.getParameter("lc");
            if (!TextUtils.isEmpty(lc)) {
                return lc;
            }
        }
        return null;
    }

    public static boolean urlContainQuestionMark(String url) {
        if (!CommonUtils.isEmpty(url)) {
            return -1 != url.indexOf("?");
        }
        return false;
    }
}
