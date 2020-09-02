package com.zj.common.http;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlUtils {
    public static String encodeUrl(String url) {
        try {
            if (null != url) {
                url = URLEncoder.encode(url, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String decodeUrl(String url) {
        try {
            if (null != url) {
                url = URLDecoder.decode(url, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
